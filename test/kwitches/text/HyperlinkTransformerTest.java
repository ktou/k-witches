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
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks(text)));
    }

    @Test
    public void test2() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/") +
                " <a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.google.co.jp/")));
    }

    @Test
    public void test3() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/ http://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/") +
                " <a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.google.co.jp/") +
                " <a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks("http://www.yahoo.co.jp/")));
    }

    @Test
    public void test4() throws Exception {
        String text = "https://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<a class='link' href='https://www.yahoo.co.jp/'>https://www.yahoo.co.jp/</a>" +
            HyperlinkTransformUtil.getSBMLinks("https://www.yahoo.co.jp/")));
    }

    @Test
    public void test5() throws Exception {
        String rawString = "http://www.nicovideo.jp/watch/sm7415744";
        String transString = "<div data-nicovideo='sm7415744' class='new_nico_thumb'></div><br>" +
        HyperlinkTransformUtil.getSBMLinks(rawString) +
        "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div>";
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void test6() throws Exception {
        String rawString = "http://twitter.com/zenra_bot/status/3605531004/";
        String transString = "<a href='http://twitter.com/zenra_bot/status/3605531004/'>" +
            "http://twitter.com/zenra_bot/status/3605531004/</a><br>" +
            "<div data-status_num='3605531004' class='twitter_thumbnail' " +
            "data-twitter_id='zenra_bot'></div>";
        assertThat(new HyperlinkTransformer().transform(rawString),
            is(transString));
    }

    @Test
    public void test7() throws Exception {
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
    public void test8() throws Exception {
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
    public void test9() throws Exception {
        String text = "ttp://s-witch.cute.or.jp/";
        assertThat(new HyperlinkTransformer().transform(text),
            is("<a class='link' href='http://s-witch.cute.or.jp/'>http://s-witch.cute.or.jp/</a>" +
                HyperlinkTransformUtil.getSBMLinks(text)));
    }
}
