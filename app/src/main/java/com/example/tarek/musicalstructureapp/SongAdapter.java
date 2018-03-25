package com.example.tarek.musicalstructureapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private Context mContext;

    /**
     * create a new song object
     * @param context is the current context (the activity)that the adapter is being create
     * @param songs_list is the list to be displayed
     */
    public SongAdapter(@NonNull Context context, ArrayList<Song> songs_list) {
        super(context, 0 , songs_list);
        mContext = context;
    }

    /**
     *
     * @param position of clicked item
     * @param convertView to listItem.xml
     * @param parent
     * @return listItemView which is been created to display songs as customized .
     */
    @Override
    public View getView (int position , View convertView , ViewGroup parent){
        View listItemView = convertView ;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            Song currentSong = getItem(position);
            TextView songName = listItemView.findViewById(R.id.id_song_name_list_item);
            songName.setText(currentSong.getSongName());
            TextView authorName = listItemView.findViewById(R.id.id_author_name_list_item);
            authorName.setText(currentSong.getSingerName());
        }
            return listItemView ; // the layout which contains 2 text views and image for each song
    }

}
