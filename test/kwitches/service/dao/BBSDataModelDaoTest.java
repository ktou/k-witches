package kwitches.service.dao;

import java.util.List;

import kwitches.model.BBSDataModel;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BBSDataModelDaoTest extends AppEngineTestCase {

    private BBSDataModelDao service = BBSDataModelDao.GetInstance();

    @Test
    public void getBBSDataList() throws Exception {
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.setComment("test");
        Datastore.put(bbsDataModel);
        List<BBSDataModel> bbsDataList = service.getBBSDataList();
        assertThat(bbsDataList.size(), is(1));
        assertThat(bbsDataList.get(0).getComment(), is("test"));
    }
    
    @Test
    public void getBBSDataList2() throws Exception {
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.setComment("test");
        Datastore.put(bbsDataModel);
        List<BBSDataModel> bbsDataList = service.getBBSDataList(0, 1);
        assertThat(bbsDataList.size(), is(1));
        assertThat(bbsDataList.get(0).getComment(), is("test"));
    }
    
    @Test
    public void getBBSDataList3() throws Exception {
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.setComment("test");
        bbsDataModel.setId(1);
        Datastore.put(bbsDataModel);
        bbsDataModel = new BBSDataModel();
        bbsDataModel.setComment("test2");
        bbsDataModel.setId(2);
        Datastore.put(bbsDataModel);
        List<BBSDataModel> bbsDataList = service.getBBSData(1);
        assertThat(bbsDataList.size(), is(1));
        assertThat(bbsDataList.get(0).getComment(), is("test"));
        bbsDataList = service.getBBSData(2);
        assertThat(bbsDataList.size(), is(1));
        assertThat(bbsDataList.get(0).getComment(), is("test2"));
    }
}
