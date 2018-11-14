package com.example.anchit.stellio;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TestNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<String> songList = new ArrayList<>();
    private ArrayList<String> artistList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list1;
    private Cursor songCursor1;
    private HashMap<String,String> hash = new HashMap<>();
    private Map<String,String> sortedHash;
    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_test_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*doStuff();*/

    }

    /*public void sortArrays(ArrayList<String> arrayList) {
        Collections.sort(arrayList);
    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor1 = contentResolver.query(songUri, null, null, null, null);
        *//*for(i=0;i< songCursor.getColumnCount();i++)
            Log.i("Column_Name",songCursor.getColumnName(i));*//*

        if (songCursor1 != null && songCursor1.moveToFirst()) {
            int songTitle = songCursor1.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor1.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath = songCursor1.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songAlbum = songCursor1.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            *//*int songAlbumArt = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);*//*
            do {
                String currentTitle = songCursor1.getString(songTitle);
                String currentArtist = songCursor1.getString(songArtist);
                String currentPath = songCursor1.getString(songPath);
                String currentAlbum = songCursor1.getString(songAlbum);
                *//*String currentAlbumArt = songCursor.getString(songAlbumArt);*//*


                hash.put(currentTitle,currentPath);
                sortedHash = new TreeMap<>(hash);
                arrayList.add(hash);

                songList.add(currentTitle);
                artistList.add(currentArtist);
                pathList.add(currentPath);
            }   while (songCursor1.moveToNext());
        }
        sortArrays(songList);
        *//*sortArrays(artistList);*//*
        *//*sortArrays(pathList);*//*
    }

    public void doStuff()   {
        list1 = findViewById(R.id.list);
        getMusic();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songList);
        list1.setAdapter(adapter);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (sortedHash.containsKey(songList.get(i))) {
                    Intent intent = new Intent(getBaseContext(), PlayerActivity.class);

                    Object path = sortedHash.values().toArray()[i];
                    intent.putExtra("path", String.valueOf(path));
                    startActivity(intent);

                }   else {
                    Toast.makeText(getBaseContext(), "Song doesn't Exists..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
