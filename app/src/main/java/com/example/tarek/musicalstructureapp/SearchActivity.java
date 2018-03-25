package com.example.tarek.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class SearchActivity  extends AppCompatActivity implements View.OnClickListener{


    private static final String CURRENT_SONG = "currentSong";
    private static final String ALBUM = "album";
    private ArrayList<Song> allSongs;
    private SongAdapter songAdapter;
    private ListView searchResultListView;
    private SearchView searchEditText;
    private String searchText;
    private ArrayList<Song> resultSearch;
    private Song currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        setAllViews(); // to initiate all views

        getIntents(); // to get albums from Categories intent


        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSong = resultSearch.get(position);
                sendIntentToNowPlayingSongActivity();
            }
        });


    }

    public void setAllViews() {
        ImageView backButton = findViewById(R.id.id_back_arrow_icon);
        backButton.setOnClickListener(this);
        ImageView searchButton = findViewById(R.id.id_search_icon);
        searchButton.setOnClickListener(this);


        searchEditText = findViewById(R.id.id_search_edit_text);
        searchResultListView = findViewById(R.id.id_list_view);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_back_arrow_icon){
            Intent categoryActivityIntent = new Intent(SearchActivity.this, NowPlayingSongActivity.class);
            startActivity(categoryActivityIntent);

            finish(); // to destroy this activity
        }else if (id == R.id.id_search_icon) {
            songAdapter = new SongAdapter(this , resultSearch(allSongs) );
            searchResultListView.setAdapter(songAdapter);
        }

    }


    /**
     * setting intent to NowPlayingSongActivity
     */
    public void sendIntentToNowPlayingSongActivity() {
        Intent intentToNowPlayingSongActivity = new Intent(SearchActivity.this, NowPlayingSongActivity.class);
        intentToNowPlayingSongActivity.putExtra(CURRENT_SONG, currentSong);
        startActivity(intentToNowPlayingSongActivity);
    }

    /**
     * to run getIntent() code
     */
    public void getIntents() {
        Intent comingIntents = getIntent();
        /**
         * check if there is intent then get it's Extra
         */
        if (comingIntents.hasExtra(ALBUM)) {
            ArrayList<Song> album = (ArrayList<Song>) comingIntents.getSerializableExtra(ALBUM);
            allSongs = album;
        } else {
            // take no action it just for avoid exeptions
        }

    }

    /**
     * to search for songs matched input user text
     * @return ArrayList contains matched name song
     */
    public ArrayList<Song> resultSearch (ArrayList<Song> songs){
        resultSearch = new ArrayList<>();
        for (int i = 0 ; i< songs.size() ; i++){
            if(songs.get(i).getSongName().toLowerCase().matches(getSearchText()+".*")){
                resultSearch.add(allSongs.get(i));
            }
        }
        return resultSearch;
    }

    /**
     * to finish the app when back btn pressed
     */
    public void onBackPressed(){
        finish();
    }

    /**
     * to get input text from user
     * @return  input text after validating it
     */
    public String getSearchText (){
        searchText = searchEditText.getQuery().toString();
        return validateText(searchText);
    }

    /**
     * to check the input text by some conditions
     * @param text
     * @return validated text
     */
    public String validateText(String text) {

        if (text.length() > R.integer.max_text_size)
            text = text.substring(0, R.integer.max_text_size); // return 1-20th letters only

        return text;
    }


}
