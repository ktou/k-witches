package kwitches.controller;

import kwitches.service.kgimport.KGImportService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class KgimportController extends Controller {

    @Override
    public Navigation run() throws Exception {
        new KGImportService().checkKG();
        return null;
    }
}
