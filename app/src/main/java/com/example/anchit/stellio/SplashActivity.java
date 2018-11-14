package com.example.anchit.stellio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {
    private VideoView videoView;
    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.INTERNET"};
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.Theme_AppCompat_NoActionBar);

        setContentView(R.layout.activity_splash);

        requestReadExternalStoragePermission();

    }

    private void requestReadExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                                                            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                                            ,Manifest.permission.INTERNET},
                        PERMISSION_REQUEST_CODE);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                                                            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                                            ,Manifest.permission.INTERNET},
                        PERMISSION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            playVideo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)    {
            case PERMISSION_REQUEST_CODE:   {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    playVideo();
                }   else    {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void playVideo() {
        videoView = findViewById(R.id.vid);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        videoView.start();
    }

    /*private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(),
                MainActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.drawable.logo));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        getApplicationContext().sendBroadcast(addIntent);
    }*/
}
