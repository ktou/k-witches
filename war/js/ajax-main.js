
$(function(){
    if (!$.browser.safari) {
       $("#aud").remove();
    }
    $('.clearField').clearField();

    var article = new Article();
    article.drawArticles(1, 30);
    var pageFooter = new PagingFooter();
    pageFooter.setMaxId(g_maxId);
    pageFooter.drawPageLink();
    var channel = new goog.appengine.Channel(channelToken);
    var socket = channel.open();
    var isFileUpload = false;
    var location = new Location("#locationsetting");
    var liveChecker = new LiveChecker("#liveChecker");

    socket.onmessage = function(msg) {
        var data = $.parseJSON(msg.data);
        if (data.type == "sign") {
            if (g_maxId == data.content.id) {
                return;
            }
            Api.playSound('../swf/notify_sound1.mp3');
            g_maxId = data.content.id;
            pageFooter.setMaxId(g_maxId);
            pageFooter.drawPageLink();
            $("#articles").prepend(article.createDom(data.content));
            article.rewritePageTitle(data.content.id, data.content.name);
            article.decorate();
        } else if (data.type == "booth_in") {
            Api.playSound('../swf/notify_sound2.mp3');
            $.jGrowl(data.content + "さんが円環の理に導かれました", {
               speed: 'fast'
            });
            liveChecker.appendOrUpdate(data.content);
        } else if (data.type == "live") {
            liveChecker.appendOrUpdate(data.content);
        }
    };

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

function createResDom(resAnchor) {
    var entryDom = $(resAnchor).parent();
    var resDom = entryDom.find(".res");
    if ($(resAnchor).next("blockquote").html() != null) {
        $(resAnchor).next("blockquote").toggle();
        return;
    }
    var resNumber = $(resAnchor).attr("data-resnum");
    var param = $.param({
        res_num: resNumber
    });
    var bq = $("<blockquote/>").attr("class","res_quote");
    bq.insertAfter(resAnchor);
    bq.html(Api.getLoadingImage());

    $.getJSON(
        "./json?" + param,
        function(data) {
           var artcle = new Article();
           bq.html(artcle.createDom(data.articles[0]).removeClass("article"));
           artcle.decorate();
        }
    );
}

var Res = function() {}
Res.appendTextarea = function(resNumber) {
    var textarea = $("#textarea").get(0);
    if (textarea.value == "\n") {
        textarea.value = "";
    }
    $("#textarea").get(0).value += ">>%d\n".replace("%d", resNumber);
}

var PagingFooter = function() {
    this.initialize.apply(this, arguments);
}

PagingFooter.prototype = {
    initialize : function() {
        this.currentPage = 1;
        this.maxPage = 1;
        this.maxId = 0;
        this.pageLength = 30;
    },

    setMaxId : function(maxId) {
        this.maxId = maxId;
        this.maxPage = Math.floor(this.maxId / this.pageLength + 1);
    },

    drawPageLink : function() {
        var _this = this;
        var pagediv = $("#pagelink").addClass("sabrosus");
        var nextLink = function(isNext) {
            $(pagediv).append($("<a/>").addClass("other").click(function(){
                _this.movePage(_this.currentPage + (isNext ? 1 :-1));
            }).text(isNext ? ">>" : "<<").css("cursor","pointer"));
        };
        $("#pagelink").empty();
        if (this.currentPage > 1) {
            nextLink(false);
        } else {
            $(pagediv).append($("<span/>").addClass("disabled").text("<<"));
        }
        for (var i = 1; i <= this.maxPage; i++){
            if (i != this.currentPage) {
                $(pagediv).append($("<a/>").addClass("other").click(function(){
                    _this.movePage(parseInt(this.innerHTML));
                }).text("" + i).css("cursor","pointer"));
            } else {
                $(pagediv).append($("<span/>").addClass("current").text("" + i));
            }
            if ((i % 25 == 0 && i != 0 && i < 101)||(i%20 == 0 && i != 0 && i > 101)) {
                $(pagediv).append($("<br/>"));
            }
        }
        if (this.currentPage < this.maxPage) {
            nextLink(true);
        } else {
            $(pagediv).append($("<span/>").addClass("disabled").text(">>"));
        }
    },

    movePage : function(moveTo){
        this.currentPage = moveTo;
        this.maxId = 0;
        $("#articles").empty();
        new Article().drawArticles(this.currentPage, this.pageLength);
        this.drawPageLink();
    }
}

var Article = function() {
    var self = arguments.callee;
    if (self.instance == null) {
        this.initialize.apply(this, arguments);
        self.instance = this;
    }
    return self.instance;
}

Article.prototype = {
    initialize : function() {
    },

    _getIconUrl: function(data) {
        var icon = data.icon;
        return "user/icon?_=" + icon;
    },

   createDom : function(e) {
        var icon_url = this._getIconUrl(e);
        var article = $("<div/>").addClass("article").append(
            $("<div/>").addClass("photo").append(
                $("<img/>").attr("src", icon_url)
            )
        ).append(
            $("<div/>").addClass("entry").attr("data-entrynumber", e.id
            ).append(
                $("<div/>").addClass("title").append(
                    $("<a/>").addClass("number").attr("href","#").text(e.id).click(function() {
                        Res.appendTextarea(e.id);
                        $("#textarea").focus().addClass("expand");
                    })
                ).append(" : ").append(
                    $("<a/>").addClass("name").attr("href", "#").text(decodeURL(e.name))
                )
            ).append(
                $("<div/>").addClass("body").html(
                    decodeURL(e.comment).replace(/\n/g, "<br/>")
                ).append(
                    this._getFileDom(e)
                )
            ).append(
                $("<div/>").addClass("bottom").attr("align", "right").append(
                    $("<a/>").addClass("time").attr("href", "#").text(e.date)
                ).append(
                    $("<span/>").addClass("location").attr("href", "#").text(decodeURL(e.location == "" ? "" : " from "+e.location))
                )
            ).append(
                $("<div/>").addClass("res")
            )
        );
        return article;
    },

    drawArticles : function(page, limit) {
        var _this = this;
        $("#articles").empty().append(Api.getLoadingImage());
        var param = $.param({
            page : page,
            limit : limit
        });
        $.ajax({
            type: "GET",
            url: "./json?" + param,
            dataType: "json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader("If-Modified-Since", "Thu, 01 Jun 1970 00:00:00 GMT");
            },
            success: function(data) {
                $("#articles").empty();
                if (!data.articles[0]) return;
                data.articles.forEach(function(e) {
                    $("#articles").append(_this.createDom(e));
                });
                _this.rewritePageTitle(data.articles[0].id, data.articles[0].name);
                _this.decorate();
            }
        });
    },

    search : function(word, page, limit) {
        var _this = this;
        $("#articles").empty().append(Api.getLoadingImage());
        $.ajax({
            type: "GET",
            url: "./search?word=" + word,
            dataType: "json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader("If-Modified-Since", "Thu, 01 Jun 1970 00:00:00 GMT");
            },
            success: function(data) {
                $("#articles").empty();
                data.articles.forEach(function(e) {
                    $("#articles").append(_this.createDom(e));
                });
                _this.decorate();
            }
        });
    },

    decorate : function() {
        var lazyScriptLoader = new LazyScriptLoader();
        var decoratorer = [
            new NicoThumnail("div.new_nico_thumb", lazyScriptLoader),
            new NicoTags("div.new_nico_tags"),
            new GistPreview("div.new_gist_preview", lazyScriptLoader),
            new TwitterThumnail("div.new_twitter_thumb"),
            new TumblrThumnail("div.new_tumblr_thumb"),
            new PixivThumnail("div.new_pixiv_thumb"),
            new InstagrThumnail("div.new_instagr_thumb"),
            new NormalLink("div.new_link")
        ];
        for (var i = 0, length = decoratorer.length; i < length; i++) {
            decoratorer[i].execute();
        }
        lazyScriptLoader.execute();
    },

    rewritePageTitle : function(number, name) {
    	name = name.replace(/\+/g,"%20");
    	document.title = "KEITO KAMADA " + number + " : " + decodeURIComponent(name);
    },

    _getFileDom : function(data) {
        var fileDom = "";
        if (!data.file.filename) return "";
        var param = $.param({
            key : data.file.key,
            version : data.file.version
        });
        if (data.file.filename.match(/\.(jpeg|jpg|png|gif|bmp)$/i)) {
            fileDom = $("<blockquote/>").addClass("file")
                .append(
                    decodeURI(data.file.filename) + " " + data.file.length + "KB"
                ).append(
                    $("<br/>")
                ).append(
                    $("<a/>").attr({
                        "href":"./file?" + param,
                        "target":"_blank"
                    }).append(
                        $("<img/>").attr({
                            "src":"./file?" + param,
                            "width":"200"
                        }).css("border", "1px solid")
                    )
                );
        } else {
            fileDom = $("<blockquote/>").addClass("file")
                .append(
                    $("<a/>").attr({
                        "href":"./file?" + param,
                        "target":"_blank"
                    }).append(
                        decodeURI(data.file.filename)
                    )
                ).append(
                    " " + data.file.length + "KB"
                )
        }
        return fileDom;
    }
}

