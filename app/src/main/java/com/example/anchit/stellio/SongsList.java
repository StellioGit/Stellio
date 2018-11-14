package com.example.anchit.stellio;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SongsList {

    private Cursor songCursor;

    private HashMap<String, String> unsortedTitlePathHashMap = new HashMap<>();
    private Map<String, String> sortedTitlePathTreeMap;
    private HashMap<String, String> unsortedTitleAlbumHashMap = new HashMap<>();
    private Map<String, String> sortedTitleAlbumTreeMap;
    private HashMap<String, String> unsortedAlbumTitleHashMap = new HashMap<>();
    private Map<String, String> sortedAlbumTitleTreeMap;
    private HashMap<String, String> ALBUMID_Name = new HashMap<>();

    private ArrayList<String> songs = new ArrayList<>();
    private ArrayList<String> albumID = new ArrayList<>();


    public SongsList() {

    }

    public ArrayList<String> getSongs(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do {
                String currentTitle = songCursor.getString(songTitle);
                songs.add(currentTitle);
            } while (songCursor.moveToNext());
        }
        songCursor.close();
        return songs;
    }

    public ArrayList<String> getAlbumID(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songAID = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do {
                String currentAID = songCursor.getString(songAID);
                albumID.add(currentAID);
            } while (songCursor.moveToNext());
        }
        songCursor.close();
        return albumID;
    }

    public HashMap<String, String> getALBUMName_ID(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentPath = songCursor.getString(songPath);
                unsortedTitlePathHashMap.put(currentTitle, currentPath);
            } while (songCursor.moveToNext());
        }
        songCursor.close();
        return unsortedTitlePathHashMap;
    }

    public HashMap<String, String> getUnsortedTitlePathHashMap(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentPath = songCursor.getString(songPath);
                unsortedTitlePathHashMap.put(currentTitle, currentPath);
            } while (songCursor.moveToNext());
        }
        songCursor.close();
        return unsortedTitlePathHashMap;
    }

    public Map<String, String> getSortedTitlePathTreeMap(Context context) {
        SongsList list = new SongsList();
        sortedTitlePathTreeMap = new TreeMap<>(list.getUnsortedTitlePathHashMap(context));
        return sortedTitlePathTreeMap;
    }

    public HashMap<String, String> getUnsortedTitleAlbumHashMap(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                String currentAlbum = songCursor.getString(songAlbum);
                String currentTitle = songCursor.getString(songTitle);

                unsortedTitleAlbumHashMap.put(currentTitle, currentAlbum);
            } while (songCursor.moveToNext());
        }

        for (int i = 0; i < unsortedTitleAlbumHashMap.size(); i++) {
            if (unsortedTitleAlbumHashMap.containsKey(null)) {
                Object obj = unsortedTitleAlbumHashMap.remove(unsortedTitleAlbumHashMap.get(i));
                unsortedTitleAlbumHashMap.put("<noalbum>", obj.toString());
            }
        }
        songCursor.close();
        return unsortedTitleAlbumHashMap;
    }

    public Map<String, String> getSortedTitleAlbumTreeMap(Context context) {
        SongsList list = new SongsList();
        sortedTitleAlbumTreeMap = new TreeMap<>(list.getUnsortedTitleAlbumHashMap(context));

        return sortedTitleAlbumTreeMap;
    }

    public HashMap<String, String> getUnsortedAlbumTitleHashMap(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                String currentAlbum = songCursor.getString(songAlbum);
                String currentTitle = songCursor.getString(songTitle);

                unsortedAlbumTitleHashMap.put(currentAlbum, currentTitle);
            } while (songCursor.moveToNext());
        }

        for (int i = 0; i < unsortedAlbumTitleHashMap.size(); i++) {
            if (unsortedAlbumTitleHashMap.containsKey(null)) {
                Object obj = unsortedAlbumTitleHashMap.remove(unsortedAlbumTitleHashMap.get(i));
                unsortedAlbumTitleHashMap.put("<noalbum>", obj.toString());
            }
        }
        songCursor.close();
        return unsortedAlbumTitleHashMap;
    }

    public Map<String, String> getSortedAlbumTitleTreeMap(Context context) {
        SongsList list = new SongsList();
        sortedAlbumTitleTreeMap = new TreeMap<>(list.getUnsortedAlbumTitleHashMap(context));

        return sortedAlbumTitleTreeMap;
    }

}
