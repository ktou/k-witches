package kwitches.controller.twitter;

import kwitches.service.twitter.TwitterAccessService;
import kwitches.service.twitter.TwitterRequestService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class FetchController extends Controller {

    @Override
    public Navigation run() throws Exception {
        TwitterAccessService access = new TwitterAccessService();
        if (access.isAuthorized() == false) {
            return redirect(new TwitterRequestService().getAuthURL());
        }
        // TODO check tweet and sign

        return null;
    }
}
