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

    private CategoriesActivity cats;
    private ListView nowPlayingListView;
    private ArrayList<Song> playingList;
    private SongAdapter songAdapterCat;
    private String currentSongName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_list);

        cats = new CategoriesActivity();
        playingList = cats.getAllSongs(); // as placeholder data

        // initiate all views and set their onClickListener
        ImageView backButton = (ImageView) findViewById(R.id.id_back_arrow_icon);
        backButton.setOnClickListener(this);

        ImageView deleteButton = (ImageView) findViewById(R.id.id_delete_icon);
        deleteButton.setOnClickListener(this);

        ImageView optionsButton = (ImageView) findViewById(R.id.id_options_icon);
        optionsButton.setOnClickListener(this);

        ImageView skipPreivButton = (ImageView) findViewById(R.id.id_skip_previous_icon);
        skipPreivButton.setOnClickListener(this);

        ImageView playSongButton = (ImageView) findViewById(R.id.id_play_song_icon);
        playSongButton.setOnClickListener(this);

        ImageView skipNextButton = (ImageView) findViewById(R.id.id_skip_next_icon);
        skipNextButton.setOnClickListener(this);

        LinearLayout playSongLinearLayout = (LinearLayout) findViewById(R.id.id_play_song_linearLayout);
        playSongLinearLayout.setOnClickListener(this);


        // initiating list view
        nowPlayingListView = (ListView) findViewById(R.id.id_category_list_view);

        songAdapterCat = new SongAdapter(this , playingList);

        nowPlayingListView.setAdapter(songAdapterCat);

        // when any item clicked , get it's name and send it in an intent to main activity
        nowPlayingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSongName = playingList.get(position).getSongName();
                sendIntentToMainActivity();
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_back_arrow_icon){
            sendIntentToMainActivity();
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
            sendIntentToMainActivity();
        }
    }



    /**
     * setting intent to MainActivity
     */
    public void sendIntentToMainActivity(){
        Intent intentToMainActivity = new Intent(NowPlayingListActivity.this, MainActivity.class);
        intentToMainActivity.putExtra("songName",currentSongName);
        startActivity(intentToMainActivity);
    }

    /**
     * to finish the app when back btn pressed
     */
    public void onBackPressed(){
        finish();
    }

}
