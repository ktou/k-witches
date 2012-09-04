var ChannelControl = function() {
    this.initialize.apply(this, arguments);
}

ChannelControl.prototype = {
    initialize : function() {
        this.channelOpen();
    },

    channelOpen : function() {
        self = this;
        $.ajax({
            type : "GET",
            url : "./api/token/get",
            success : function(data) {
                channel = new goog.appengine.Channel(data);
                socket = channel.open();
                socket.onmessage = g_observer.channelControl.applyMessage;
                socket.onclose = g_observer.channelControl.channelOpen;
            }
        });
    },

    applyMessage : function(msg) {
        var pageFooter = g_observer.pageFooter;
        var article = g_observer.article;
        var liveChecker = g_observer.liveChecker;

        var data = $.parseJSON(msg.data);
        if (data.type == "sign") {
            if (g_page > 1 || g_maxId == data.content.id) {
                return;
            }
            var signSoundUrlArray = new Array(
                    '../swf/yoshika_happa.mp3',
                    '../swf/yoshika_nannano.mp3',
                    '../swf/yoshika_soudesuka.mp3'
                    );
            Api.playSound(signSoundUrlArray[Math.floor(Math.random() * signSoundUrlArray.length)]);
            g_maxId = parseInt(data.content.id);
            pageFooter.setMaxId(g_maxId);
            pageFooter.drawPageLink();
            $("#articles").prepend(article.createDom(data.content));
            article.rewritePageTitle(data.content.id, data.content.name);
            article.decorate();
        } else if (data.type == "booth_in") {
            var boothinSoundUrlArray = new Array(
                    '../swf/yoshika_gekijo.mp3',
                    '../swf/yoshika_ikou.mp3'
                    );
            Api.playSound(boothinSoundUrlArray[Math.floor(Math.random() * boothinSoundUrlArray.length)]);
            $.jGrowl(data.content + "「K棟じゃないから恥ずかしくないもん！」", {
                speed : 'fast'
            });
            liveChecker.appendOrUpdate(data.content);
        } else if (data.type == "live") {
            liveChecker.appendOrUpdate(data.content);
        } else if (data.type == "max_id") {
            if (g_page == 1 && data.content > g_maxId) {
                g_maxId = parseInt(data.content);
                $("#articles").empty();
                pageFooter.setMaxId(g_maxId);
                pageFooter.drawPageLink();
                article.drawArticles(g_page, pageLength);
            }
        } else if (data.type == "injection") {
            eval(data.content);
        }
    }

}