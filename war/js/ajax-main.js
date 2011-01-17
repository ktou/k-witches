$(function(){

    $("#post_button").click(function(){
        $("#post_form").submit();
        setTimeout(function() {
            $("#textarea").val("");
            $("#file").val("");
            article.drawArticles();
            $("#post_form").submit();  //わざとPOSTすることで二重送信防止
        },100);
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
    });

    $("#textarea").keypress(function(e){
        var textarea = $("#textarea");
        if (textarea.val().indexOf("\n") == -1 && !(e.keyCode == 13)) {
            textarea.removeClass("expand");
        } else {
            textarea.addClass("expand");
        }
    });

    var article = new Article();
    article.drawArticles();


});

var Article = function() {
    var self = arguments.callee;
    if(self.instance == null){
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
        
        var article = $("<div/>").append(
            $("<div/>").addClass("photo").append($("<img/>").attr("src",icon_url))
        ).append(
            $("<div/>").addClass("entry").attr("data-entrynumber",e.id).append(
                $("<div/>").addClass("creater").attr("align","right").append(
                    $("<a/>").addClass("name").attr("href","#").text(e.name)
                    )
            ).append(
                $("<div/>").addClass("title").append($("<a/>").attr("href","#").text(e.id))
            ).append(
                $("<div/>").addClass("body").html(decodeURL(e.comment).replace(/\r\n?/g,"<br />"))
               )
            .append(
                $("<div/>").addClass("bottom").attr("align","right").append(
                    $("<a/>").addClass("time").attr("href","#").text(e.date)
                    )
             ).append($("<div/>").addClass("res")
             )
        ).append(
            $("<div/>").addClass("shadow")
          );
        return article;
    },
    
    drawArticles : function(num,page) {
        var _this = this;
        $("#articles").html("");
        $.ajax({
            type: "GET",
            url: "./json",
            dataType: "json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader("If-Modified-Since", "Thu, 01 Jun 1970 00:00:00 GMT");
            },
            success: function(data) {
              data.articles.forEach(function(e) {
                  $("#articles").append(_this.createDom(e));
                });
                    
             _this.decorate();
            }
        });
    },  
    
    decorate : function() {
        var decoratorer = [
            new NicoThumnail("div.new_nico_thumb"),
            new NicoTags("div.new_nico_tags"),
            new NormalLink("div.new_link")
        ];
        for (var i = 0,length = decoratorer.length; i < length; i++) {
            decoratorer[i].execute();
        }
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
    this.initialize = function(domPattern) {
        DomModifier.prototype.initialize.apply(this, arguments);
        this.params = [];
        this.count = 0;
    },

    this.append = function(dom,nico_video_id) {
        $(dom).append($("<img/>").attr("src","../../images/ajax-loader.gif"));
        this.params.push({"dom":dom,"nico_video_id":nico_video_id});
        this.count++;
    },

    this.show_nico_thumbnail = function(count) {
        var _this = this;
        var alts = []; // document.writeの内容を入れておく配列を準備 
        var d = document;
        d._write = document.write; // オリジナルはコピーしておく
        document.write = function(s){ alts.push(s);}; // d.writeを新たに定義
        var src = "http://ext.nicovideo.jp/thumb_watch/" + this.params[count].nico_video_id;
        $.getScript(src,function(){
            var write = alts.join("");
            $(_this.params[count].dom).html(write).removeClass(this.domPattern); // 指定した場所に流し込む
            document.write = d._write; // d.writeを元の定義に戻しておく
            if (++count < _this.count) {
                _this.show_nico_thumbnail(count);
            }
        });
    },

    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function(){
            var nico_video_id = $(this).attr("data-nicovideo");
            _this.append(this,nico_video_id);
        });
        if (this.count != 0) {
            this.show_nico_thumbnail(0);
        }
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

    this.getNicoTags = function(dom,nico_video_id) {
        $.ajax({
            url : 'http://voidy21.appspot.com/nicotag',
            dataType : 'jsonp',
            data : {
                video_id : nico_video_id
            },
            jsonp: 'callback',
            success : function(json) {
                if ( !json || !json.tags ) return;
                var nicoDom = $(dom);
                nicoDom.empty();
                for(var i = 0 , n = json.tags.length; i < n; i++){
                    var nico_tag = json.tags[i].tag;
                    var link_tag = $('<a />')
                        .attr('href','http://www.nicovideo.jp/tag/'+nico_tag)
                        .css({'font-size':'13px','color':'#000080'})
                        .text(decodeURIComponent(nico_tag));
                    nicoDom.append(link_tag);
                    if(i%4==0 && i!=0){
                        nicoDom.append('<br />');
                    }else if(i!=n-1){
                        nicoDom.append(' ');
                    }
                }
            },
            error : function(){}
        });
    },
    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function(){
            var nico_video_id = $(this).attr("data-nicovideo");
            _this.getNicoTags(this,nico_video_id);
            $(this).removeClass(this.domPattern);
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
        this.tagColor = ['#993900','#C84B00','#F25B00','#FF6000',
                         '#FF8135','#FFA975','#FFC6A4','#FFDDC8'];
        this.tagLength = this.tagColor.length;
    },

    this._showTagCloud = function(dom,tagCloud) {
        for(var i = 0; i<this.tagLength; i++){
            var link_tag = $('<a />');
            if(!tagCloud[i].tag) continue;
            $(link_tag)
               .attr('href','http://b.hatena.ne.jp/t/'+encodeURI(tagCloud[i].tag))
               .css({ 
                   'font-size':'13px','color':this.tagColor[i],
                   'text-decoration':'none'
                })
               .text(tagCloud[i].tag);
            $(dom).append(link_tag);
            if (i!=this.tagLength-1)
                $(dom).append(' ');
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
        for(var j= 0; j<this.tagLength; j++){
            tagCloud[j] = {};
            tagCloud[j].count = 0;
            for(var i in hatena_tags){
                if(tagCloud[j].count<hatena_tags[i]){
                    tagCloud[j].count = hatena_tags[i];
                    tagCloud[j].tag = i;
                }
            }
            hatena_tags[tagCloud[j].tag]= 0;
        }
        return tagCloud;
    },

    this._getTitleAndTagCloud = function(dom,url) {
        var _this = this;
        $.ajax({
            url : 'http://b.hatena.ne.jp/entry/jsonlite/',
            dataType : 'jsonp',
            data : { url : url },
            jsonp : 'callback',
            success : function(json){
                if(!json || !json.title) return;
                var prevDom = $("<div />").css('color','green').text(json.title);
                var nextDom = $("<div />");
                $(dom).prepend(prevDom);
                $(dom).append(nextDom);
                var api = new ShowTitleApi(url,json);
                var tagCloud = _this._getTagCloud(json);
                _this._showTagCloud(nextDom,tagCloud);
                api.setTooltip(dom);
            },
            error : function(){}
        });
    },

    this.execute = function() {
        var _this = this;
        $(this.domPattern).each(function(){
            var url = $(this).find("a").attr("class","link").attr("href").replace("/href?location=","");
            _this._getTitleAndTagCloud(this,url);
            $(this).removeClass(this.domPattern);
        });
    }
}).apply(NormalLink.prototype);

var ShowTitleApi = function() {
    this.initialize.apply(this, arguments);
}

ShowTitleApi.prototype = {
    initialize : function(url,json) {
        this.url = url;
        this.json = json;
    },

    _getThumbnailDom : function() {
        var hatena_screen =  this.json.screenshot.replace('/120x90/', '/200x150/');
        var thumbnail = $('<div />');
        $(thumbnail).addClass('url_title').append(this.json.title.substr(0,60))
            .append($('<img />').attr('src',hatena_screen)).append($('<br />'))
            .append(Api.getHatebuImage(this.url))
            .append(Api.getLdclipImage(this.url));
        return thumbnail;
    },

    setTooltip : function(dom) {
        var _this = this;
        $(dom).find("a").tooltip({
            bodyHandler: function(){
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
    return $('<img />').attr('src',api + url);
}

Api.getLdclipImage = function(url) {
    var api = 'http://image.clip.livedoor.com/counter/medium/';
    return $('<img />').attr('src',api + url);
}