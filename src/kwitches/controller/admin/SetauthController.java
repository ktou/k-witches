package kwitches.controller.admin;

import kwitches.service.AuthService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

public class SetauthController extends Controller {

    private AuthService service = new AuthService();
    
    @Override
    public Navigation run() throws Exception {
        service.set(new RequestMap(request));
        return redirect("./authlist");
    }
}
