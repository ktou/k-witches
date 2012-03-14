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

var PixivThumnail = function() {
    this.initialize.apply(this, arguments);
}

PixivThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.execute = function() {
        var _this = this;

        $(this.domPattern).each(function() {
            var illust_id = $(this).attr("data-pixivillustid");
            var pixiv_detail_url = "http://iphone.pxv.jp/iphone/illust.php?illust_id="+illust_id;
            var dom = this;
            $.ajax({
                type: "GET",
                url: "/util/fetchurl",
                data: {url: pixiv_detail_url},
                dataType: "text",
                success: function(data) {
                    var illust_url_480 = data.match("http://[^:]*480mw.jpg");
                    var illust_url_128 = data.match("http://[^:]*128x128.jpg");
                    $(dom).empty().append(
                        $("<a/>").attr({
                            "href" : illust_url_480,
                            "target" : "_blank"
                        }).append(
                            $("<img/>").attr("src", illust_url_128)
                        )
                    );
                }
            });
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }

}).apply(PixivThumnail.prototype);

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

var SoundCloudThumnail = function() {
    this.initialize.apply(this, arguments);
}

SoundCloudThumnail.prototype = new DomModifier();

(function() {
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
    },

    this.execute = function() {
        var _this = this;

        $(this.domPattern).each(function() {
            var url = $(this).attr("url");
            var dom = this;
            $.ajax({
                type: "GET",
                url: "http://soundcloud.com/oembed",
                data: {url: url, format: "json"},
                dataType: "json",
                success: function(data) {
                    $(dom).empty().append(data.html);
                }
            });
            $(this).removeClass(_this.domPattern.split(".")[1]);
        });
    }

}).apply(SoundCloudThumnail.prototype);

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
