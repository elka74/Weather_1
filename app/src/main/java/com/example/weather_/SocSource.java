package com.example.weather_;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class SocSource implements SocialDataSource {
    private List<Soc> dataSource;
    private Resources resources;
    public SocSource(Resources resources){
        dataSource = new ArrayList<>(3);
        this.resources = resources;
    }
    public SocSource init(){
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        int[] pictures = getImageArray();
        for (int i = 0; i < descriptions.length; i++){
           dataSource.add(new Soc(descriptions[i], pictures[i], false));
        }
        return  this;
    }
    public Soc getSoc(int position){
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    private int[] getImageArray(){
        @SuppressLint("Recycle") TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }
}
