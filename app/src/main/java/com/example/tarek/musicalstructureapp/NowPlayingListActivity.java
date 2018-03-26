package com.example.tarek.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NowPlayingListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CURRENT_PLAYLIST = "album";
    private static final String CURRENT_SONG = "currentSong";
    private ListView nowPlayingListView;
    private ArrayList<Song> playingList;
    private SongAdapter songAdapterCat;
    private Song currentSong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_list);

        setAllViews(); // set all views
        getIntents(); // to get intents

        songAdapterCat = new SongAdapter(this, playingList);

        nowPlayingListView.setAdapter(songAdapterCat);

        // when any item clicked , get it's name and send it in an intent to main activity
        nowPlayingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSong = playingList.get(position);
                sendIntentToNowPlayingSongActivity();
            }
        });


    }

    /**
     * to set all views and decrease lines in OnCreat method
     */
    public void setAllViews() {
        // initiate all views and set their onClickListener
        ImageView backButton = findViewById(R.id.id_back_arrow_icon);
        backButton.setOnClickListener(this);

        ImageView deleteButton = findViewById(R.id.id_delete_icon);
        deleteButton.setOnClickListener(this);

        ImageView optionsButton = findViewById(R.id.id_options_icon);
        optionsButton.setOnClickListener(this);

        ImageView skipPreivButton = findViewById(R.id.id_skip_previous_icon);
        skipPreivButton.setOnClickListener(this);

        ImageView playSongButton = findViewById(R.id.id_play_song_icon);
        playSongButton.setOnClickListener(this);

        ImageView skipNextButton = findViewById(R.id.id_skip_next_icon);
        skipNextButton.setOnClickListener(this);

        LinearLayout playSongLinearLayout = findViewById(R.id.id_play_song_linearLayout);
        playSongLinearLayout.setOnClickListener(this);


        // initiating list view
        nowPlayingListView = findViewById(R.id.id_list_view);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_back_arrow_icon){
            sendIntentToNowPlayingSongActivity();
        } else if (id == R.id.id_delete_icon){
            Toast.makeText(getBaseContext() ,"delete" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_options_icon){
            Toast.makeText(getBaseContext() ,"options" , Toast.LENGTH_LONG).show();
        }else if (id == R.id.id_skip_previous_icon){
            Toast.makeText(getBaseContext() ,"skip previous" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_play_song_icon){
            Toast.makeText(getBaseContext() ,"play" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_skip_next_icon){
            Toast.makeText(getBaseContext() ,"skip next" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_play_song_linearLayout){
            sendIntentToNowPlayingSongActivity();
        }
    }

    /**
     * to check if there is intent carrying current playlist or no
     */
    public void getIntents() {
        Intent comingIntents = getIntent();
        /**
         * check if there is intent then get it's Extra
         */
        if (comingIntents.hasExtra(CURRENT_PLAYLIST)) {
            ArrayList<Song> album = (ArrayList<Song>) comingIntents.getSerializableExtra(CURRENT_PLAYLIST);
            playingList = album;
        } else {
            // take no action it just for avoid exeptions
        }

    }

    /**
     * setting intent to NowPlayingSongActivity
     */
    public void sendIntentToNowPlayingSongActivity() {
        Intent intentToNowPlayingSongActivity = new Intent(NowPlayingListActivity.this, NowPlayingSongActivity.class);
        intentToNowPlayingSongActivity.putExtra(CURRENT_SONG, currentSong);
        startActivity(intentToNowPlayingSongActivity);
    }

    /**
     * to finish the app when back btn pressed
     */
    public void onBackPressed(){
        finish();
    }

}
