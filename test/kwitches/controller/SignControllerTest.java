package kwitches.controller;

import kwitches.model.BBSDataModel;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SignControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.param("comment", "Hello");
        tester.start("/sign");
        SignController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        BBSDataModel stored = Datastore.query(BBSDataModel.class).asSingle();
        assertThat(stored, is(notNullValue()));
        assertThat(stored.getBBSComment(), is("Hello"));
    }
}
