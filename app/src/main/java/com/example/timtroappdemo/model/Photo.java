package com.example.timtroappdemo.model;

import java.io.Serializable;
import java.util.List;

public class Photo implements Serializable {
    private String img_url;

    public Photo() {
    }

    public Photo(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
