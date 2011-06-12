package kwitches.service.api;

import java.util.Map;

public class LiveCheckService {

    public String generateLiveUserJson(Map<String, Object> input) {
        String username = (String) input.get("name");
        String location = (String) input.get("location");
        String userid = (String) input.get("id");
        return "{\"id\":\"" + userid + "\",\"name\":\"" + username + "\",\"location\":\"" + location + "\"}";
    }
}
