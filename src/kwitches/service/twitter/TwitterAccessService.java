package kwitches.service.twitter;

import kwitches.model.twitter.AccessTokenModel;
import kwitches.model.twitter.RequestTokenModel;
import kwitches.service.twitter.dao.MentionCounterDao;

import org.slim3.datastore.Datastore;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.google.appengine.api.datastore.Key;

public class TwitterAccessService {
    public boolean isAuthorized() {
        AccessTokenModel accessToken = getAccessToken();
        return (accessToken == null) == false;
    }

    private AccessTokenModel getAccessToken() {
        return Datastore.getOrNull(
            AccessTokenModel.class,
            Datastore.createKey(AccessTokenModel.class, "ktoubot"));
    }

    public void setAuth(String token) {
        Key requestModelKey =
            Datastore.createKey(RequestTokenModel.class, token);
        RequestToken requestToken =
            Datastore.get(RequestTokenModel.class, requestModelKey).getToken();
        Datastore.delete(requestModelKey);

        try {
            Twitter twitter = new TwitterFactory().getInstance();
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken);

            AccessTokenModel accessTokenModel = new AccessTokenModel();
            accessTokenModel.setKey(Datastore.createKey(
                AccessTokenModel.class,
                twitter.getScreenName()));
            accessTokenModel.setToken(accessToken);
            Datastore.put(accessTokenModel);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private ResponseList<Status> getMentions(long since)
            throws TwitterException {
        return new TwitterFactory()
            .getInstance(getAccessToken().getToken())
            .getMentions(new Paging(since));
    }

    public ResponseList<Status> getMentionsAtCounter() throws TwitterException {
        long counter = MentionCounterDao.getInstance().getMentionCounter();
        ResponseList<Status> mentions = getMentions(counter);

        if (mentions.size() > 0) {
            MentionCounterDao.getInstance().setMentionCounter(
                mentions.get(0).getId());
        }

        return mentions;
    }
}
