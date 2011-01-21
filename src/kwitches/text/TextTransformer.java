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
@SuppressWarnings({ "serial" })
public class TextTransformer {

    private static List<LineMessageTransformInterface> transformerList =
        new ArrayList<LineMessageTransformInterface>(){{
            add(new ResMessageTransformer());
            add(new QuoteMessageTransformer());
            add(new HyperlinkTransformer());
        }};

    public static String transform(String rawText) {
        if (rawText == null) {
            return null;
        }
        rawText = StringUtils.escape(rawText);
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
