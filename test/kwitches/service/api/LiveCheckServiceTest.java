package kwitches.service.api;

import java.util.HashMap;
import java.util.Map;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LiveCheckServiceTest extends AppEngineTestCase {

    private LiveCheckService service = new LiveCheckService();

    @Test
    public void test() throws Exception {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("id", "hoge");
        input.put("name", "hogename");
        input.put("location", "hogelocation");
        assertThat(service.generateLiveUserJson(input), is(notNullValue()));
    }
}
