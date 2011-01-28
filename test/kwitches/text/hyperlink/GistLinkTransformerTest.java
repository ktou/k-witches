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
public class GistLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform1() {
        String rawString = "https://gist.github.com/754592";
        String transString = "<a href='https://gist.github.com/754592'>" +
            "https://gist.github.com/754592</a><br>" +
            "<div data-gistid='754592' class='new_gist_preview'></div>";
        HyperlinkTransformInterface transformer = new GistLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

    /**
     * {@link kwitches.text.hyperlink.TumblrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "https://gist.github.com/754592/";
        String transString = "<a href='https://gist.github.com/754592/'>" +
            "https://gist.github.com/754592/</a><br>" +
            "<div data-gistid='754592' class='new_gist_preview'></div>";
        HyperlinkTransformInterface transformer = new GistLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
}
