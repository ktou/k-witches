<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <base href="/" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="icon" href="/images/favicon.ico" type="image/vnd.microsoft.icon" />
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="css/global.css">
    <link type="text/css" rel="stylesheet" href="css/iphone.css" media="only screen and (max-device-width: 480px)">
    <link type="text/css" rel="stylesheet" href="css/jquery.jgrowl.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <script src="js/lib/underscore-min.js" type="text/javascript"></script>
    <script src="js/lib/backbone-min.js" type="text/javascript"></script>
    <c:if test="${isKAuth}">
    <script src="./_ah/channel/jsapi"></script>
    <script src="js/util/object-prototype.js" type="text/javascript"></script>
    <script src="js/dom-modifier.js" type="text/javascript"></script>
    <script src="js/place.js" type="text/javascript"></script>
    <script src="js/livechecker.js" type="text/javascript"></script>
    <script src="js/article.js" type="text/javascript"></script>
    <script src="js/pagefooter.js" type="text/javascript"></script>
    <script src="js/channelcontrol.js" type="text/javascript"></script>
    <script src="js/ajax-main.js" type="text/javascript"></script>
    <script src="js/jquery/jquery.jgrowl_google.js" type="text/javascript"></script>
    <script src="js/jquery/jquery.cookie.js" type="text/javascript"></script>
    <script src="js/jquery/jquery.oembed.min.js" type="text/javascript"></script>
    <script src="js/util/tinycon.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    <!--
    // Global
    channelToken = "${f:h(channelToken)}";
    g_maxId = ${f:h(maxId)};
    g_userName = "${f:h(userName)}";
    g_userId = "${f:h(userId)}";
    g_page = "${f:h(page)}";
    //-->
    </script>
    </c:if>
    <title>けいとうヤルカ</title>
</head>
<body>
    <jsp:include page="user_account.jsp" />
    <c:if test="${'LOGIN' == isLogin}">
        <script type="text/javascript" src="js/util/swfobject.js"></script>
        <script type="text/javascript" src="js/util/soundapi.js"></script>
        <a href="/"><img id="logo" src="images/madomov-logo.png"/></a>
        <br />
        <p>税率が騰がったとき、会計は新たなる結末へ。Amazonは<a href="http://www.amazon.co.jp/gp/gc?ie=UTF8&tag=thrakt-22&linkCode=ur2&camp=247&creative=1211" target="_blank">こちら</a>から。</p>
        <c:if test="${isKAuth}">
        <div id="postlocate" class="backpartition">
            <form id="post_form" method="post" enctype="multipart/form-data" action="./sign" target="dammy">
                <textarea rows="1" name="comment" id="textarea" class=""></textarea><br>
                <div class="buttons">
                    <button id="post_button" class="positive" href="javascript:void(0)">
                        <img alt="" src="images/apply2.png">
                        Panzerschießen!
                    </button>
                      <!--
                    <a id="preview_link" class="regular" href="javascript:void(0)">
                        <img alt="" src="images/preview.png">
                        プレビュー
                    </a>
                -->
                   <input type="file" id="file" name="file"/>

                   <span id="locationsetting">
                       location :
                       <span></span>
                       <input type="text" name="location" style="display: none;"/>
                   </span>
                   <br />
              </div>
            </form>
            <iframe id="dammy" name="dammy" style="display:none"></iframe>
        </div>
        <div id="gadgets">
            <input id="search" type="text" class="clearField" value="検索" />
        </div>
        <div id="liveChecker" class="backpartition gadget">
          <h4>行動中列機</h4>
          <span></span>
        </div>
        <% if(request.getHeader("User-Agent").indexOf("Mobile") == -1){ %>
        <div id="ustream" class="backpartition gadget">
            <h4>全国K棟道大会中継</h4>
            <div id="ustplayer" style="display: none;">
                <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                    width="480" height="296">
                    <param name="flashvars"
                        value="autoplay=false&amp;brand=embed&amp;cid=${f:h(ustId)}&amp;locale=ja_JP&amp;v3=1" />
                    <param name="allowfullscreen" value="true" />
                    <param name="allowscriptaccess" value="always" />
                    <param name="movie" value="http://www.ustream.tv/flash/viewer.swf" />
                    <embed
                        flashvars="autoplay=false&amp;brand=embed&amp;cid=${f:h(ustId)}&amp;locale=ja_JP&amp;v3=1"
                        width="480" height="296" allowfullscreen="true"
                        allowscriptaccess="always"
                        src="http://www.ustream.tv/flash/viewer.swf"
                        type="application/x-shockwave-flash" />
                </object>
            </div>
        </div>
        <% } %>
        <div id="articles" class="backpartition"></div>
        <div id="pagelink" class="pagination"></div>
        </c:if>
    </c:if>
</body>
</html>
