/**
 * 
 */
package kwitches.text;

import java.util.ArrayList;
import java.util.List;

import kwitches.util.StringUtils;

/**
 * @author voidy21
 *
 */
@SuppressWarnings({ "unchecked", "serial" })
public class TextTransformer {
    
    private static List<LineMessageTransformInterface> transformerList =
        new ArrayList(){{
            add(new ResMessageTransformer());
            add(new QuoteMessageTransformer());
            add(new HyperlinkTransformer());
        }};
    
    public static String transform(String rawText) {
        if (rawText == null) {
            return null;
        }
        String[] rawTextLines = rawText.split("\\n|\\r|\\r\\n");
        List<String> transLines = new ArrayList<String>();
        for (String rawString : rawTextLines) {
            for (LineMessageTransformInterface transformer : transformerList) {
                rawString = transformer.transform(rawString);
            }
            transLines.add(rawString);
        }
        return StringUtils.join(transLines, "\n");
    }
}