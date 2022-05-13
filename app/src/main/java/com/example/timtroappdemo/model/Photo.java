package com.example.timtroappdemo.model;

import java.io.Serializable;

public class Photo implements Serializable {
    private int resourceId;

    public Photo(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
