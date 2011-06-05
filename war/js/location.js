var Location = function() {
    this.cookie_name = "location";
    this.nothing_name = "none";
    this.initialize.apply(this, arguments);
};

Location.prototype = {
    initialize : function(dom_id) {
        this.dom_id = dom_id;
        this.input_dom = $(this.dom_id + " input:first");
        this.display_dom = $(this.dom_id + " span:first");
        this.setName(this.getCookie());
        this.initializeBindEvents();
    },

    initializeBindEvents : function() {
        this.bindClickEvent();
        this.bindFocusoutEvent();
    },

    bindClickEvent : function() {
        var _this = this;
        $(this.dom_id).click(function() {
            _this.showInputDom();
        });
    },

    bindFocusoutEvent : function() {
        var _this = this;
        $(this.dom_id).focusout(function() {
            _this.hideInputDom();
            _this.setNameByInputValue();
        });
    },

    isDisplayInputDom : function() {
        return this.input_dom.css("display") == "none";
    },

    showInputDom : function() {
       if (this.isDisplayInputDom()) {
           this.display_dom.hide();
           this.input_dom.show();
       }
    },

    hideInputDom : function() {
       if (!this.isDisplayInputDom()) {
           this.display_dom.show();
           this.input_dom.hide();
       }
    },

    getCookie : function() {
        return $.cookie(this.cookie_name);
    },

    setCookie : function(value) {
        $.cookie(this.cookie_name, value, { expires: 30 });
    },

    getInputValue : function() {
        return this.input_dom.val();
    },

    setInputValue : function(value) {
        this.input_dom.val(value);
    },

    getName : function() {
        return this.name;
    },

    setName : function(name) {
        this.input_dom.val(name);
        this.name = name || this.nothing_name;
        this.display_dom.text(" " + this.name);
    },

    setNameByInputValue : function() {
        this.setName(this.getInputValue());
    },

    setCookieByInputValue : function() {
        this.setCookie(this.getInputValue());
    },

    setNameAndCookieByInputValue : function() {
        this.setNameByInputValue();
        this.setCookieByInputValue();
    }
}

