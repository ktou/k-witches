var pageLength = 30;

var article;
var pageFooter;
var liveChecker;

$(function(){
    if (!$.browser.safari) {
       $("#aud").remove();
    }
    $('.clearField').clearField();

    article = new Article();
    pageFooter = new PagingFooter(g_page);

    pageFooter.setMaxId(g_maxId);
    article.drawArticles(g_page, pageLength);
    pageFooter.drawPageLink();

    var channelControl = new ChannelControl();

    var isFileUpload = false;
    var location = new Location("#locationsetting");
    liveChecker = new LiveChecker("#liveChecker");


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

