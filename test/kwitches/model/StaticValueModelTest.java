package kwitches.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StaticValueModelTest extends AppEngineTestCase {

    private StaticValueModel model = new StaticValueModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
