<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link type="text/css" rel="stylesheet" href="../css/global.css">
    <script src="http://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript">google.load("jquery", "1.4");</script>
    <title>K-Witches</title>
</head>
<body>
<%@ page import="org.slim3.datastore.Datastore" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="java.util.List" %>
<%@ page import="kwitches.model.UserModel" %>
<%@ page import="static kwitches.util.ViewUtils.*" %>
   <table>
       <tr>
            <th></th>
            <th>名前</th>
            <th>メールアドレス</th>
            <th>許可</th>
            <th></th>
       </tr>
<%
    List<UserModel> userModelList = (List<UserModel>)get("userModelList",getClass().getClassLoader());
    for (UserModel userModel : userModelList) {
        Key key = userModel.getIconRef().getKey();
        boolean isAuthUser = userModel.isAuthUser();
        String imageKey = "";
        if (key != null) {
            imageKey = Datastore.keyToString(key);
        }
%>
           <tr>
               <form action="./setauth" method="POST">
               <td><img src="../user/icon?_=<%=imageKey%>" width="48" height="48"></td>
               <td><%= userModel.getName() %></td>
               <td><%= userModel.getUser().getEmail() %></td>
               <td><input type="checkbox" name="is_auth_user" value="yes" <%= isAuthUser ? "checked" : "" %>></td>
               <input type="hidden" name="user" value="<%= Datastore.keyToString(userModel.getKey()) %>">
               <td><input type="submit" value="変更"></td>
               </form>
           </tr>
<%
    }
%>
   </table>
</body>
</html>
