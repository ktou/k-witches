package kwitches.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class NumberCounterModelTest extends AppEngineTestCase {

    private NumberCounterModel model = new NumberCounterModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
