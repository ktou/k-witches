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
public class NicovideoLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.NicovideoLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform() {
        String rawString = "http://www.nicovideo.jp/watch/sm7415744";
        String transString = "<div data-nicovideo='sm7415744' class='new_nico_thumb'></div><br>" +
        HyperlinkTransformUtil.getSBMLinks(rawString) +
        "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div>";
        HyperlinkTransformInterface transformer = new NicovideoLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.NicovideoLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://www.nicovideo.jp/watch/sm7415744/";
        String transString = "<div data-nicovideo='sm7415744' class='new_nico_thumb'></div><br>" +
            HyperlinkTransformUtil.getSBMLinks(rawString) +
            "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div>";
        HyperlinkTransformInterface transformer = new NicovideoLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

}
