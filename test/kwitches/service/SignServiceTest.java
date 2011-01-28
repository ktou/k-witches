package kwitches.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kwitches.model.BBSDataModel;
import kwitches.model.UserModel;
import kwitches.util.TimeUtils;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.google.appengine.api.users.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SignServiceTest extends AppEngineTestCase {

    private SignService service = new SignService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    @Test
    public void sign() throws Exception {
        String name = "hoge";
        User user = new User("hoge@localhost.com", "gmail.com");
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        userModel.setName(name);
        userModel.setAuthUser(true);
        Datastore.put(userModel);
        String ip = "127.0.0.1";
        Date createdDate = TimeUtils.getJstDate();
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("comment", "Hello");
        BBSDataModel signed = service.sign(input, ip, createdDate, userModel);
        assertThat(signed, is(notNullValue()));
        BBSDataModel stored = Datastore.get(BBSDataModel.class, signed.getKey());
        assertThat(stored.getComment(), is("Hello"));
        assertThat(stored.getUserModelRef().getModel(), is(userModel));
        assertThat(stored.getIpAddress(), is(ip));
        assertThat(stored.getId(), is(1));
        assertThat(stored.getCreatedDate(), is(createdDate));
    }
    
    @Test
    public void getMaxId() throws Exception {
        String name = "hoge";
        User user = new User("hoge@localhost.com", "gmail.com");
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        userModel.setName(name);
        userModel.setAuthUser(true);
        Datastore.put(userModel);
        String ip = "127.0.0.1";
        Date createdDate = TimeUtils.getJstDate();
        Map<String, Object> input = new HashMap<String, Object>();
        for (int i = 0; i < 10; i++) {
            BBSDataModel signed = service.sign(input, ip, createdDate, userModel);
            assertThat(signed, is(notNullValue()));
            BBSDataModel stored = Datastore.get(BBSDataModel.class, signed.getKey());
            assertThat(stored.getId(), is(i + 1));
         }
    }

}
