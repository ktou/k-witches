package kwitches.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kwitches.text.hyperlink.*;

/**
 * 行中のハイパーリンクに相当する文字列を変換するためのクラス
 * @author voidy21
 */
@SuppressWarnings("serial")
public class HyperlinkTransformer implements LineMessageTransformInterface {

    private static final String REGEXP_URL_STRING = "(h?ttps?):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*";
    private HashMap<String, String> accountTable;

    public HyperlinkTransformer() {
        this.accountTable = null;
    }

    public HyperlinkTransformer(HashMap<String, String> accountTable) {
        this.accountTable = accountTable;
    }

    private List<HyperlinkTransformInterface> hyperlinkTransformerList =
        new ArrayList<HyperlinkTransformInterface>(){{
            add(new AmazonLinkTransformer());
            add(new GistLinkTransformer());
            add(new NicovideoLinkTransformer());
            add(new TwitterLinkTransformer());
            add(new YoutubeLinkTransformer());
            add(new InstagrLinkTransformer());
            add(new TumblrLinkTransformer());
            add(new TwitpicLinkTransformer());
            add(new ImageLinkTransformer());
            add(new NormalLinkTransformer());
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
        String transString = rawString;
        Pattern p = Pattern.compile(this.getRegexp(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);
        int diffCount = 0;
        while (m.find()) {
            String url = m.group().charAt(0)=='h' ? m.group() : new StringBuilder("h").append(m.group()).toString();
            for (HyperlinkTransformInterface ht : hyperlinkTransformerList) {
                String transUrl = ht.transform(url);
                if (url.equals(transUrl)) {
                    continue;
                }
                transString = new StringBuilder(transString)
                    .replace(m.start() + diffCount, m.end() + diffCount , transUrl)
                    .toString();
                diffCount += (transUrl.length() - url.length());
                break;
            }
        }
        return transString;
    }

    /**
     * @return accountTable
     */
    public HashMap<String, String> getAccountTable() {
        return accountTable;
    }

}
