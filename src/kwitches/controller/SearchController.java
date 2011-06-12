package kwitches.controller;

import org.slim3.controller.Navigation;

import kwitches.service.SearchService;

public class SearchController extends JsonController {
    
    @Override
    public Navigation run() throws Exception {
        search(new SearchService());
        return null;
    }

}
