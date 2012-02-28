package kwitches.service.dao;

import kwitches.model.ImageModel;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ImageModelDao {

    private static ImageModelDao instance = new ImageModelDao();

    private static MemcacheService memcached = MemcacheServiceFactory
        .getMemcacheService();
    private static final String IMAGE = "image_";

    private ImageModelDao() {
    }

    public static ImageModelDao GetInstance() {
        return instance;
    }

    public void putImage(ImageModel imageModel) {
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(imageModel);
        tx.commit();
    }

    public void deleteImage(String key) {
        Transaction tx = Datastore.beginTransaction();
        Datastore.delete(Datastore.stringToKey(key));
        memcached.delete(IMAGE.concat(key));
        tx.commit();
    }

    public ImageModel getImage(String key) {
        ImageModel image = (ImageModel) memcached.get(IMAGE.concat(key));
        if(image == null){
            image = Datastore.getOrNull(ImageModel.class, Datastore.stringToKey(key));
            if (image != null)
                memcached.put(IMAGE.concat(key), image);
        }

        return image;
    }

}
