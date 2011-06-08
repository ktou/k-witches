package kwitches.controller;

import org.slim3.controller.Navigation;

public class SignmobileController extends SignController { 
    @Override
    public Navigation run() throws Exception {
        sign();
        return forward("/mobile");
    }
}
