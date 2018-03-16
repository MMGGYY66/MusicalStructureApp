package com.example.tarek.musicalstructureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView albumImage;
    private ImageView favouriteButton ;
    private ImageView volumeButton ;
    private  TextView songName ;
    private String currentSongName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_song);

        songName = (TextView) findViewById(R.id.id_song_name_text_view);

        getIntents(); // run get intent code

        ImageView backButton = (ImageView) findViewById(R.id.id_back_arrow_icon);
        backButton.setOnClickListener(this);

        ImageView currentPlayListButton = (ImageView) findViewById(R.id.id_now_playing_list_icon);
        currentPlayListButton.setOnClickListener(this);

        ImageView searchButton = (ImageView) findViewById(R.id.id_search_icon);
        searchButton.setOnClickListener(this);

        ImageView optionsButton = (ImageView) findViewById(R.id.id_options_icon);
        optionsButton.setOnClickListener(this);

        favouriteButton = (ImageView) findViewById(R.id.id_favourite_icon);
        favouriteButton.setOnClickListener(this);

        albumImage = (ImageView) findViewById(R.id.id_image_song);
        albumImage.setOnClickListener(this);

        ImageView shuffleButton = (ImageView) findViewById(R.id.id_shuffle_icon);
        shuffleButton.setOnClickListener(this);

        ImageView repeatButton = (ImageView) findViewById(R.id.id_repeat_icon);
        repeatButton.setOnClickListener(this);

        volumeButton = (ImageView) findViewById(R.id.id_volume_icon);
        volumeButton.setOnClickListener(this);

        ImageView skipPreivButton = (ImageView) findViewById(R.id.id_skip_previous_icon);
        skipPreivButton.setOnClickListener(this);

        ImageView playSongButton = (ImageView) findViewById(R.id.id_play_song_icon);
        playSongButton.setOnClickListener(this);

        ImageView skipNextButton = (ImageView) findViewById(R.id.id_skip_next_icon);
        skipNextButton.setOnClickListener(this);

        ImageView mainCategoriesButtons = (ImageView) findViewById(R.id.id_categories_list_icon);
        mainCategoriesButtons.setOnClickListener(this);

        ImageView playButton = (ImageView) findViewById(R.id.id_play_song_icon);
        playButton.setOnClickListener(this);

        ImageView categoryButton = (ImageView) findViewById(R.id.id_categories_list_icon);
        categoryButton.setOnClickListener(this);

        SeekBar songTime = (SeekBar) findViewById(R.id.id_seekbar);
        songTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.id_back_arrow_icon) {
            sendIntentToCategoryActivity();
        }
        else if (id == R.id.id_now_playing_list_icon){
            sendIntentToNowPlayingListActivity();
        }
        else if (id == R.id.id_search_icon){
            Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchActivityIntent);
        }
        else if (id == R.id.id_options_icon){
            Toast.makeText(getBaseContext() ,"options" , Toast.LENGTH_LONG).show();
            // options code
        }
        else if (id == R.id.id_favourite_icon){
            // favourite button doesn't responding from 1st click ! or 1st click doesn't change tag !
            String tag = favouriteButton.getTag().toString();
            if(tag.equals(getString(R.string.tag1_favourite_icon))) {
                favouriteButton.setTag(getString(R.string.tag2_favourite_icon));
                favouriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_48dp));
            } else if(tag.equals(getString(R.string.tag2_favourite_icon))){
                favouriteButton.setTag(getString(R.string.tag1_favourite_icon));
                favouriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_48dp));
            }
        }
        else if (id == R.id.id_shuffle_icon){
            Toast.makeText(getBaseContext() ,"shuffle" , Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.id_repeat_icon){
            Toast.makeText(getBaseContext() ,"repeat" , Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.id_volume_icon){
            // volume button doesn't responding from 1st click ! or 1st click doesn't change tag !
            String tag = volumeButton.getTag().toString();
            if(tag.equals(getString(R.string.tag1_favourite_icon)))  {
                volumeButton.setTag(getString(R.string.tag2_favourite_icon));
                volumeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_up_black_48dp));
            } else if(tag.equals(getString(R.string.tag2_favourite_icon))) {
                volumeButton.setTag(getString(R.string.tag1_favourite_icon));
                volumeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_mute_black_48dp));
            }
        }
        else if (id == R.id.id_skip_previous_icon){
            Toast.makeText(getBaseContext() ,"skip previous" , Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.id_play_song_icon){
            Toast.makeText(getBaseContext() ,"play" , Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.id_skip_next_icon){
            Toast.makeText(getBaseContext() ,"skip next" , Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.id_categories_list_icon){
            sendIntentToCategoryActivity();
        }

    }

    /**
     * setting Intent to Now playing list Activity
     */
    public void sendIntentToNowPlayingListActivity(){
        Intent intentToNowPlayingListActivity = new Intent(MainActivity.this, NowPlayingListActivity.class);
        startActivity(intentToNowPlayingListActivity);
    }

    /**
     * setting Intent to category Activity
     */
    public void sendIntentToCategoryActivity(){
        Intent intentToCategoryActivity = new Intent(MainActivity.this, CategoriesActivity.class);
        intentToCategoryActivity.putExtra("currentSongName",getCurrentSongName());
        startActivity(intentToCategoryActivity);
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
        if (comingIntents.hasExtra("songName")){
            setCurrentSongName(comingIntents.getStringExtra("songName"));
        }else {
            setCurrentSongName(getString(R.string.default_song_name));
        }
        songName.setText(currentSongName);
    }

    /**
     * to finish the app when back btn pressed
     */
    public void onBackPressed(){
        finish();
    }

    public String getCurrentSongName() {
        return currentSongName;
    }
    public void setCurrentSongName(String songName) {
        this.currentSongName = songName;
    }

}
