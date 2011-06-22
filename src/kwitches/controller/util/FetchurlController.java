package kwitches.controller.util;

import java.net.URL;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class FetchurlController extends Controller {

    @Override
    public Navigation run() throws Exception {
        requestScope("content", new String(URLFetchServiceFactory
            .getURLFetchService()
            .fetch(new URL((String) request.getAttribute("url")))
            .getContent()));
        return forward("fetchurl.jsp");
    }
}
