package com.example.tarek.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity  extends AppCompatActivity implements View.OnClickListener{


    private CategoriesActivity cats;
    private ArrayList<Song> allSongs;
    private SongAdapter songAdapter;
    private ListView searchResultListView;
    private EditText searchEditText;
    private String searchText;
    private String chooseSongName ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        cats = new CategoriesActivity();
        allSongs = cats.getAllSongs();

        ImageView backButton = (ImageView) findViewById(R.id.id_back_arrow_icon);
        backButton.setOnClickListener(this);
        ImageView searchButton = (ImageView) findViewById(R.id.id_search_icon);
        searchButton.setOnClickListener(this);

        searchEditText = (EditText) findViewById(R.id.id_search_edit_text);
        searchResultListView = (ListView) findViewById(R.id.id_result_search_list_view);
        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseSongName = allSongs.get(position).getSongName();
                sendIntentToMainActivity();

            }
        });



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_back_arrow_icon){
            Intent categoryActivityIntent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(categoryActivityIntent);

            finish(); // to destroy this activity
        }else if (id == R.id.id_search_icon) {
            songAdapter = new SongAdapter(this , resultSearch(allSongs) );
            searchResultListView.setAdapter(songAdapter);
        }

    }


    /**
     * setting intent to MainActivity
     */
    public void sendIntentToMainActivity(){
        Intent intentToMainActivity = new Intent(SearchActivity.this, MainActivity.class);
        intentToMainActivity.putExtra("songName",chooseSongName);
        startActivity(intentToMainActivity);
    }

    /**
     * to search for songs matched input user text
     * @return ArrayList contains matched name song
     */
    public ArrayList<Song> resultSearch (ArrayList<Song> songs){
        ArrayList<Song> resultSearch = new ArrayList<Song>();
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
        searchText = searchEditText.getText().toString();
        return validateText(searchText);
    }

    /**
     * to check the input text by some conditions
     * @param text
     * @return validated text
     */
    public String validateText(String text) {

        if (text.length() > 20)
            text = "not valid";

        return text;
    }


}
