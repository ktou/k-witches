package kwitches.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BBSDataModelTest extends AppEngineTestCase {

    private BBSDataModel model = new BBSDataModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
