package kwitches.service;

import kwitches.service.dao.BBSDataModelDao;
import kwitches.text.tokenizer.AnalyzerInterface;
import kwitches.text.tokenizer.SimpleAnalyzer;

public class SearchService extends JsonService {
    
    private static BBSDataModelDao bbsDao = BBSDataModelDao.GetInstance();
    private static AnalyzerInterface analyzer = new SimpleAnalyzer();
    
    public String search(String word) throws Exception {
        return this.getJson(bbsDao.searchBBSData(analyzer.parse(word)));
    }
}
