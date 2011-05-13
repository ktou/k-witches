package kwitches.service.twitter.dao;

import org.slim3.datastore.Datastore;

import kwitches.model.NumberCounterModel;


public class MentionCounterDao {

    private static MentionCounterDao instance = new MentionCounterDao();

    private MentionCounterDao(){}

    private String KEY = "TWITTER_MENTION_ID";

    public static MentionCounterDao getInstance(){
        return instance;
    }

    private NumberCounterModel getModel() {
        return Datastore.getOrNull(
            NumberCounterModel.class,
            Datastore.createKey(NumberCounterModel.class, KEY));
    }

    public long getMentionCounter() {
        NumberCounterModel model = getModel();
        return model == null ? 1L : model.getCounter();
    }

    public void setMentionCounter(long mentionCounter) {
        NumberCounterModel model = getModel();
        if (model == null) {
            model = new NumberCounterModel();
            model.setKey(Datastore.createKey(NumberCounterModel.class, KEY));
        }
        model.setCounter(mentionCounter);
        Datastore.put(model);
    }


}
