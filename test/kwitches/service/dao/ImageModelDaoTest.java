package kwitches.service.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ImageModelDaoTest extends AppEngineTestCase {

    private ImageModelDao service = ImageModelDao.GetInstance();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
