package kwitches.controller;

import kwitches.meta.UploadedDataMeta;
import kwitches.model.UploadedData;
import kwitches.service.UploadFileService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class FileController extends Controller {

    private UploadFileService service = new UploadFileService();

    private UploadedDataMeta meta = UploadedDataMeta.get();

    @Override
    public Navigation run() {
        UploadedData data =
            service.getData(asKey(meta.key), asLong(meta.version));
        byte[] bytes = service.getBytes(data);
        show(data.getFileName(), bytes);
        return null;
    }

}
