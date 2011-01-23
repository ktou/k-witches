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
public class TwitpicLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform1() {
        String rawString = "http://twitpic.com/2xfh6c";
        String transString = "<a href='http://twitpic.com/2xfh6c'>http://twitpic.com/2xfh6c<br>" +
            "<img src='http://twitpic.com/show/thumb/2xfh6c'></a><br>";
        HyperlinkTransformInterface transformer = new TwitpicLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://twitpic.com/2xfh6c/";
        String transString = "<a href='http://twitpic.com/2xfh6c/'>http://twitpic.com/2xfh6c/<br>" +
            "<img src='http://twitpic.com/show/thumb/2xfh6c'></a><br>";
        HyperlinkTransformInterface transformer = new TwitpicLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
}
