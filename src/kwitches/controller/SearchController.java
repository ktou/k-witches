package kwitches.controller;

import java.io.PrintWriter;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import kwitches.service.SearchService;

public class SearchController extends Controller {
    
    private SearchService service = new SearchService();
    
    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        String word = asString("word");
        String jsonString = service.search(word);
        pw.println(jsonString);
        pw.close();
        return null;
    }

}
