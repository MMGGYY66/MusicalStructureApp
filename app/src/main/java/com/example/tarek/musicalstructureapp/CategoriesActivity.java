package com.example.tarek.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String CURRENT_SONG_NAME = "currentSongName";
    private static final String CURRENT_SONG = "currentSong";
    private static final String ALBUM = "album";
    private ArrayList<String> categoriesList;
    private ArrayList<Song> allSongs;
    private Song currentSong;
    private SongAdapter songAdapterCat;
    private ListView categoryListView;
    private String currentCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllSongs(); // set all songs
        setNavBar();   // initiate NavBar
        setAllViews(); // initiate all views and set their onClickListener
        getIntents();  // if there is intent run it's code
        setCategoriesList(); // set categories list
        // initiating categories adapter
        // customized layout with categories_item and displayed data from ArrayList<String> categories list
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, R.layout.categories_item, categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
        //setSongAdapterCat(allSongs); // set adapter to show songs in list view
        // categoryListView.setAdapter(getSongAdapterCat());
        // setting onItemClickListener for each item in the list view here assigned to 3 categories as placeholder data
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tagListView = categoryListView.getTag().toString(); // get tag
                // check if tag is folder then show clicked category/folder
                // or if it songs then play clicked one
                // tag to avoid interface code running as it is one listview with one OnItemClickListener
                if (tagListView.equals(getString(R.string.category_list_default_tag))) {
                    categoryListView.setTag(getString(R.string.category_list_view_show_songs));
                    getSupportActionBar().setTitle(categoriesList.get(position));
                    if (position == 0) {
                        setSongAdapterCat((allSongs));
                    } else if (position == 1) {
                        setSongAdapterCat(allSongs); // as placeholder data
                    } else if (position == 2) {
                        setSongAdapterCat(allSongs); // as placeholder data
                    } else if (position == 3) {
                        setSongAdapterCat(allSongs); // as placeholder data
                    }
                } else {

                    currentSong = allSongs.get(position);
                    sendIntentNowPlayingSongActivity();
                }
                categoryListView.setAdapter(getSongAdapterCat());
            }

        });


    }

    /**
     * initiate all views and set their onClickListener
     */
    public void setAllViews() {

        setCurrentCategoryName(getString(R.string.default_category_name)); // set current category name with default value

        ImageView skipPreivButton = findViewById(R.id.id_skip_previous_icon);
        skipPreivButton.setOnClickListener(this);

        ImageView playSongButton = findViewById(R.id.id_play_song_icon);
        playSongButton.setOnClickListener(this);

        ImageView skipNextButton = findViewById(R.id.id_skip_next_icon);
        skipNextButton.setOnClickListener(this);

        LinearLayout playSongLinearLayout = findViewById(R.id.id_play_song_linearLayout);
        playSongLinearLayout.setOnClickListener(this);

        categoryListView = findViewById(R.id.id_list_view);
    }

    /**
     * initiate navBar
     */
    public void setNavBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_skip_previous_icon) {
            Toast.makeText(getBaseContext(), "skip previous", Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_play_song_icon) {
            Toast.makeText(getBaseContext(), "play", Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_skip_next_icon) {
            Toast.makeText(getBaseContext(), "skip next", Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_play_song_linearLayout){
            sendIntentNowPlayingSongActivity();
        } else if (id == R.id.id_song_name_text_view) {
            sendIntentNowPlayingSongActivity();
        }
    }


    /**
     * setting intent to NowPlayingSongActivity
     */
    public void sendIntentNowPlayingSongActivity() {
        Intent intentToNowPlayingSongActivity = new Intent(CategoriesActivity.this, NowPlayingSongActivity.class);
        if (allSongs != null) {
            intentToNowPlayingSongActivity.putExtra(ALBUM, allSongs); // to send album to be available to share with search activity
        }
        if (currentSong != null) {
            intentToNowPlayingSongActivity.putExtra(CURRENT_SONG, currentSong);
        }
        startActivity(intentToNowPlayingSongActivity);
    }

    /**
     * setting intent to NowPlayingSongActivity
     */
    public void sendIntentToSearchActivity() {
        Intent intentToSearchActivity = new Intent(CategoriesActivity.this, SearchActivity.class);
        if (allSongs != null) {
            intentToSearchActivity.putExtra(ALBUM, allSongs);
        }
        startActivity(intentToSearchActivity);
    }

    /**
     * to run getIntent() code
     */
    public void getIntents() {
        Intent comingIntents = getIntent();
        /**
         * check if there is intent from current playing song then get it's Extra
         * which carry song name as string
         * else set the default/last values
         */
        final TextView songName = findViewById(R.id.id_song_name_text_view);
        if (comingIntents.hasExtra(CURRENT_SONG_NAME)) {
            songName.setText(comingIntents.getStringExtra(CURRENT_SONG_NAME));
        } else {
            songName.setText(getString(R.string.default_song_name));
        }
        songName.setSelected(true);
        getSupportActionBar().setTitle(currentCategoryName);

    }

    /**
     * to set songs as placeholder data
     */
    public void setAllSongs() {
        allSongs = new ArrayList<>();
        String[] songsNames = {"Hello", "Send My Love", "I Miss You", "When We Were Young", "Remedy",
                "Water Under the Bridge", "River Lea", "Love in the Dark", "song ", "Million Years Ago", "All I Ask"};
        String[] authorsNames = {"Adele Adkins, Greg Kurstin", "Adkins, Max Martin, Shellback", "Adkins, Paul Epworth",
                "Adkins, Tobias Jesso Jr", "Adkins, Ryan Tedder", "Adkins, Kurstin", "Adkins, Brian Burton", "Adkins, Samuel Dixon",
                "Adkins, Kurstin", "Adkins, Bruno Mars, Philip Lawrence, Christopher Brody Brown", "Adkins, Epworth"};
        for (int i = 0; i < songsNames.length; i++) {
            allSongs.add(new Song(songsNames[i], authorsNames[i], R.drawable.luke_chesser_50_unsplash));
        }
    }

    public ArrayList<Song> getAllSongs() {
        return allSongs;
    }

    public SongAdapter getSongAdapterCat() {
        return songAdapterCat;
    }

    /**
     * initiate SongAdapter1 with arrayList1 <Song>
     *
     * @param cat
     */
    public void setSongAdapterCat(ArrayList<Song> cat) {
        this.songAdapterCat = new SongAdapter(this, cat);
    }

    /**
     * to set the list with placeholder data
     */
    public void setCategoriesList() {
        categoriesList = new ArrayList<>();
        categoriesList.add(getString(R.string.album1));
        categoriesList.add(getString(R.string.album2));
        categoriesList.add(getString(R.string.album3));
        categoriesList.add(getString(R.string.album4));

    }

    public void setCurrentCategoryName(String catName) {
        this.currentCategoryName = catName;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        getSupportActionBar().setTitle(item.getTitle());
        if (id == R.id.nav_now_playing_list) {
            setSongAdapterCat(allSongs); // as placeholder data
        } else if (id == R.id.nav_last_played_list) {
            setSongAdapterCat(allSongs); // as placeholder data
        } else if (id == R.id.nav_favourite_list) {
            setSongAdapterCat(allSongs); // as placeholder data
        } else if (id == R.id.nav_my_play_list1) {
            setSongAdapterCat(allSongs); // as placeholder data
        } else if (id == R.id.nav_share) {
            Toast.makeText(getBaseContext(), "to share songs", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_search) {
            sendIntentToSearchActivity();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        categoryListView.setTag(getString(R.string.category_list_view_show_songs));
        categoryListView.setAdapter(getSongAdapterCat()); // to show the list view
        return true;
    }
}



