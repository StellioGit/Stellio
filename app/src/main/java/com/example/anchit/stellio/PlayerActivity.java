package com.example.anchit.stellio;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private MediaPlayer mediaPlayer;
    private SeekBar mSeekBar;
    private TextView btnPlay, btnPause, current_time, total_time,songName,songAlbum;
    private ImageView play_pause, btn_next, btn_previous;
    private Handler handler = new Handler();
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_player);



        current_time = findViewById(R.id.current_time);
        total_time = findViewById(R.id.total_time);
        mSeekBar = findViewById(R.id.mSeekBar);
        songName = findViewById(R.id.songName);
        songAlbum = findViewById(R.id.songAlbum);

        /*btnPlay = findViewById(R.id.play_pause);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        btnPause = findViewById(R.id.play_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });*/

        play_pause = findViewById(R.id.play_pause);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
                animation.setInterpolator(interpolator);
            if (play_pause.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.btn_pause).getConstantState())  {
                mediaPlayer.pause();
                play_pause.startAnimation(animation);
                play_pause.setImageResource(R.drawable.btn_play);
                /*getAudioStats();
                initializeSeekBar();*/
            }

            else if (play_pause.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.btn_play).getConstantState())    {
                mediaPlayer.start();
                play_pause.startAnimation(animation);
                play_pause.setImageResource(R.drawable.btn_pause);
                /*getAudioStats();
                initializeSeekBar();*/
            }
            }
        });

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                btn_next.startAnimation(animation);
            }
        });

        btn_previous = findViewById(R.id.btn_previous);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                btn_previous.startAnimation(animation);
            }
        });

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String song_Name = intent.getStringExtra("songName");
        String song_Album = intent.getStringExtra("songAlbum");
        songName.setText(song_Name);
        songAlbum.setText(song_Album);
        /*Toast.makeText(this, "Trimmed Value is: " + path, Toast.LENGTH_LONG).show();*/

        /*mediaPlayer = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+path));*/

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            Log.d("SetDataSourcePath", path);
            File filePath = new File(path);
            FileInputStream is = new FileInputStream(filePath);
            mediaPlayer.setDataSource(is.getFD());
            mediaPlayer.prepare();
            is.close();
        } catch (Exception ex) {
            Log.i("Not Played", ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*mediaPlayer.reset();*/

        /*startActivity(new Intent(getBaseContext(),MainActivity.class));*/
    }

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
        Toast.makeText(this, "Duration = " + (mediaPlayer.getDuration()/1000) + "", Toast.LENGTH_SHORT).show();
        mSeekBar.setMax(mediaPlayer.getDuration()/1000);
        getAudioStats();
        initializeSeekBar();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getAudioStats();
                initializeSeekBar();

                if(mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
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
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    protected void getAudioStats(){
        long duration  = mediaPlayer.getDuration()/1000; // In milliseconds
        long due = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition())/1000;
        long pass = duration - due;

        String test = DateUtils.formatElapsedTime(pass);
        String test1 = DateUtils.formatElapsedTime(due);

        current_time.setText("" + test);
        //mDuration.setText("" + duration + " seconds");
        total_time.setText("" + test1);
    }

    protected void initializeSeekBar(){
        mSeekBar.setMax(mediaPlayer.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                int mCurrentPosition = 0;
                if(mediaPlayer!=null){
                    try {
                        mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000; // In milliseconds
                    }   catch(Exception e)  {
                        mediaPlayer.seekTo(0);
                    }
                }
                else    {
                    mCurrentPosition = 0;
                }

                if (mSeekBar != null)   {
                    mSeekBar.setProgress(mCurrentPosition);
                    getAudioStats();
                }
                if (!isDestroyed()) {
                    handler.postDelayed(mRunnable,1000);
                }
            }
        };
        if (!isDestroyed()) {
            handler.postDelayed(mRunnable,1000);
        }
    }
}