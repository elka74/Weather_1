package com.example.weather_;

public class Soc {
    private String description;
    private int picture;
    private boolean like;

    public Soc(String description, int picture, boolean like){
        this.description=description;
        this.picture=picture;
        this.like=like;
    }

    // геттеры
    public String getDescription(){
        return description;
    }

    public int getPicture(){
        return picture;
    }

    public boolean getLike(){
        return like;
    }
}
