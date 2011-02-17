package kwitches.service;

import java.util.List;

import kwitches.model.BBSDataModel;
import kwitches.service.dao.BBSDataModelDao;


public class MobileService {

    private static BBSDataModelDao bbsDao = BBSDataModelDao.GetInstance();
    
    public List<BBSDataModel> getMobileBBSData(int offset, int limit) throws Exception {
        List<BBSDataModel> bbsDataList = bbsDao.getBBSDataList(offset, limit);
        return bbsDataList;
    }
    
    public int getNextOffset(int offset, int limit) {
        int maxId = BBSDataModelDao.getMaxId();
        int maxPage = maxId / limit + 1;
        if (maxPage > offset / limit + 1) {
            return offset + limit;
        } else {
            return -1;
        }
    }

    public int getPrevOffset(int offset, int limit) {
        if (offset == 0) {
            return -1;
        } else {
            return offset - limit;
        }
    }
}
