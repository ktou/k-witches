var pageLength = 30;

var g_observer;

var KtouObserver = function(){
    this.initialize.apply(this, arguments);
}

KtouObserver.prototype = {
    initialize: function(){
        this.article = new Article();
        this.pageFooter = new PagingFooter(g_page);
        this.place =  new Place("#locationsetting");
        this.liveChecker = new LiveChecker("#liveChecker");
        this.channelControl = new ChannelControl(this);

        var article = this.article;
        var pageFooter = this.pageFooter;
        var place = this.place;
        var liveChecker = this.liveChecker;

        pageFooter.setMaxId(g_maxId);
        article.drawArticles(g_page, pageLength);
        pageFooter.drawPageLink();

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
                location: place.getInputValue(),
                id: g_userId
            }
        }
    }

}

$(function(){
    if (!$.browser.safari) {
       $("#aud").remove();
    }
    $('.clearField').clearField();

    g_observer = new KtouObserver();
    var article = g_observer.article;
    var pageFooter = g_observer.pageFooter;
    var place = g_observer.place;
    var liveChecker = g_observer.liveChecker;

    var isFileUpload = false;

    $("#file").change(function() {
        isFileUpload = true;
    });

    $("#post_button").click(function() {
        function successHandler() {
            $("#textarea").val("");
            place.setNameAndCookieByInputValue();
            place.hideInputDom();
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
                    location : place.getInputValue()
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

});

