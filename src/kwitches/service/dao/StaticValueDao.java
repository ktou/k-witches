package kwitches.service.dao;

import java.util.HashMap;
import java.util.Map;

import org.slim3.datastore.Datastore;

import kwitches.meta.StaticValueModelMeta;
import kwitches.model.StaticValueModel;

public class StaticValueDao {

    public static enum StaticValueType {
        TWITTERFETCH_CONSUMER_KEY,
        TWITTERFETCH_CONSUMER_SECRET_KEY,
        TWITTERFETCH_TWITTER_ID,
        TWITTERFETCH_DEFAULT_USER,
        USTREAM_CHANNEL_KEY,
    };

    private final static StaticValueModelMeta meta = StaticValueModelMeta.get();

    private static Map<StaticValueType, String> cache = new HashMap<StaticValueType, String>();;

    private StaticValueDao() {
    }

    public static String getValue(StaticValueType type) {
        String value = null;

        if(cache.containsKey(type)){
            value = cache.get(type);
        } else {
            StaticValueModel model =
                Datastore.getOrNull(
                    StaticValueModel.class,
                    Datastore.createKey(meta, type.toString()));
            if(model == null == false) value = model.getValue();
        }

        return value;
    }

    public static void setValue(StaticValueType type,String value){
        StaticValueModel model =
            Datastore.getOrNull(
                StaticValueModel.class,
                Datastore.createKey(meta, type.toString()));
        if(model==null){
            model = new StaticValueModel();
            model.setKey(Datastore.createKey(meta, type.toString()));
        }

        model.setValue(value);
        cache.put(type, value);

        Datastore.put(model);
    }

}
