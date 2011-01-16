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
            "<div class='sbm_links'><a href='http://b.hatena.ne.jp/entry/image/http://www.nicovideo.jp/watch/sm7415744' target='_brank'>" +
            "<img src='http://b.hatena.ne.jp/entry/http://www.nicovideo.jp/watch/sm7415744' /></a>" +
            "<a href='http://image.clip.livedoor.com/counter/medium/http://www.nicovideo.jp/watch/sm7415744' target='_brank'>" +
            "<img src='http://clip.livedoor.com/page/http://www.nicovideo.jp/watch/sm7415744' /></a>" +
            "<a href='http://image.twib.jp/counter/http://www.nicovideo.jp/watch/sm7415744' target='_brank'>" +
            "<img src='http://twib.jp/url/http://www.nicovideo.jp/watch/sm7415744' /></a></div>" +
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
            "<div class='sbm_links'><a href='http://b.hatena.ne.jp/entry/image/http://www.nicovideo.jp/watch/sm7415744/' target='_brank'>" +
            "<img src='http://b.hatena.ne.jp/entry/http://www.nicovideo.jp/watch/sm7415744/' /></a>" +
            "<a href='http://image.clip.livedoor.com/counter/medium/http://www.nicovideo.jp/watch/sm7415744/' target='_brank'>" +
            "<img src='http://clip.livedoor.com/page/http://www.nicovideo.jp/watch/sm7415744/' /></a>" +
            "<a href='http://image.twib.jp/counter/http://www.nicovideo.jp/watch/sm7415744/' target='_brank'>" +
            "<img src='http://twib.jp/url/http://www.nicovideo.jp/watch/sm7415744/' /></a></div>" +
            "<br><div data-nicovideo='sm7415744' class='new_nico_tags'></div>";
        HyperlinkTransformInterface transformer = new NicovideoLinkTransformer();
        assertThat(transformer.transform(rawString), is(transString));
    }

}
