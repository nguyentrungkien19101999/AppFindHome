package com.example.timtroappdemo.model;

public class RoomAvailable {
    private int roomImage;
    private String roomTitle;
    private String roomPrice;
    private String roomAddress;
    private String roomDescription;

    public RoomAvailable(int roomImage, String roomTitle, String roomPrice, String roomAddress, String roomDescription) {
        this.roomImage = roomImage;
        this.roomTitle = roomTitle;
        this.roomPrice = roomPrice;
        this.roomAddress = roomAddress;
        this.roomDescription = roomDescription;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public int getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(int roomImage) {
        this.roomImage = roomImage;
    }
}
