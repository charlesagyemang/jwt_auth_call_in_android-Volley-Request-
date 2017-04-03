package com.example.pianoafrik.volleytest.model;

public class FeedPost {

    private String body, image;
    private int id;


    public FeedPost(String body, String image, int id) {
        this.body = body;
        this.image = image;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
