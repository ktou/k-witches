package kwitches.controller;

import kwitches.service.MobileService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class MobileController extends Controller {

    private MobileService service = new MobileService();
    
    @Override
    public Navigation run() throws Exception {
        int offset = 0;
        int limit = 5;
        String body = "";
        try {
            offset = asInteger("offset");
        } catch (NullPointerException e) { }
        try {
            limit = asInteger("limit");
        } catch (NullPointerException e) { }
        try {
            body = asString("body");
        } catch (NullPointerException e) { }
        requestScope("bbsDataList", service.getMobileBBSData(offset, limit));
        requestScope("body", body);
        requestScope("nextPage", service.getNextOffset(offset, limit));
        requestScope("prevPage", service.getPrevOffset(offset, limit));
        requestScope("limit", limit);
        return forward("mobile.jsp");
    }
}
