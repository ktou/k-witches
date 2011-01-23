package kwitches.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import kwitches.text.hyperlink.HyperlinkTransformUtil;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;


public class HyperlinkTransformerTest extends AppEngineTestCase {
    @Test
    public void test1() throws Exception {
        String text = "http://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<div class='new_link'><a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks(text) +
                "</div>" ));
    }

    @Test
    public void test2() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<div class='new_link'><a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/") +
                "</div> <div class='new_link'><a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.google.co.jp/") +
                "</div>"));
    }

    @Test
    public void test3() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/ http://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<div class='new_link'><a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/") +
                "</div> <div class='new_link'><a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.google.co.jp/") +
                "</div> <div class='new_link'><a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/") +
                "</div>"));
    }

    @Test
    public void httpsLinkTest() throws Exception {
        String text = "https://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<div class='new_link'><a class='link' href='https://www.yahoo.co.jp/'>https://www.yahoo.co.jp/</a>" +
            HyperlinkTransformUtil.getSBMLinks("https://www.yahoo.co.jp/") + "</div>"));
    }

    @Test
    public void nicovideoLinkTest() throws Exception {
        String rawString = "http://www.nicovideo.jp/watch/sm7415744";
        String transString = "<div data-nicovideo='sm7415744' class='new_nico_thumb'></div><br>" +
        HyperlinkTransformUtil.getSBMLinks(rawString) +
        "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div>";
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void twitterLinkTest() throws Exception {
        String rawString = "http://twitter.com/zenra_bot/status/3605531004/";
        String transString = "<a href='http://twitter.com/zenra_bot/status/3605531004/'>" +
            "http://twitter.com/zenra_bot/status/3605531004/</a><br>" +
            "<div data-status_num='3605531004' class='new_twitter_thumb' " +
            "data-twitter_id='zenra_bot'></div>";
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void youtubeLinkTest() throws Exception {
        String rawString = "http://www.youtube.com/watch?v=zXnTYEW1m7M";
        String transString = "<div><iframe class='youtube-player' " +
            "type='text/html' width='640' height='385' " +
            "src='http://www.youtube.com/embed/zXnTYEW1m7M' " +
            "frameborder='0'></iframe></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(rawString);
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void nicovideoAndYoutubeLinkTest() throws Exception {
        String rawString = "http://www.nicovideo.jp/watch/sm7415744 http://www.youtube.com/watch?v=zXnTYEW1m7M";
        String transString = "<div data-nicovideo='sm7415744' class='new_nico_thumb'></div><br>" +
        HyperlinkTransformUtil.getSBMLinks("http://www.nicovideo.jp/watch/sm7415744") +
        "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div> " +
            "<div><iframe class='youtube-player' " +
            "type='text/html' width='640' height='385' " +
            "src='http://www.youtube.com/embed/zXnTYEW1m7M' " +
            "frameborder='0'></iframe></div><br>" +
            HyperlinkTransformUtil.getSBMLinks("http://www.youtube.com/watch?v=zXnTYEW1m7M");
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void ttpLinkTest() throws Exception {
        String text = "ttp://s-witch.cute.or.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<div class='new_link'><a class='link' href='http://s-witch.cute.or.jp/'>http://s-witch.cute.or.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("h" + text) + "</div>"));
    }
    
    @Test
    public void tumblrLinkTest() {
        String text = "http://thrakt.tumblr.com/post/2811433075";
        String transString = "<a href='http://thrakt.tumblr.com/post/2811433075'>" +
            "http://thrakt.tumblr.com/post/2811433075</a><br>" +
            "<div data-postid='2811433075' class='new_tumblr_thumb' data-tumblrid='thrakt'></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(text);
        assertThat(new HyperlinkTransformer().transform(text), is(transString));
    }
    
    @Test
    public void twitpicLinktest() {
        String text = "http://twitpic.com/2xfh6c";
        String transString = "<a href='http://twitpic.com/2xfh6c'>http://twitpic.com/2xfh6c<br>" +
            "<img src='http://twitpic.com/show/thumb/2xfh6c'></a><br>";
        assertThat(new HyperlinkTransformer().transform(text), is(transString));
    }
    
    @Test
    public void imageLinkTest() {
        String text = "https://github.com/images/gravatars/gravatar-140.png";
        String transString = "<a class='lightpop' href='https://github.com/images/gravatars/gravatar-140.png'>" +
            "<img width='200' src='https://github.com/images/gravatars/gravatar-140.png'></a><br>";
        assertThat(new HyperlinkTransformer().transform(text), is(transString));
    }
    
    @Test
    public void instagrLinkTest() {
        String text = "http://instagr.am/p/BBjYm";
        String transString = "<a href='http://instagr.am/p/BBjYm'>http://instagr.am/p/BBjYm</a><br><div data-url='http://instagr.am/p/BBjYm' class='new_instagr_thumb'></div>";
        assertThat(new HyperlinkTransformer().transform(text), is(transString));
    }
}
