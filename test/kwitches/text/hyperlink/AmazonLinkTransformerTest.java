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
public class AmazonLinkTransformerTest {

    /**
     * {@link kwitches.text.hyperlink.AmazonLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform() {
        String rawString = "http://www.amazon.co.jp/正規表現-第2版-Jeffrey-F-Friedl/dp/4873111307";
        String transString = "<a href='http://www.amazon.co.jp/正規表現-第2版-Jeffrey-F-Friedl/dp/4873111307'>" +
            "http://www.amazon.co.jp/正規表現-第2版-Jeffrey-F-Friedl/dp/4873111307</a><br>" +
            "<iframe src='http://rcm-jp.amazon.co.jp/e/cm?t=thrakt-22&o=9&p=8&l=as1&asins=4873111307&IS2=1" +
            "&fc1=000000&lt1=_blank&lc1=0000FF&bc1=FFFFFF&bg1=FFFFFF&f=ifr' style='width:120px;height:240px;' " +
            "scrolling='no' marginwidth='0' marginheight='0' frameborder='0'></iframe><br>";
        HyperlinkTransformInterface transformer = new AmazonLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

    /**
     * {@link kwitches.text.hyperlink.AmazonLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawString = "http://www.amazon.jp/dp/4873111307";
        String transString = "<a href='http://www.amazon.jp/dp/4873111307'>" +
            "http://www.amazon.jp/dp/4873111307</a><br>" +
            "<iframe src='http://rcm-jp.amazon.co.jp/e/cm?t=thrakt-22&o=9&p=8&l=as1&asins=4873111307&IS2=1" +
            "&fc1=000000&lt1=_blank&lc1=0000FF&bc1=FFFFFF&bg1=FFFFFF&f=ifr' style='width:120px;height:240px;' " +
            "scrolling='no' marginwidth='0' marginheight='0' frameborder='0'></iframe><br>";
        HyperlinkTransformInterface transformer = new AmazonLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
    
    /**
     * {@link kwitches.text.hyperlink.AmazonLinkTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform3() {
        String rawString = "http://www.amazon.co.jp/SteelSeries-95250-QcK-mini-63005/dp/B000UEZ37G/ref=sr_1_2?s=electronics&ie=UTF8&qid=1295449799&sr=1-2";
        String transString = "<a href='http://www.amazon.co.jp/SteelSeries-95250-QcK-mini-63005/dp/B000UEZ37G/ref=sr_1_2?s=electronics&ie=UTF8&qid=1295449799&sr=1-2'>" +
            "http://www.amazon.co.jp/SteelSeries-95250-QcK-mini-63005/dp/B000UEZ37G/ref=sr_1_2?s=electronics&ie=UTF8&qid=1295449799&sr=1-2</a><br>" +
            "<iframe src='http://rcm-jp.amazon.co.jp/e/cm?t=thrakt-22&o=9&p=8&l=as1&asins=B000UEZ37G&IS2=1" +
            "&fc1=000000&lt1=_blank&lc1=0000FF&bc1=FFFFFF&bg1=FFFFFF&f=ifr' style='width:120px;height:240px;' " +
            "scrolling='no' marginwidth='0' marginheight='0' frameborder='0'></iframe><br>";
        HyperlinkTransformInterface transformer = new AmazonLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }
}
