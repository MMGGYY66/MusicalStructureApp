package com.example.tarek.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener   {

    private ArrayList<String> categoriesList ;
    private ArrayList<Song> allSongs;
    private Song currentSong;
    private SongAdapter songAdapterCat;
    private ListView categoryListView;
    private TextView songName;
    private TextView categoryName;
    private String currentCategoryName;

    public CategoriesActivity(){
        setAllSongs(); // set all songs /** used it to be able to get all songs Arraylist in other activity rather than sending by Intents
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        currentSong = allSongs.get(0); // as default placeholder data
        setCategoriesList(); // initiate categories list
        setCurrentCategoryName(getString(R.string.default_category_name)); // set current category name with default value

         // initiate all views and set their onClickListener
        ImageView categoryButton = (ImageView) findViewById(R.id.id_categories_list_icon);
        categoryButton.setOnClickListener(this);

        categoryName = (TextView) findViewById(R.id.id_category_name_text_view);
        songName = (TextView) findViewById(R.id.id_song_name_text_view);


        ImageView searchButton = (ImageView) findViewById(R.id.id_search_icon);
        searchButton.setOnClickListener(this);

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

        categoryListView = (ListView) findViewById(R.id.id_category_list_view);

        getIntents(); // if there is intent run it's code

        // initiating categories adapter
        // customized layout with categories_item and displayed data from ArrayList<String> categories list
        ArrayAdapter<String> categoriesAdapter  = new ArrayAdapter<String>(this,R.layout.categories_item,categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
        // setting onItemClickListener for each item in the list view here assigned to 3 categories as placeholder data
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tagListView = categoryListView.getTag().toString(); // get tag
                // check if tag is folder then show clicked category/folder
                // or if it songs then play clicked one
                // tag to avoid interface code running as it is one listview with one OnItemClickListener
                if (tagListView.equals(getString(R.string.category_list_view_folders))){
                    categoryListView.setTag(getString(R.string.category_list_view_songs));
                    categoryName.setText(categoriesList.get(position));
                    if (position == 0){
                        setSongAdapterCat((allSongs));
                    } else if (position == 1){
                        setSongAdapterCat(allSongs); // as placeholder data
                    } else if (position == 2){
                        setSongAdapterCat(allSongs); // as placeholder data
                    } else if (position == 3){
                        setSongAdapterCat(allSongs); // as placeholder data
                    }
                }else if(tagListView.equals(getString(R.string.category_list_view_songs))) {
                    currentSong = allSongs.get(position);
                    songName.setText(currentSong.getSongName());
                    sendIntentToMainActivity();
                }
                    categoryListView.setAdapter(getSongAdapterCat());
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_categories_list_icon){
            // categories
        }else if (id == R.id.id_search_icon){
            Intent searchActivityIntent = new Intent(CategoriesActivity.this, SearchActivity.class);
            startActivity(searchActivityIntent);
        }else if (id == R.id.id_delete_icon){
            Toast.makeText(getBaseContext() ,"delete" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_options_icon){
            Toast.makeText(getBaseContext() ,"options" , Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_skip_previous_icon){
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
     * code from https://stackoverflow.com/questions/21253303/exit-android-app-on-back-pressed
     * to finish the app when back btn pressed on CategoryActivity
     */
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    /**
     * setting intent to MainActivity
     */
    public void sendIntentToMainActivity(){
        Intent intentToMainActivity = new Intent(CategoriesActivity.this, MainActivity.class);
        intentToMainActivity.putExtra("songName",getCurrentSongName());
        /**
         * how to send Song object or it's image integer value by Intent !
         */
        startActivity(intentToMainActivity);
    }

    /**
     * to run getIntent() code
     */
    public void getIntents (){
        Intent comingIntents = getIntent();
        /**
         * check if there is intent then get it's Extra
         * else set the default/last values
         */
        if (comingIntents.hasExtra("currentSongName")){
            songName.setText(comingIntents.getStringExtra("currentSongName"));
        }
        songName.setText(currentSong.getSongName());
        categoryName.setText(currentCategoryName);

    }

    /**
     * to set the list with placeholder data
     */
    public void setCategoriesList(){
        categoriesList = new ArrayList<>();
        categoriesList.add("Now Playing playlist");
        categoriesList.add("My PlayList1");
        categoriesList.add("Favourite PlayList");
        categoriesList.add("Last played playList");

    }
    /**
     * to set songs as placeholder data
     */
    public void setAllSongs() {
        allSongs = new ArrayList<>();
        String[] songsNames = {"Hello", "Send My Love","I Miss You","When We Were Young", "Remedy",
                "Water Under the Bridge","River Lea","Love in the Dark","Love in the Dark","Million Years Ago","All I Ask"};
        String[] authorsNames = {"Adele Adkins, Greg Kurstin","Adkins, Max Martin, Shellback","Adkins, Paul Epworth",
                "Adkins, Tobias Jesso Jr", "Adkins, Ryan Tedder","Adkins, Kurstin","Adkins, Brian Burton","Adkins, Samuel Dixon",
                "Adkins, Kurstin", "Adkins, Bruno Mars, Philip Lawrence, Christopher Brody Brown","Adkins, Epworth",};
        for (int i = 0 ; i < songsNames.length ; i++){
                allSongs.add(new Song(songsNames[i] , authorsNames[i]  , R.drawable.ic_album_black_48dp));
        }
    }


    public ArrayList<Song> getAllSongs(){
        return allSongs;
    }
    /**
     * initiate SongAdapter1 with arrayList1 <Song>
     * @param cat
     */
    public void setSongAdapterCat(ArrayList<Song> cat) {
        this.songAdapterCat = new SongAdapter(this , cat);
    }
    public SongAdapter getSongAdapterCat() {
        return songAdapterCat;
    }
    public String getCurrentSongName() {
        return songName.getText().toString();
    }
    public void setCurrentCategoryName (String catName){
        this.currentCategoryName = catName;
    }
}
