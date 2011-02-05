package kwitches.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UploadFileServiceTest extends AppEngineTestCase {

    private UploadFileService service = new UploadFileService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
