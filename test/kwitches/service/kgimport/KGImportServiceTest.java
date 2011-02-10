package kwitches.service.kgimport;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class KGImportServiceTest extends AppEngineTestCase {

    private KGImportService service = new KGImportService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
