package kwitches.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kwitches.text.hyperlink.HyperlinkTransformInterface;
import kwitches.text.hyperlink.*;

/**
 * 行中のハイパーリンクに相当する文字列を変換するためのクラス
 * @author voidy21
 */
public class HyperlinkTransformer implements LineMessageTransformInterface {

    private static final String REGEXP_URL_STRING = "(https?):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*";
    
    private List<HyperlinkTransformInterface> hyperlinkTransformerList = 
        new ArrayList<HyperlinkTransformInterface>(){{
            add(new NicovideoLinkTransformer());
            add(new TwitterLinkTransformer());
            add(new YoutubeLinkTransformer());
        }};

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#getRegexp()
     */
    public String getRegexp() {
        return REGEXP_URL_STRING;
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#transform(java.lang.String)
     */
    public String transform(String rawString) {
        Pattern p = Pattern.compile(this.getRegexp(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);
        return m.replaceAll("<a class='link' href='$0'>$0</a>");
    }

}
