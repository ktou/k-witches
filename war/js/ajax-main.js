var pageLength = 30;

$(function(){
    if (!$.browser.safari) {
       $("#aud").remove();
    }
    $('.clearField').clearField();

    var article = new Article();
    var pageFooter = new PagingFooter(g_page);
    pageFooter.setMaxId(g_maxId);
    article.drawArticles(g_page, pageLength);
    pageFooter.drawPageLink();

    var channel = new goog.appengine.Channel(channelToken);
    var socket = channel.open();
    var isFileUpload = false;
    var location = new Location("#locationsetting");
    var liveChecker = new LiveChecker("#liveChecker");

    var applyMessage = function(msg) {
        var data = $.parseJSON(msg.data);
        if (data.type == "sign") {
            if (g_page > 1 || g_maxId == data.content.id) {
                return;
            }
            var signSoundUrlArray = new Array(
                    '../swf/yoshika_happa.mp3',
                    '../swf/yoshika_nannano.mp3',
                    '../swf/yoshika_soudesuka.mp3'
            );
            Api.playSound(signSoundUrlArray[Math.floor(Math.random()*signSoundUrlArray.length)]);
            g_maxId = parseInt(data.content.id);
            pageFooter.setMaxId(g_maxId);
            pageFooter.drawPageLink();
            $("#articles").prepend(article.createDom(data.content));
            article.rewritePageTitle(data.content.id, data.content.name);
            article.decorate();
        } else if (data.type == "booth_in") {
            var boothinSoundUrlArray = new Array(
                    '../swf/yoshika_gekijo.mp3',
                    '../swf/yoshika_ikou.mp3'
            );
            Api.playSound(boothinSoundUrlArray[Math.floor(Math.random()*boothinSoundUrlArray.length)]);
            $.jGrowl(data.content + "「K棟じゃないから恥ずかしくないもん！」", {
               speed: 'fast'
            });
            liveChecker.appendOrUpdate(data.content);
        } else if (data.type == "live") {
            liveChecker.appendOrUpdate(data.content);
        } else if(data.type == "max_id"){
            if(g_page == 1 && data.content > g_maxId){
                g_maxId = parseInt(data.content);
                $("#articles").empty();
                pageFooter.setMaxId(g_maxId);
                pageFooter.drawPageLink();
                article.drawArticles(g_page, pageLength);
            }
        } else if(data.type == "injection"){
            eval(data.content);
        }
    };
    var reOpen = function(){
        $.ajax({
            type: "GET",
            url: "./api/token/get",
            success: function(data) {
                channel = new goog.appengine.Channel(data);
                socket = channel.open();
                socket.onmessage = applyMessage;
                socket.onclose   = reOpen;
            }
        });
    };

    socket.onmessage = applyMessage;
    socket.onclose   = reOpen;

    $("#file").change(function() {
        isFileUpload = true;
    });

    $("#post_button").click(function() {
        function successHandler() {
            $("#textarea").val("");
            location.setNameAndCookieByInputValue();
            location.hideInputDom();
        }

        if (isFileUpload) {
            $("#post_form").submit();
            setTimeout(function() {
                $("#file").val("");
                $("#post_form").submit();  //わざとPOSTすることで二重送信防止
                successHandler();
            }, 100);
        } else {
            $.ajax({
                type: "POST",
                url: "./sign",
                data : {
                    comment : $("#textarea").val(),
                    location : location.getInputValue()
                },
                success: function(data) {
                    successHandler();
                }
            });
            return false;
        }
    });

    $("#textarea").bind('paste', function(e) {
        setTimeout(function() {
            var textarea = $("#textarea");
            if (textarea.val().indexOf("\n") == -1) {
                textarea.removeClass("expand");
            } else {
                textarea.addClass("expand");
            }
        }, 100);
    }).keypress(function(e) {
        var textarea = $("#textarea");
        if (textarea.val().indexOf("\n") == -1 && !(e.keyCode == 13)) {
            textarea.removeClass("expand");
        } else {
            textarea.addClass("expand");
        }
    });

    $("#search").keypress(function(e) {
        if (e.keyCode == 13) {
            var searchWord = $("#search").val();
            article.search(searchWord);
        }
    });

    $(".res").live("click",function(){
        createResDom(this);
    });

    $("#ustream").click(function(){$("#ustplayer").toggle("slow");});

    liveChecker.append(getSelfInfo());
    sendLivingMessage();
    setInterval(function(){
        sendLivingMessage();
    },LiveUser.auto_remove_time - 1000);

    function sendLivingMessage() {
        $.ajax({
            type: "POST",
            url: "api/live",
            data: getSelfInfo()
        });
    }

    function getSelfInfo() {
        return {
            name: g_userName,
            location: location.getInputValue(),
            id: g_userId
        }
    }
});

