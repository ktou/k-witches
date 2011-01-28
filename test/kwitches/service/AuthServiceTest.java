package kwitches.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AuthServiceTest extends AppEngineTestCase {

    private AuthService service = new AuthService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
