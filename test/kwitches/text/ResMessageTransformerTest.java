package kwitches.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;


public class ResMessageTransformerTest extends AppEngineTestCase {
    @Test
    public void test1() throws Exception {
        String text = "&gt;&gt;2000";
        assertThat(new ResMessageTransformer().transform(text), 
            is("<a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a>"));
    }
    
    @Test
    public void test2() throws Exception {
        String text = "&gt;&gt;2000 &gt;&gt;2001";
        assertThat(new ResMessageTransformer().transform(text), 
            is("<a class='res' href='javascript:void(0)' data-resnum='2000'>&gt;&gt;2000</a> " +
                "<a class='res' href='javascript:void(0)' data-resnum='2001'>&gt;&gt;2001</a>"));
    }
}
