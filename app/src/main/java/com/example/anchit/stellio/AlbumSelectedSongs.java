package com.example.anchit.stellio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlbumSelectedSongs extends AppCompatActivity {

    private ListView songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_album_selected_songs);

        Intent it = getIntent();
        ArrayList<String> songsList = it.getStringArrayListExtra("ALBUM_LIST");

        songs = findViewById(R.id.songs);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,songsList);
        songs.setAdapter(adapter);

    }
}
