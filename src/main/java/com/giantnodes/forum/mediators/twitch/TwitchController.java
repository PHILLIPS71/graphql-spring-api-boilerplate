package com.giantnodes.forum.api.user.twitch;

import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UpdateTwitch {

    @Autowired
    private UserDao dao;

    private final String clientId = "yv5yewcnf1z0hcy4qdqc2ykg25z8gy";

    @Scheduled(fixedDelay = 3000)
    public void getAccountInfo() throws IOException, ParseException {
        for (User user : dao.all()) {
            if (user.getTwitch() == null) {
                continue;
            }

            URL url = new URL("https://api.twitch.tv/helix/users?id=" + user.getTwitch().getId());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + user.getTwitch().getToken());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject json = (JSONObject) new JSONParser().parse(line);
                JSONArray array  = (JSONArray) json.get("data");
                JSONObject data = (JSONObject) array.get(0);
                System.out.println(data.get("display_name"));
            }

            reader.close();
            dao.getRepository().save(user);
        }
    }
//
//    @Scheduled(fixedDelay = 60_000)
//    public void getFollowersTotal() throws IOException {
//        for (User user : dao.all()) {
//            if (user.getTwitch() == null) {
//                continue;
//            }
//
//            URL url = new URL("https://api.twitch.tv/helix/users/follows?to_id=" + user.getTwitch().getId() + "&total");
//            URLConnection connection = url.openConnection();
//            connection.setRequestProperty("Authorization", "Bearer " + user.getTwitch().getToken());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Map<String, Object> json = new Gson().fromJson(line, Map.class);
//                user.getTwitch().setFollowers(Math.round(Float.parseFloat(json.get("total").toString())));
//            }
//
//            reader.close();
//            dao.getRepository().save(user);
//        }
//
//    }

}
