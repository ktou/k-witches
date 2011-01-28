package kwitches.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * ユーザ認証を行うフィルタ
 */
public class UserAcconutFilter implements Filter {
    
    private String[] excludes;

    private static final UserModelMeta meta =  UserModelMeta.get();

    public void init(FilterConfig arg0) throws ServletException {
        String exclude = arg0.getInitParameter("exclude");
        if (exclude == null) return;
        this.excludes = exclude.split(",");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        UserService userService = UserServiceFactory.getUserService();

        String thisURL = ((HttpServletRequest) request).getRequestURI();
        
        if (thisURL.equals("/index.jsp")) {
            thisURL = "/";
        }
        if (((HttpServletRequest) request).getUserPrincipal() == null) {
            if (!isExcludePath(thisURL)) {
                ((HttpServletResponse) response).sendRedirect(userService
                  .createLoginURL("/"));
                return;
            }
        }

        Principal principal = ((HttpServletRequest) request).getUserPrincipal();
        HttpSession session = ((HttpServletRequest) request).getSession();

        if (principal == null) {
            session.setAttribute("isLogin", "LOGOUT");
            session.setAttribute("urlpath", userService.createLoginURL(thisURL));
           
        } else {
            session.setAttribute("isLogin", "LOGIN");
            session.setAttribute("urlpath", userService.createLogoutURL(thisURL));
            session.setAttribute("userInfo", userService.getCurrentUser());
            User user = userService.getCurrentUser();
            UserModel userModel = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
            boolean isKAuth = false;
            if (userModel == null) {
                 if (!thisURL.equals("/user/config.jsp")) {
                     ((HttpServletResponse) response).sendRedirect("/user/config");
                 }
            } else if (userService.isUserAdmin() || userModel.isAuthUser()) {
                isKAuth = true;
            }
            
            session.setAttribute("isKAuth", isKAuth);
        }

        filterChain.doFilter(request, response);
    }
    
    /**
     * リクエストされたURLが除外対象か判断する。
     * 除外対象の場合trueを返す
     * @param thisURL
     * @return
     */
    private boolean isExcludePath(String thisURL) {
        String[] excludes = this.excludes;
        for (String path : excludes) {
            // 除外対象パスの最後が「*」の場合、indexOfで含まれるか確認
            if (path.indexOf("*") == path.length() - 1) {
                if (thisURL.indexOf(path.substring(0, path.length() - 2)) >= 0) {
                    return true;
                }
            } else {
                // 上記以外は、完全一致
                if (thisURL.equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void destroy() {
    }

}
