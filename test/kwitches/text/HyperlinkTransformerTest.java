package kwitches.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;


public class HyperlinkTransformerTest extends AppEngineTestCase {
    @Test
    public void test1() throws Exception {
        String text = "http://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text), 
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>"));
    }
    
    @Test
    public void test2() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text), 
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a> " +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a>"));
    }
    
    @Test
    public void test3() throws Exception {
        String text = "http://www.yahoo.co.jp/ http://www.google.co.jp/ http://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text), 
            is("<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a> " +
                "<a class='link' href='http://www.google.co.jp/'>http://www.google.co.jp/</a> " +
                "<a class='link' href='http://www.yahoo.co.jp/'>http://www.yahoo.co.jp/</a>"));
    }
    
    @Test
    public void test4() throws Exception {
        String text = "https://www.yahoo.co.jp/";
        assertThat(new HyperlinkTransformer().transform(text), 
            is("<a class='link' href='https://www.yahoo.co.jp/'>https://www.yahoo.co.jp/</a>"));
    }
}
