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
            }
        });
    }
}