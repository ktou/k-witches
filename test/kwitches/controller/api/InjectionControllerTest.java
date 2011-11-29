package kwitches.controller.api;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class InjectionControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/api/injection");
        InjectionController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
