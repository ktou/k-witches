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
public class TumblrLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform1() {
        String rawString = "http://thrakt.tumblr.com/post/2811433075/1991-pixiv";
        String transString = "<a href='http://thrakt.tumblr.com/post/2811433075/1991-pixiv'>" +
            "http://thrakt.tumblr.com/post/2811433075/1991-pixiv</a><br>" +
            "<div data-postid='2811433075' class='new_tumblr_thumb' data-tumblrid='thrakt'></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(rawString);
        HyperlinkTransformInterface transformer = new TumblrLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://thrakt.tumblr.com/post/2811433075";
        String transString = "<a href='http://thrakt.tumblr.com/post/2811433075'>" +
            "http://thrakt.tumblr.com/post/2811433075</a><br>" +
            "<div data-postid='2811433075' class='new_tumblr_thumb' data-tumblrid='thrakt'></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(rawString);
        HyperlinkTransformInterface transformer = new TumblrLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

}
