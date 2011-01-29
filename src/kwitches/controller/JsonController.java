package kwitches.controller;

import java.io.PrintWriter;

import kwitches.service.JsonService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

public class JsonController extends Controller {

    private JsonService service = new JsonService();
    
    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        String jsonString = service.getJson(new RequestMap(request));
        pw.println(jsonString);
        pw.close();
        return null;
    }
}
