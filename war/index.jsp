<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" href="./images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="icon" href="./images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link type="text/css" rel="stylesheet" href="css/global.css" />
    <link type="text/css" rel="stylesheet" href="css/jquery.jgrowl.css" />
    <script src="http://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4");
        google.load("jqueryui", "1.5");
    </script>
    <c:if test="${isKAuth}">
    <script src='./_ah/channel/jsapi'></script>
    <script src="js/object-prototype.js" type="text/javascript"></script>
    <script src="js/jquery.pack.js" type="text/javascript"></script>
    <script src="js/ajax-main.js" type="text/javascript"></script>
    <script src="js/jquery.jgrowl_google.js" type="text/javascript"></script>
    <script type="text/javascript">
    <!--
    // Global
    channelToken = "${f:h(channelToken)}";
    g_maxId = ${f:h(maxId)};
    //-->
    </script>
    </c:if>
    <title>K-Witches</title>
</head>
<body>
    <jsp:include page="user_account.jsp" />
    <c:if test="${'LOGIN' == isLogin}">
        <script type="text/javascript" src="js/swfobject.js"></script>
        <script type="text/javascript" src="js/soundapi.js"></script>
        <img src="images/kw-logo.png"/>
        <br />
        <p>守りたいから私は飛ぶ！！パンツじゃないから恥ずかしくないもん！</p>
        <c:if test="${isKAuth}">
        <div class="postlocate">
            <form id="post_form" method="post" enctype="multipart/form-data" action="./sign" target="dammy">
                <textarea rows="1" name="comment" id="textarea" class=""></textarea><br>
                <div class="buttons">
                    <button id="post_button" class="positive" href="javascript:void(0)">
                        <img alt="" src="images/apply2.png">
                        送信
                    </button>
                      <!--
                    <a id="preview_link" class="regular" href="javascript:void(0)">
                        <img alt="" src="images/preview.png">
                        プレビュー
                    </a>
                -->
                   <input type="file" id="file" name="file"/>
                
                   <br />
              </div>
            </form>
            <iframe id="dammy" name="dammy" style="display:none"></iframe>
        </div>
        <div id="gadgets">
            <input id="search" type="text" class="clearField" value="検索" />
        </div>
        <div id="articles"></div>
        <div id="pagelink"></div>
        </c:if>
    </c:if>
</body>
</html>
