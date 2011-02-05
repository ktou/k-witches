package kwitches.controller.user;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import kwitches.meta.ImageModelMeta;
import kwitches.model.ImageModel;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class IconController extends Controller {

    private static final ImageModelMeta meta =  ImageModelMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        String keyString = request.getParameter("_");
        Key key;
        try {
            key = Datastore.stringToKey(keyString);
        } catch (Exception e) {
            return redirect("../images/avater.jpg");
        }
        ImageModel imageModel = Datastore.query(meta)
           .filter(meta.key.equal(key))
           .asSingle();

        String contentType = imageModel.getFileContentType();
        byte[] image = imageModel.getFileImage().getBytes();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(contentType);
        try {
            out.write(image, 0, image.length);
        } finally {
            if ( out != null ) try {
                out.close();
            } catch ( IOException e ) {
            }
        }
        return null;
    }
}
