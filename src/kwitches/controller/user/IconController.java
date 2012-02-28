package kwitches.controller.user;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import kwitches.model.ImageModel;
import kwitches.service.dao.ImageModelDao;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IconController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String keyString = request.getParameter("_");
        ImageModel imageModel;
        try {
            imageModel = ImageModelDao.GetInstance().getImage(keyString);
        } catch (Exception e) {
            return redirect("../images/avater.jpg");
        }

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
