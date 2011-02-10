package kwitches.model.kgimport;

import kwitches.model.FetchedIdModel;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class KGFetchedIdModelTest extends AppEngineTestCase {

    private FetchedIdModel model = new FetchedIdModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
