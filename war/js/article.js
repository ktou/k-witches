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
           var article = new Article();
           bq.html(article.createDom(data.articles[0]).removeClass("article"));
           article.decorate();
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

var ArticleModel = Backbone.Model.extend();

var ArticleList = Backbone.Collection.extend({model: ArticleModel});

var ArticlesView = Backbone.View.extend({
    el: '#articles',

    initialize: function() {
        //this.listenTo(this.collection, 'add', this.append);
    },

    append: function(article) {
        var index = this.collection.indexOf(article);
        var view = new Article({model: article}).render();
        if(index) {
            view.$el.insertAfter(this.$el.children()[index-1]);
        } else {
            this.$el.prepend(view.el);
        }
    },

    render: function() {
        this.$el.empty();
        this.collection.each(function(model){
            this.append(model);
        }, this);
        return this;
    }
});

var Article = Backbone.View.extend({
    _getIconUrl: function(data) {
        var icon = data.icon;
        return "user/icon?_=" + icon;
    },

    createDom: function(e){
        this.model = e;
        this.render();
        return this.$el;
    },

    render: function() {
        var e = this.model.attributes;
        var icon_url = this._getIconUrl(e);
        var article = $("<div/>").addClass("article").append(
            $("<div/>").addClass("photo").append(
                $("<img/>").attr("src", icon_url)
            )
        ).append(
            $("<div/>").addClass("entry").attr("data-entrynumber", e.id
            ).append(
                $("<div/>").addClass("title").append(
                    $("<a/>").addClass("number").addClass("like-link").text(e.id).click(function() {
                        Res.appendTextarea(e.id);
                        $("#textarea").focus().addClass("expand");
                    })
                ).append(" : ").append(
                    $("<a/>").addClass("name").addClass("like-link").text(decodeURL(e.name))
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
        this.$el = article;
        return this;
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
                if (!data.articles[0]) return;
                articlesView.collection = new ArticleList(data.articles);
                articlesView.render();
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
                articlesView.collection = new ArticleList(data.articles);
                articlesView.render();
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
            new OEmbedThumnail("div.new_oembed_thumb"),
            new PixivThumnail("div.new_pixiv_thumb"),
            new NormalLink("div.new_link")
        ];
        for (var i = 0, length = decoratorer.length; i < length; i++) {
            decoratorer[i].execute();
        }
        lazyScriptLoader.execute();
    },

    rewritePageTitle : function(number, name) {
        name = name.replace(/\+/g,"%20");
        document.title = "けいとうヤルカ " + number + " : " + decodeURIComponent(name);
    },

    rewriteFavicon : function(){
        if(g_observer.inFocus){
            this.displayedId = g_maxId;
            Tinycon.reset();
        } else {
            Tinycon.setBubble(g_maxId - this.displayedId);
        }
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
});
