package kwitches.controller.twitter;

import java.util.Collections;

import kwitches.service.twitter.TwitterAccessService;
import kwitches.service.twitter.TwitterRequestService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import twitter4j.ResponseList;
import twitter4j.Status;

public class FetchController extends Controller {

    @Override
    public Navigation run() throws Exception {
        TwitterAccessService access = new TwitterAccessService();
        if (access.isAuthorized() == false) {
            return redirect(new TwitterRequestService().getAuthURL());
        }

        ResponseList<Status> mentions = new TwitterAccessService().getMentionsAtCounter();
        Collections.reverse(mentions);

        for(Status s:mentions){
            // TODO sign
        }

        return null;
    }
}
