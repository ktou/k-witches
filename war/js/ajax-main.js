
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
            $.jGrowl(data.content + "さんがブースインしました", {
               speed: 'fast'
            });
        }
    };

    $("#file").change(function() {
        isFileUpload = true;
    });

    $("#post_button").click(function() {
        if (isFileUpload) {
            $("#post_form").submit();
            setTimeout(function() {
                $("#textarea").val("");
                $("#file").val("");
                $("#post_form").submit();  //わざとPOSTすることで二重送信防止
            }, 100);
        } else {
            $.ajax({
                type: "POST",
                url: "./sign",
                data : {
                    comment : $("#textarea").val()
                },
                success: function(data) {
                    $("#textarea").val("");
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

var DomModifier = function() {
    this.initialize.apply(this, arguments);
}

DomModifier.prototype = {
    initialize : function(domPattern) {
        this.domPattern = domPattern;
    },

    execute : function() {}
}

var NicoThumnail = function() {
    this.initialize.apply(this, arguments);
}

NicoThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern, lazyScriptLoader) {
        DomModifier.prototype.initialize.apply(this, arguments);
        this.params = [];
        this.count = 0;
        this.lazyScriptLoader = lazyScriptLoader;
    },

    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function() {
            var nico_video_id = $(this).attr("data-nicovideo");
            var src = "http://ext.nicovideo.jp/thumb_watch/" + nico_video_id;
            _this.lazyScriptLoader.append({
                "dom" : this,
                "src_url" : src,
                "dom_pattern": _this.domPattern
            });
        });
    }

}).apply(NicoThumnail.prototype);


var NicoTags = function() {
    this.initialize.apply(this, arguments);
}

NicoTags.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.getNicoTags = function(dom, nico_video_id) {
        $.ajax({
            url : 'http://voidy21.appspot.com/nicotag',
            dataType : 'jsonp',
            data : {
                video_id : nico_video_id
            },
            jsonp: 'callback',
            success : function(json) {
                if (!json || !json.tags) return;
                var nicoDom = $(dom);
                nicoDom.empty();
                for(var i = 0 , n = json.tags.length; i < n; i++){
                    var nico_tag = json.tags[i].tag;
                    var link_tag = $('<a/>')
                        .attr('href','http://www.nicovideo.jp/tag/' + nico_tag)
                        .css({'font-size':'13px','color':'#000080'})
                        .text(decodeURIComponent(nico_tag));
                    nicoDom.append(link_tag);
                    if (i%4 == 0 && i != 0) {
                        nicoDom.append('<br/>');
                    } else if(i != n-1) {
                        nicoDom.append(' ');
                    }
                }
            },
            error : function(){}
        });
    },
    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function() {
            var nico_video_id = $(this).attr("data-nicovideo");
            _this.getNicoTags(this,nico_video_id);
            $(this).removeClass(_this.domPattern);
        });
    }

}).apply(NicoTags.prototype);

var NormalLink = function() {
    this.initialize.apply(this, arguments);
}

NormalLink.prototype = new DomModifier();

(function() {
    this.initialize = function() {
        DomModifier.prototype.initialize.apply(this, arguments);
        this.tagColor = ['#993900', '#C84B00', '#F25B00', '#FF6000',
                         '#FF8135', '#FFA975', '#FFC6A4', '#FFDDC8'];
        this.tagLength = this.tagColor.length;
    },

    this._showTagCloud = function(dom, tagCloud) {
        for(var i = 0; i < this.tagLength; i++){
            var link_tag = $('<a/>');
            if(!tagCloud[i].tag) continue;
            $(link_tag)
               .attr('href', 'http://b.hatena.ne.jp/t/' + encodeURI(tagCloud[i].tag))
               .css({
                   'font-size' : '13px',
                   'color' : this.tagColor[i],
                   'text-decoration' : 'none'
                })
               .text(tagCloud[i].tag);
            $(dom).append(link_tag);
            if (i != this.tagLength - 1) {
                $(dom).append(' ');
            }
        }
    },

    this._getTagCloud = function(json) {
        var hatena_tags = {};
        $.each(json.bookmarks,function() {
            $.each(this.tags,function() {
                var count = hatena_tags[this];
                typeof count != 'undefined' ?
                    hatena_tags[this]++ : hatena_tags[this] = 1;
            });
        });
        var tagCloud = {};
        for (var j= 0; j < this.tagLength; j++) {
            tagCloud[j] = {};
            tagCloud[j].count = 0;
            for (var i in hatena_tags) {
                if (tagCloud[j].count < hatena_tags[i]) {
                    tagCloud[j].count = hatena_tags[i];
                    tagCloud[j].tag = i;
                }
            }
            hatena_tags[tagCloud[j].tag] = 0;
        }
        return tagCloud;
    },

    this._getTitleAndTagCloud = function(dom, url) {
        var _this = this;
        $.ajax({
            url : 'http://b.hatena.ne.jp/entry/jsonlite/',
            dataType : 'jsonp',
            data : {
                url : url
            },
            jsonp : 'callback',
            success : function(json){
                if(!json || !json.title) return;
                var prevDom = $("<div/>").css('color', 'green').text(json.title);
                var nextDom = $("<div/>");
                $(dom).prepend(prevDom);
                $(dom).append(nextDom);
                var api = new ShowTitleApi(url, json);
                var tagCloud = _this._getTagCloud(json);
                _this._showTagCloud(nextDom, tagCloud);
                api.setTooltip(dom);
            },
            error : function(){}
        });
    },

    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function() {
            var url = $(this).find("a").attr("class","link").attr("href").replace("/href?location=", "");
            _this._getTitleAndTagCloud(this, url);
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }
}).apply(NormalLink.prototype);

var GistPreview = function() {
    this.initialize.apply(this, arguments);
}

GistPreview.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern, lazyScriptLoader) {
        DomModifier.prototype.initialize.apply(this, arguments);
        this.params = [];
        this.count = 0;
        this.lazyScriptLoader = lazyScriptLoader;
     },

    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function(){
            var gist_id = $(this).attr("data-gistid");
            var src = "https://gist.github.com/%d.js".replace("%d", gist_id);
            _this.lazyScriptLoader.append({
                "dom" : this,
                "src_url" : src,
                "dom_pattern": _this.domPattern
            });
        });
    }

}).apply(GistPreview.prototype);

var TumblrThumnail = function() {
    this.initialize.apply(this, arguments);
}

TumblrThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.execute = function() {
        var _this = this;

        $(this.domPattern).each(function() {
            var tumblr_id = $(this).attr("data-tumblrid");
            var post_id = $(this).attr("data-postid");
            var tumblr_api_url = "http://%d.tumblr.com/api/read/json".replace("%d", tumblr_id);
            var dom = this;
            $.ajax({
                type: "GET",
                url: tumblr_api_url,
                dataType: "jsonp",
                data : {
                    id : post_id
                },
                success: function(data) {
                    var post = data.posts[0];
                    if (post.type == "photo") {
                        var photo_thumbnail_url = post["photo-url-250"];
                        var photo_url = post["photo-url-1280"];
                        var photo_caption = post["photo-caption"];
                        $(dom).empty().append(
                            $("<a/>").attr({
                                "href" : photo_url,
                                "target" : "_blank"
                            }).append(
                                $("<img/>").attr("src", photo_thumbnail_url)
                            ).append(
                                $("<div/>").addClass("photo_caption").html(photo_caption)
                            )
                        );
                    }
                }
            });
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }

}).apply(TumblrThumnail.prototype);

var TwitterThumnail = function() {
    this.initialize.apply(this, arguments);
}

TwitterThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.execute = function() {
        var _this = this;

        $(this.domPattern).each(function() {
            var twitterId = $(this).attr("data-twitter_id");
            var statusNum = $(this).attr("data-status_num");
            var dom = this;
            $.ajax({
                type: "GET",
                url: "http://voidy21.appspot.com/twit_status",
                dataType: "jsonp",
                jsonp: 'callback',
                data : {
                    twitter_id : twitterId,
                    status_num : statusNum
                },
                success: function(data) {
                    if(!data) return;
                    $(dom).empty();
                    var cite = $('<cite/>').html(
                        "<a href='http://twitter.com/%d/'>%d</a> on ".replace(/%d/g, twitterId) +
                        decodeURI(data.date)
                    );
                    var comment = $('<span/>').attr('class','twitter')
                        .html(decodeURI(data.entry));
                    var twit_entry = $('<blockquote/>').attr('class','twitter')
                        .attr('style','background-image: url(' +
                        decodeURI(data.image) +
                        ');background-position: left center;')
                        .append(comment).append('<br/>').append(cite);
                    $(dom).append(twit_entry);
                }
            });
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }

}).apply(TwitterThumnail.prototype);

var InstagrThumnail = function() {
    this.initialize.apply(this, arguments);
}

InstagrThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.execute = function() {
        var _this = this;

        $(this.domPattern).each(function() {
            var url = $(this).attr("data-url");
            var api_url = "http://instagr.am/api/v1/oembed";
            var dom = this;
            $.ajax({
                type: "GET",
                url: api_url,
                dataType: "jsonp",
                data : {
                    url : url,
                    maxwidth : 450
                },
                jsonp: 'callback',
                success: function(data) {
                    var photo_thumbnail_url = data.url;
                    var photo_caption = data.title;
                    $(dom).empty().append(
                        $("<a/>").attr({
                            "href" : url,
                            "target" : "_blank"
                        }).append(
                            $("<img/>").attr("src", photo_thumbnail_url)
                        ).append(
                            $("<div/>").addClass("photo_caption").html(photo_caption)
                        )
                    );
                }
            });
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }

}).apply(InstagrThumnail.prototype);

var ShowTitleApi = function() {
    this.initialize.apply(this, arguments);
}

ShowTitleApi.prototype = {
    initialize : function(url, json) {
        this.url = url;
        this.json = json;
    },

    _getThumbnailDom : function() {
        var hatena_screen =  this.json.screenshot.replace('/120x90/', '/200x150/');
        var thumbnail = $('<div/>');
        $(thumbnail).addClass('url_title').append(this.json.title.substr(0,60))
            .append($('<img/>').attr('src', hatena_screen)).append($('<br/>'))
            .append(Api.getHatebuImage(this.url))
            .append(Api.getLdclipImage(this.url));
        return thumbnail;
    },

    setTooltip : function(dom) {
        var _this = this;
        $(dom).find("a").tooltip({
            bodyHandler: function() {
                return _this._getThumbnailDom();
            },
            showURL: false,
            track: true,
            delay: 0,
            showURL: false,
            showBody: ' - ',
            fade: 250
        });
    },

}

var Api = function() {}

Api.getHatebuImage = function(url) {
    var api = 'http://b.hatena.ne.jp/entry/image/';
    return $('<img/>').attr('src', api + url);
}

Api.getLdclipImage = function(url) {
    var api = 'http://image.clip.livedoor.com/counter/medium/';
    return $('<img/>').attr('src', api + url);
}

Api.getLoadingImage = function() {
    return $("<img/>").attr("src", "../images/ajax-loader.gif");
}

Api.playSound = function(url) {
    if (soundapi && soundapi.playFile) {
        soundapi.playFile(url);
    }
}

var LazyScriptLoader = function() {
    this.initialize.apply(this, arguments);
}

LazyScriptLoader.prototype = {
    initialize : function() {
        this.params = [];
        this.count = 0;
    },

    append : function(params) {
        $(params.dom).append(Api.getLoadingImage());
        this.params.push({
            "dom" : params.dom,
            "src_url" : params.src_url,
            "dom_pattern": params.dom_pattern
        });
        this.count++;
    },

    _execute : function(count) {
        var _this = this;
        var src = this.params[count].src_url;
        var dom = this.params[count].dom;
        var dom_pattern = this.params[count].dom_pattern;
        var alts = []; // document.writeの内容を入れておく配列を準備
        var d = document;
        d._write = document.write; // オリジナルはコピーしておく
        document.write = function(s){ alts.push(s); }; // d.writeを新たに定義
        $.getScript(src, function(){
            var write = alts.join("");
            $(dom).html(write).removeClass(dom_pattern.split(".")[1]); // 指定した場所に流し込む
            document.write = d._write; // d.writeを元の定義に戻しておく
            if (++count < _this.count) {
                _this._execute(count);
            }
        });
    },

    execute : function() {
        if (this.count != 0) {
            this._execute(0);
        }
    }
}
