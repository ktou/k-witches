package kwitches.service.kgimport.dao;

import kwitches.model.FetchedIdModel;

import org.slim3.datastore.Datastore;

public class KGFetchedIdDao {

    private static KGFetchedIdDao instance = new KGFetchedIdDao();

    private KGFetchedIdDao() {
    }

    public static KGFetchedIdDao getInstance() {
        return instance;
    }

    private FetchedIdModel getModel(String key) {
        return Datastore.getOrNull(
            FetchedIdModel.class,
            Datastore.createKey(FetchedIdModel.class, key));
    }

    public int getFetchedId(String key) {
        FetchedIdModel model = getModel(key);
        return model == null ? 0 : model.getFetchedId();
    }

    public void setFetchedId(String key, int fetchedId) {
        FetchedIdModel model = getModel(key);
        if (model == null) {
            model = new FetchedIdModel();
            model.setKey(Datastore.createKey(FetchedIdModel.class, key));
        }
        model.setFetchedId(fetchedId);
        Datastore.put(model);
    }

}
