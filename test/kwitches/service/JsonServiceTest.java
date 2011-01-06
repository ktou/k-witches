package kwitches.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

public class JsonServiceTest extends AppEngineTestCase {

    private JsonService jsonService = new JsonService();
    private SignService signService = new SignService();

    @Test
    public void test() throws Exception {
        assertThat(jsonService, is(notNullValue()));
    }
    
    @Test
    public void getJson() throws Exception {
        String name = "hoge";
        User user = new User("hoge@localhost.com", "gmail.com");
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        userModel.setName(name);
        userModel.setAuthUser(true);
        Datastore.put(userModel);
        String ip = "127.0.0.1";
        String comment = "hello";
        Date createdDate = TimeUtils.getJstDate();
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("comment", comment);
        BBSDataModel signed = signService.sign(input, ip, createdDate, userModel);
        assertThat(signed, is(notNullValue()));
        BBSDataModel stored = Datastore.get(BBSDataModel.class, signed.getKey());
        assertThat(stored.getComment(), is(comment));
        assertThat(stored.getUserModelRef().getModel(), is(userModel));
        assertThat(stored.getIpAddress(), is(ip));
        assertThat(stored.getId(), is(1));
        String jsonString = "<'articles':[<'name':'{0}','ip':'{1}'," +
            "'date':'{2,date} {2,time}','comment':'{3}','id':'1','classtype':'text','icon':''>]>";
        Object[] argument = {
            name,
            ip,
            createdDate,
            comment
        };
        jsonString = jsonString.replace("'", "\"");
        jsonString = MessageFormat.format(jsonString, argument)
                        .replace("<","{")
                        .replace(">","}");        
        assertThat(jsonService.getJson(), is(jsonString));
        /* 
         * JSONはこういうフォーマットにする
         * {"articles":[{
         *      "name":"hoge",
         *      "ip":"127.0.0.1",
         *      "date":"2010/12/30",
         *      "comment":"hello",
         *      "id":"1",
         *      "classtype":"text",
         *      "icon" : "",     
         *  }]}
         */
    }
    
    
    @Test
    public void getBBSDataList() throws Exception {
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.setComment("test");
        Datastore.put(bbsDataModel);
        List<BBSDataModel> bbsDataList = jsonService.getBBSDataList();
        assertThat(bbsDataList.size(), is(1));
        assertThat(bbsDataList.get(0).getComment(), is("test"));
    }
}
