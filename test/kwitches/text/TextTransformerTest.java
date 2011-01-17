/**
 * 
 */
package kwitches.text;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;

/**
 * @author voidy21
 *
 */
public class TextTransformerTest {

    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform() {
        String rawText = "てすと\nてすと";
        String transText = TextTransformer.transform(rawText);
        assertThat(rawText, is(transText)); 
    }

    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform2() {
        String rawText = "http://www.yahoo.co.jp/\nhttp://www.google.co.jp/";
        String transText = TextTransformer.transform(rawText);
        assertThat("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>\n" +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>", is(transText)); 
    }
    
    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform3() {
        String rawText = "&gt; http://www.yahoo.co.jp/\nhttp://www.google.co.jp/";
        String transText = TextTransformer.transform(rawText);
        assertThat("<div class='quote'>&gt; <a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a></div>\n" +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>", is(transText)); 
    }

    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform4() {
        String rawText = "&gt;\nhttp://www.google.co.jp/";
        String transText = TextTransformer.transform(rawText);
        assertThat("<div class='quote'>&gt;</div>\n" +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>", is(transText)); 
    }
    
    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform5() {
        String rawText = "&gt;&gt;2000\nhttp://www.google.co.jp/";
        String transText = TextTransformer.transform(rawText);
        assertThat("<a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a>\n" +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>", is(transText)); 
    }
    
    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform6() {
        String rawText = "&gt; &gt;&gt;2000";
        String transText = TextTransformer.transform(rawText);
        assertThat("<div class='quote'>&gt; <a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a></div>", is(transText)); 
    }
    
    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform7() {
        String rawText = "&gt;&gt;&gt;2000";
        String transText = TextTransformer.transform(rawText);
        assertThat("<div class='quote'>&gt;<a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a></div>", is(transText)); 
    }
    
    /**
     * {@link kwitches.text.TextTransformer#transform(java.lang.String)} のためのテスト・メソッド。
     */
    @Test
    public void testTransform8() {
        String rawText = "&gt;&gt;&gt;&gt;2000";
        String transText = TextTransformer.transform(rawText);
        assertThat("<div class='quote'>&gt;&gt;<a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a></div>", is(transText)); 
    }
}
