package kwitches.service.twitter;

import kwitches.service.dao.StaticValueDao;
import kwitches.service.dao.StaticValueDao.StaticValueType;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterInstanceManager {

    private static TwitterFactory factory =
        new TwitterFactory(
            new ConfigurationBuilder()
                .setOAuthConsumerKey(
                    StaticValueDao
                        .getValue(StaticValueType.TWITTERFETCH_CONSUMER_KEY))
                .setOAuthConsumerSecret(
                    StaticValueDao
                        .getValue(StaticValueType.TWITTERFETCH_CONSUMER_SECRET_KEY))
                .build());

    public static Twitter getTwitterInstance(){
        return factory.getInstance();
    }

    public static Twitter getTwitterInstance(AccessToken token){
        return factory.getInstance(token);
    }

}
