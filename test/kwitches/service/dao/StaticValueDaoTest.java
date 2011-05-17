package kwitches.service.dao;

import kwitches.service.dao.StaticValueDao.StaticValueType;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StaticValueDaoTest extends AppEngineTestCase {

    @Test
    public void test() throws Exception {
        assertThat(StaticValueDao.getValue(StaticValueType.USTREAM_CHANNEL_KEY), is(notNullValue()));
    }
}
