package kwitches.service.twitter.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MentionCounterDaoTest extends AppEngineTestCase {

    private MentionCounterDao service = MentionCounterDao.getInstance();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
