package kwitches.service.twitter;

import kwitches.model.twitter.RequestTokenModel;

import org.slim3.datastore.Datastore;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;


public class TwitterRequestService {

    public String getAuthURL() {
        Twitter twitter = new TwitterFactory().getInstance();
        String url = "";
        try {
            RequestToken token = twitter.getOAuthRequestToken();
            RequestTokenModel model = new RequestTokenModel();
            model.setKey(Datastore.createKey(RequestTokenModel.class, token.getToken()));
            model.setToken(token);
            Datastore.put(model);

            url = token.getAuthorizationURL();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return url;
    }
}
