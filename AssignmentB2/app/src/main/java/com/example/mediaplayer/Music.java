package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Music extends AppCompatActivity {
    TextView songName,current_duration,duration;
    SeekBar sb;
    ImageButton next,play,pause,prev;
    public static MediaPlayer mediaPlayer;
    String sname;
    public static int position ;
    Thread updateSeekBar;
    Boolean playAgain;

    public String convertToTime(int dur,int flag) {
        int minutes = (int) Math.ceil(dur / 1000);
        String time = minutes/60 + ":";
        if( minutes % 60 < 10) time += ("0");
        if( minutes % 60 == 0 && flag == 0) time += "1";
        else time +=  minutes % 60;
        return time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        ArrayList<File> mySongs;

        position = 0;

        playAgain = false;
        songName = (TextView) findViewById(R.id.songName);

        sb = (SeekBar) findViewById(R.id.seekbar);
        next = (ImageButton) findViewById(R.id.next);
        pause = (ImageButton) findViewById(R.id.pause);
        prev = (ImageButton)findViewById(R.id.prev);

        current_duration = (TextView) findViewById(R.id.current_duration);
        current_duration.setText(convertToTime(0,0));
        duration = (TextView) findViewById(R.id.duration);



        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        sname = mySongs.get(position).getName().toString();

        String songname = (String) i.getStringExtra("songname");
        songName.setText(songname+"");
        songName.setSelected(true);

        position = bundle.getInt("pos");

        Uri u = Uri.parse(mySongs.get(position).toString());

        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
        mediaPlayer.start();
        sb.setProgress(0);
        sb.setMax(mediaPlayer.getDuration());

        duration.setText(convertToTime(mediaPlayer.getDuration(),0));

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while(true) {
                    try {
                        sleep(100);
                        currentPosition = mediaPlayer.getCurrentPosition();

                        current_duration.setText(convertToTime(currentPosition, 0));

                        sb.setProgress(Math.min(currentPosition, mediaPlayer.getDuration()));

                        if(currentPosition >= mediaPlayer.getDuration()) {
                            sb.setProgress(0);
                            current_duration.setText(convertToTime(0, 1));
                            pause.setImageResource(R.drawable.icon_play);
                        }
                        Log.i("MSG", "" + mediaPlayer.isPlaying());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateSeekBar.start();

        sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    pause.setImageResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                } else {
                    if(playAgain == true) {
                        changeSeekBar();
                    }

                    pause.setImageResource(R.drawable.icon_pause);
                    mediaPlayer.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(updateSeekBar.isAlive()) updateSeekBar.interrupt();

                mediaPlayer.stop();
                mediaPlayer.release();

                position = (position + 1) % mySongs.size();

                String songname = mySongs.get(position).getName().toString();

                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);

                songName.setText(songname);
                mediaPlayer.start();

                sb.setMax(mediaPlayer.getDuration());

                duration.setText(convertToTime(mediaPlayer.getDuration(),0));


                pause.setImageResource(R.drawable.icon_pause);


            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(updateSeekBar.isAlive()) updateSeekBar.interrupt();
                mediaPlayer.stop();
                mediaPlayer.release();

                position = (position - 1 + mySongs.size())  % mySongs.size();
                String songname = mySongs.get(position).getName().toString();

                Uri u = Uri.parse(mySongs.get(position).toString());

                pause.setImageResource(R.drawable.icon_pause);
                songName.setText(songname);
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);

                mediaPlayer.start();

                sb.setMax(mediaPlayer.getDuration());

                duration.setText(convertToTime(mediaPlayer.getDuration(),0));
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    public void changeSeekBar(){
        sb.setMax(mediaPlayer.getDuration());

        duration.setText(convertToTime(mediaPlayer.getDuration(),0));

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                playAgain = false;
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while(currentPosition <= totalDuration) {
                    try {
                        sleep(100);
                        currentPosition = mediaPlayer.getCurrentPosition();

                        Log.i("MSG","" + currentPosition);
                        current_duration.setText(convertToTime(currentPosition,0));

                        sb.setProgress(Math.min(currentPosition,mediaPlayer.getDuration()));

                        if(!mediaPlayer.isPlaying()) {
                            break;
                        }
                        Log.i("MSG","" + mediaPlayer.isPlaying());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                current_duration.setText(convertToTime(0,1));

                pause.setImageResource(R.drawable.icon_play);
                sb.setProgress(0);

                playAgain = true;
                Log.e("MSG",songName.getText() + "Stopped");
            }
        };
        updateSeekBar.start();
    }
}