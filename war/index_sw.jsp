<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" href="./images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="icon" href="./images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link type="text/css" rel="stylesheet" href="css/global_sw.css" media="screen and (min-device-width: 481px)">
    <link type="text/css" rel="stylesheet" href="css/iphone.css" media="only screen and (max-device-width: 480px)">
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
            <audio controls id="aud"><source src="./swf/notify_sound1.mp3"></audio>
        </div>
        <div id="articles"></div>
        <div id="pagelink"></div>
        <script src="http://l.yimg.com/a/i/us/pps/imagebadge_1.3.js">{"pipe_id":"96eb667d4f5b69673fb38eda4e4e18cb","_btype":"image","width":"900px","height":"800px","pipe_params":{"username":"thrakt"}}</script>
        </c:if>
    </c:if>
</body>
</html>