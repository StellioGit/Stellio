package com.example.anchit.stellio;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anchit.stellio.Fragments.SongsFragment;
import com.example.anchit.stellio.Fragments.TestFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TestFragment.OnFragmentInteractionListener,
                    SongsFragment.OnFragmentInteractionListener{
    private ArrayList<String> songList = new ArrayList<>();
    private ArrayList<String> artistList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    private Cursor songCursor;
    private HashMap<String,String> hash = new HashMap<>();
    private Map<String,String> sortedHash;
    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_main);

        FrameLayout frame1 = findViewById(R.id.frame1);
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


        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new SongsFragment()).commit();
        //doStuff();
    }

    public void sortArrays(ArrayList<String> arrayList) {
        Collections.sort(arrayList);
    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        /*for(i=0;i< songCursor.getColumnCount();i++)
            Log.i("Column_Name",songCursor.getColumnName(i));*/

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            /*int songAlbumArt = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);*/
            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentPath = songCursor.getString(songPath);
                String currentAlbum = songCursor.getString(songAlbum);
                /*String currentAlbumArt = songCursor.getString(songAlbumArt);*/


                hash.put(currentTitle,currentPath);
                sortedHash = new TreeMap<>(hash);
                arrayList.add(hash);

                songList.add(currentTitle);
                artistList.add(currentArtist);
                pathList.add(currentPath);
            }   while (songCursor.moveToNext());
        }
        sortArrays(songList);
        /*sortArrays(artistList);*/
        /*sortArrays(pathList);*/
    }

    public void doStuff()   {
        list = findViewById(R.id.list);
        getMusic();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (sortedHash.containsKey(songList.get(i))) {
                    Intent intent = new Intent(getBaseContext(), PlayerActivity.class);

                    Intent svc = new Intent(getBaseContext(), BackgroundSoundService.class);

                    Object path = sortedHash.values().toArray()[i];
                    intent.putExtra("path", String.valueOf(path));
                    startService(svc);
                    startActivity(intent);

                }   else {
                    Toast.makeText(MainActivity.this, "Song doesn't Exists..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_songs) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new SongsFragment()).commit();
        } else if (id == R.id.nav_album) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new TestFragment()).commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
