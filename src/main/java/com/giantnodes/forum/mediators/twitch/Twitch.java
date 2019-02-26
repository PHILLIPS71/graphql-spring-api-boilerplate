package com.giantnodes.forum.api.user.twitch;

public class UserTwitch {

    private String id;
    private String token;
    private String display;
    private Integer followers;

    public UserTwitch(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
}
