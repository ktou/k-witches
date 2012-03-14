package kwitches.service.twitter.dao;

import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;

import kwitches.model.NumberCounterModel;


public class MentionCounterDao {

    private static MentionCounterDao instance = new MentionCounterDao();

    private String KEY = "TWITTER_MENTION_ID";

    private MentionCounterDao(){}

    public static MentionCounterDao getInstance(){
        return instance;
    }

    private NumberCounterModel getModel() {
        return Datastore.getOrNull(
            NumberCounterModel.class,
            Datastore.createKey(NumberCounterModel.class, KEY));
    }

    public long getMentionCounter() {
        Long count = (Long) Memcache.get(KEY);
        if (count == null) {
            NumberCounterModel model = getModel();
            count = model == null ? 1L : model.getCounter();
        }
        return count.longValue();
    }

    public void setMentionCounter(long mentionCounter) {
        NumberCounterModel model = getModel();
        if (model == null) {
            model = new NumberCounterModel();
            model.setKey(Datastore.createKey(NumberCounterModel.class, KEY));
        }
        model.setCounter(mentionCounter);
        Datastore.put(model);
        Memcache.put(KEY, new Long(mentionCounter));
    }


}
