package kwitches.service.dao;

import kwitches.service.kgimport.dao.KGFetchedIdDao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class KGFetchedIdDaoTest extends AppEngineTestCase {

    private KGFetchedIdDao service = KGFetchedIdDao.getInstance();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
