var LiveUser = function() {
    this.dom = null;
    this.time = null;
    this.initialize.apply(this, arguments);
};

LiveUser.auto_remove_time = 15 * 1000;

LiveUser.createDom = function(user) {
	if (!(user && user["id"])) return;
    var userDom = $("<span/>").addClass("activeUser").data("id", user.id);
    var nameDom = $("<span/>").addClass("name").text(user.name);
    var locationDom = $("<span/>").addClass("location").text(user.location);
    return userDom.append(nameDom).append(locationDom);
};

LiveUser.prototype = {
    initialize : function() {
        this.initTimer();
    },

    setDomByUser : function(user) {
        this.dom = LiveUser.createDom(user);
    },

    getDom : function() {
        return this.dom;
    },

    remove : function() {
        if (!this.dom) return;
        this.dom.remove();
        this.dom = null;
    },

    initTimer : function() {
        var _this = this;
        this.timer = setTimeout(function() { _this.remove(); }, LiveUser.auto_remove_time);
    },

    resetTimer : function() {
        clearTimeout(this.timer);
        this.initTimer();
    }
}

var LiveChecker = function() {
    this.liveUsers = {};
    this.initialize.apply(this, arguments);
};

LiveChecker.prototype = {
    initialize : function(dom_id) {
        this.dom_id = dom_id;
        this.display_dom = $(this.dom_id + " span").first();
    },

    exists : function(user) {
        var liveUser = this.getLiveUserByUserId(user.id);
        return liveUser && liveUser.getDom() != null;
    },

    update : function(user) {
        if (!this.exists(user)) return;
        this.getUserDomByUserId(user.id).html(LiveUser.createDom(user));
    },

    getLiveUserByUserId : function(userId) {
        var liveUser = null;
        $(this.liveUsers).each(function(i, obj) {
            if (userId in obj) {
              liveUser = obj[userId];
              return;
            }
        });
        return liveUser;
    },

    append : function(user) {
        if (this.exists(user)) return;
        var liveUser = new LiveUser();
        liveUser.setDomByUser(user);
        this.display_dom.append(liveUser.getDom());
        this.liveUsers[user.id] = liveUser;
    },

    update : function(user) {
        if (!this.exists(user)) return;
        var liveUser = this.getLiveUserByUserId(user.id);
        liveUser.resetTimer();
        liveUser.getDom().html(LiveUser.createDom(user).html());
    },

    appendOrUpdate : function(user) {
        this.exists(user) ? this.update(user) : this.append(user);
    }
}

