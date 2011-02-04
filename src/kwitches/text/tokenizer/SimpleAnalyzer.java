package kwitches.text.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author voidy21
 */
public class SimpleAnalyzer implements AnalyzerInterface {

    public static final String REGEXP_TOKEN = "[一-龠々〆ヵヶ]+|[ぁ-ん]+|[ァ-ヴー]+" +
        "|[a-zA-Z0-9]+|[ａ-ｚＡ-Ｚ０-９]+|[,.、。！!？?()（）「」『』]+|[ 　]+";
    
    public static final String REGEXP_JOSHI= "(でなければ|について|ならば|までを" +
    		"|までの|くらい|なのか|として|とは|なら|から|まで|して|だけ|より|ほど|など" +
    		"|って|では|は|で|を|の|が|に|へ|と|て)";
    
    /* (非 Javadoc)
     * @see kwitches.text.tokenizer.AnalyzerInterface#parse(java.lang.String)
     */
    public List<String> parse(String rawString) {
        if (rawString == null) {
            return null;
        }
        Pattern p = Pattern.compile(REGEXP_JOSHI);
        Matcher m = p.matcher(rawString);
        rawString = m.replaceAll("$0|");
        String[] sp = rawString.split("\\|");
        List<String> result = new ArrayList<String>();
        for (String s : sp) {
            Pattern p2 = Pattern.compile(REGEXP_TOKEN);
            Matcher m2 = p2.matcher(s);
            String token = m2.replaceAll("$0|");
            String[] sp2 = token.split("\\|");
            for (String s2 : sp2) {
                if (!s2.equals(" ") && !s2.equals("　")) {
                    result.add(s2.replace("\r\n", "").replace("\n", "").replace("\r", ""));
                }
            }
        }
        return result;
    }

}
