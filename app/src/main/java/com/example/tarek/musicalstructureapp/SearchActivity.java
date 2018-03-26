package com.example.tarek.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


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

        if (id == R.id.id_back_arrow_icon) {
            Intent categoryActivityIntent = new Intent(SearchActivity.this, NowPlayingSongActivity.class);
            startActivity(categoryActivityIntent);

            finish(); // to destroy this activity
        } else if (id == R.id.id_search_icon) {
            resultSearch(); // to run search
            songAdapter = new SongAdapter(this, resultSearch);
            searchResultListView.setAdapter(songAdapter);
        }

    }


    /**
     * setting intent to NowPlayingSongActivity
     */
    public void sendIntentToNowPlayingSongActivity() {
        Intent intentToNowPlayingSongActivity = new Intent(SearchActivity.this, NowPlayingSongActivity.class);
        if (currentSong != null) {
            intentToNowPlayingSongActivity.putExtra(CURRENT_SONG, currentSong);
        }
        if (allSongs != null) {
            // resend all songs again to NowPlayingSongActivity to be available to sharing again
            // because if this if not sent again it will not work !
            intentToNowPlayingSongActivity.putExtra(ALBUM, allSongs);
        }
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
            allSongs = (ArrayList<Song>) comingIntents.getSerializableExtra(ALBUM);
        } else {
            // take no action it just for avoid exeptions
        }

    }

    /**
     * to search for songs matched input user text
     */
    public void resultSearch() {
        resultSearch = new ArrayList<>();
        if (allSongs != null) {
            for (int i = 0; i < allSongs.size(); i++) {
                if (allSongs.get(i).getSongName().toLowerCase().matches(getSearchText() + ".*")) {
                    resultSearch.add(allSongs.get(i));
                }
            }
        } else {
            // intent coming from NowPlayingSong works only if the user go from Categories to Now Playing Song
            // but if he search then return to NowPlayingSong then want to search again it makes error
            // because there is no intent carrying album to search in it.
            Toast.makeText(getBaseContext(), "No songs , intent works just once", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * to finish the app when back btn pressed
     */
    public void onBackPressed() {
        finish();
    }

    /**
     * to get input text from user
     *
     * @return input text after validating it
     */
    public String getSearchText() {
        searchText = searchEditText.getQuery().toString();
        return validateText(searchText);
    }

    /**
     * to check the input text by some conditions
     *
     * @param text
     * @return validated text
     */
    public String validateText(String text) {

        if (text.length() > R.integer.max_text_size)
            text = text.substring(0, R.integer.max_text_size); // return 1-20th letters only

        return text;
    }


}
