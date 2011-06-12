package kwitches.controller;

import java.io.PrintWriter;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

import kwitches.service.JsonService;

public class JsonController extends Controller {
    
    private JsonService service;
    
    @Override
    public Navigation run() throws Exception {
        search(new JsonService());
        return null;
    }
     
    public void search(JsonService service) throws Exception {
        this.service = service;
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        String jsonString = service.getJson(new RequestMap(request));
        pw.println(jsonString);
        pw.close();
    }
}
