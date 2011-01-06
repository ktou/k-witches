<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<c:if test="${'LOGOUT' == isLogin}">
<a href="${urlpath}">login</a>
</c:if>
<c:if test="${'LOGIN' == isLogin}">
${userInfo.email} | <a href="${urlpath}">logout</a>
</c:if>
 | 
<c:if test="${!isKAuth}">
許可されていません
</c:if>
<c:if test="${isKAuth}">
<a href="./admin/authlist">管理画面</a>
</c:if>
</div>
