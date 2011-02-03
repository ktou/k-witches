/**
 * 
 */
package kwitches.text.tokenizer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author voidy21
 *
 */
public class SimpleAnalyzerTest {

    /**
     * {@link kwitches.text.tokenizer.SimpleAnalyzer#parse(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testParse() {
        AnalyzerInterface analyzer = new SimpleAnalyzer();
        assertArrayEquals(analyzer.parse("今日はいい天気でした。").toArray(), new ArrayList<String>(){{
            add("今日");
            add("は");
            add("いい");
            add("天気");
            add("で");
            add("した");
            add("。");
        }}.toArray());
    }

}
