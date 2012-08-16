var PagingFooter = function() {
    this.initialize.apply(this, arguments);
}

PagingFooter.prototype = {
    initialize : function(page) {
        this.currentPage = parseInt(page);
        this.maxPage = 1;
        this.maxId = 0;
        var _this = this;
        window.onpopstate = function(event){
            var nextpage = _this.currentPage;
            var urlHierarchy = window.location.href.split('\/');
            if(urlHierarchy.length == 6 && urlHierarchy[3] == 'page'){
                nextpage = urlHierarchy[4];
            } else if(urlHierarchy.length == 4){
                nextpage = 1;
            }
            if(_this.currentPage != nextpage){
                _this.movePage(nextpage,false);
            }
        }
    },

    setMaxId : function(maxId) {
        this.maxId = maxId;
        this.maxPage = Math.floor(this.maxId / pageLength + 1);
    },

    drawPageLink : function() {
        var _this = this;
        $("#pagelink").empty().append($("<ul/>"));
        var pagediv = $("#pagelink > ul");
        var nextLink = function(isNext) {
            $(pagediv).append($("<li/>").append($("<a/>").click(function(){
                _this.movePage(_this.currentPage + (isNext ? 1 :-1));
            }).text(isNext ? ">>" : "<<")));
        };
        if (this.currentPage > 1) {
            nextLink(false);
        } else {
            $(pagediv).append($("<li/>").append($("<a/>").text("<<")).addClass("disabled"));
        }
        for (var i = 1; i <= this.maxPage; i++){
            if (i != this.currentPage) {
                $(pagediv).append($("<li/>").append($("<a/>").click(function(){
                    _this.movePage(parseInt(this.innerHTML));
                }).text("" + i)));
            } else {
                $(pagediv).append($("<li/>").append($("<a/>").text("" + i)).addClass("active"));
            }
        }
        if (this.currentPage < this.maxPage) {
            nextLink(true);
        } else {
            $(pagediv).append($("<li/>").append($("<a/>").text(">>")).addClass("disabled"));
        }
    },

    movePage : function(moveTo){
        this.currentPage = parseInt(moveTo);
        this.maxId = 0;
        if(arguments.length < 2 || arguments[1]) history.pushState("", "", "/page/"+moveTo+"/");
        $("#articles").empty();
        new Article().drawArticles(this.currentPage, pageLength);
        this.drawPageLink();
    }
}
