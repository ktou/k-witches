<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" href="../images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="icon" href="../images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link type="text/css" rel="stylesheet" href="../css/global.css">
    <script src="http://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript">google.load("jquery", "1.4");</script>
    <title>K-Witches</title>
</head>
<body>
    <jsp:include page="../user_account.jsp" />

        <h1>設定</h1>
        <form action="./register_config" enctype="multipart/form-data" method="post">
            <p>名前</p>
            <input type="text" name="name" value="${username}">
            <p>アイコン</p>

            <img src="./icon?_=${imageKey}" width="96" height="96">

            <br><br>
            <input type="checkbox" name="icon_reset" value="yes">画像をデフォルトに戻す<br>
            <input type="file" name="fileImage"><br><br>
            <input type="submit" value="設定する">

        </form>
        <br>
        <br>
        <a href="../">戻る</a>

</body>
</html>
