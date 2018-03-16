package com.example.tarek.musicalstructureapp;


public class Song {

    private String songName; // song name
    private String authorName; // author name
    private final int NO_IMAGE_PROVIDED = -1; // Constant value for if there isn't any image
    private int imageResourceId = NO_IMAGE_PROVIDED; // as default value for image id of song


    public Song (String song_name, String author_name){
        songName = song_name;
        authorName = author_name;
    }

    public Song (String song_name, String author_name ,int image_id){
        songName = song_name;
        authorName = author_name;
        imageResourceId = image_id;
    }


    public String getSongName() {
        return songName;
    }

    public String getAuthorName() {
        return authorName;
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
