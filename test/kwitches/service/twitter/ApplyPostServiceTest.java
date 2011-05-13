package kwitches.service.twitter;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ApplyPostServiceTest extends AppEngineTestCase {

    private ApplyPostService service = new ApplyPostService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
