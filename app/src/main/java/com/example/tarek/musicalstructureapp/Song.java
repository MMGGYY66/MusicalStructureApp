package com.example.tarek.musicalstructureapp;

import android.graphics.drawable.BitmapDrawable;

import java.io.Serializable;

public class Song implements Serializable {

    private final int NO_IMAGE_PROVIDED = -1; // Constant value for if there isn't any image
    private String songName; // song name
    private String singerName; // singer name
    private BitmapDrawable image;
    private int imageResourceId;//= NO_IMAGE_PROVIDED; // as default value for image id of song


    public Song() {

    }
    public Song (String song_name, String author_name){
        songName = song_name;
        singerName = author_name;

    }

    public Song (String song_name, String author_name ,int image_id){
        songName = song_name;
        singerName = author_name;
        imageResourceId = image_id;
    }

    public Song(String song_name, String author_name, BitmapDrawable image_id) {
        songName = song_name;
        singerName = author_name;
        image = image_id;
    }




    public String getSongName() {
        return songName;
    }

    public String getSingerName() {
        return singerName;
    }


    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * to chech if there is image or not ?
     * @return
     */
    public boolean hasImage (){
        return imageResourceId != NO_IMAGE_PROVIDED;
    }


}
