package com.example.pianoafrik.volleytest.model;


public class Feed {

    private String body;
    private String picture;
    private String mesterId;
    private String time;

    public Feed(String body, String picture, String mesterId, String time) {
        this.body = body;
        this.picture = picture;
        this.mesterId = mesterId;
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMesterId() {
        return mesterId;
    }

    public void setMesterId(String mesterId) {
        this.mesterId = mesterId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
