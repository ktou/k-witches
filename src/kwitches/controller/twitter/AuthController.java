package kwitches.controller.twitter;

import kwitches.service.twitter.TwitterAccessService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;


public class AuthController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String token = param("oauth_token");
        if (token == null == false) {
            new TwitterAccessService().setAuth(token);
            if (new TwitterAccessService().isAuthorized() == false)
                return redirect("http://twitter.com/logout");
        }
        return redirect("/twitter/fetch");
    }
}
