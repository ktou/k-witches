/**
 * 
 */
package kwitches.text.hyperlink;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author voidy21
 *
 */
public class TwitterLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.TwitterLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform() {
        String rawString = "http://twitter.com/zenra_bot/status/3605531004";
        String transString = "<a href='http://twitter.com/zenra_bot/status/3605531004'>" +
            "http://twitter.com/zenra_bot/status/3605531004</a><br>" +
            "<div data-status_num='3605531004' class='new_twitter_thumb' " +
            "data-twitter_id='zenra_bot'></div>";
        HyperlinkTransformInterface transformer = new TwitterLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

    /**
     * {@link kwitches.text.hyperlink.TwitterLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://twitter.com/zenra_bot/status/3605531004/";
        String transString = "<a href='http://twitter.com/zenra_bot/status/3605531004/'>" +
            "http://twitter.com/zenra_bot/status/3605531004/</a><br>" +
            "<div data-status_num='3605531004' class='new_twitter_thumb' " +
            "data-twitter_id='zenra_bot'></div>";
        HyperlinkTransformInterface transformer = new TwitterLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
}
