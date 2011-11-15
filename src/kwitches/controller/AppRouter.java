package kwitches.controller;

import org.slim3.controller.router.RouterImpl;

public class AppRouter extends RouterImpl {
    public AppRouter() {
        addRouting(
            "/page/{num}/",
            "/?page={num}");
    }
}
