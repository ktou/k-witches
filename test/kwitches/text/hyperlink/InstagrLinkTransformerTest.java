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
public class InstagrLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.InstagrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform1() {
        String rawString = "http://instagr.am/p/BBjYm";
        String transString = "<a href='http://instagr.am/p/BBjYm'>http://instagr.am/p/BBjYm</a><br><div data-url='http://instagr.am/p/BBjYm' class='new_instagr_thumb'></div>";
        HyperlinkTransformInterface transformer = new InstagrLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.InstagrLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://instagr.am/p/BBjYm/";
        String transString = "<a href='http://instagr.am/p/BBjYm/'>http://instagr.am/p/BBjYm/</a><br><div data-url='http://instagr.am/p/BBjYm/' class='new_instagr_thumb'></div>";
        HyperlinkTransformInterface transformer = new InstagrLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
}
