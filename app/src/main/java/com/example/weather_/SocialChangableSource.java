package com.example.weather_;

public interface SocialChangableSource {
    void add();
    void delete();

    Soc getSoc(int position);

    int size();
}
