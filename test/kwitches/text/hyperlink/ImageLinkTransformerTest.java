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
public class ImageLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.ImageLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform1() {
        String rawString = "https://github.com/images/gravatars/gravatar-140.png";
        String transString = "<a class='lightpop' href='https://github.com/images/gravatars/gravatar-140.png'>" +
            "<img width='200' src='https://github.com/images/gravatars/gravatar-140.png'></a>";
        HyperlinkTransformInterface transformer = new ImageLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

    /**
     * {@link kwitches.text.hyperlink.ImageLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "https://github.com/images/gravatars/gravatar-140.PNG";
        String transString = "<a class='lightpop' href='https://github.com/images/gravatars/gravatar-140.PNG'>" +
            "<img width='200' src='https://github.com/images/gravatars/gravatar-140.PNG'></a>";
        HyperlinkTransformInterface transformer = new ImageLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.ImageLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform3() {
        String rawString = "http://livedoor.2.blogimg.jp/ringotomomin/imgs/b/2/b28c4db6.gif";
        String transString = "<a class='lightpop' href='http://livedoor.2.blogimg.jp/ringotomomin/imgs/b/2/b28c4db6.gif'>" +
            "<img width='200' src='http://livedoor.2.blogimg.jp/ringotomomin/imgs/b/2/b28c4db6.gif'></a>";
        HyperlinkTransformInterface transformer = new ImageLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

}
