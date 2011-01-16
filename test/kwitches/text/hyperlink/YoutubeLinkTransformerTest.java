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
public class YoutubeLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.YoutubeLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform() {
        String rawString = "http://www.youtube.com/watch?v=zXnTYEW1m7M";
        String transString = "<div><iframe class='youtube-player' " +
            "type='text/html' width='640' height='385' " +
            "src='http://www.youtube.com/embed/zXnTYEW1m7M' " +
            "frameborder='0'></iframe></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(rawString);
        HyperlinkTransformInterface transformer = new YoutubeLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

}
