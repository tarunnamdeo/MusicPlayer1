package com.example.tarun.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mp;
    ArrayList<File> mySongs;
    int position;
    Uri u;
    //creativity
    private TextView leftTime;
    private TextView rightTime;


    private double startTime = 0;
    private double finalTime = 0;

    //for playerlayout
    SeekBar sb;
    Button btPlay,btPv,btFF,btFB,btNxt;
    // for seekbar
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btPlay=(Button)findViewById(R.id.btPlay);
        btNxt=(Button)findViewById(R.id.btNxt);
        btPv=(Button)findViewById(R.id.btPv);
        btFF=(Button)findViewById(R.id.btFF);
        btFB=(Button)findViewById(R.id.btFB);

        //for left,right
        leftTime =(TextView) findViewById(R.id.leftTime);
        rightTime =(TextView) findViewById(R.id.rightTime);

        btPlay.setOnClickListener(this);
        btNxt.setOnClickListener(this);
        btPv.setOnClickListener(this);
        btFF.setOnClickListener(this);
        btFB.setOnClickListener(this);

        sb=(SeekBar) findViewById(R.id.seekBar);

        updateSeekBar=new Thread(){
            @Override
            public void run(){
               int totalDuration=mp.getDuration();
               int currentPosition=0;
               sb.setMax(totalDuration);
               while (currentPosition<totalDuration){
                   try{
                       sleep(500);
                       currentPosition=mp.getCurrentPosition();
                       sb.setProgress(currentPosition);
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
               }
            }
        };

        if(mp!=null){
            mp.stop();
            mp.release();
        }


        Intent i=getIntent();
        Bundle b=i.getExtras();
        mySongs=(ArrayList)b.getParcelableArrayList("songlist");
        position=b.getInt("pos",0);

        u=Uri.parse(mySongs.get(position).toString());
        mp= MediaPlayer.create(getApplicationContext(),u);
        mp.start();

        sb.setMax(mp.getDuration());
        updateSeekBar.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){
                    mp.seekTo(progress);
                }


               // SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss"); // old method ..

                startTime=mp.getCurrentPosition();
                finalTime=mp.getDuration();            //first min 2nd sec
               leftTime.setText (String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime))));

               rightTime.setText(String.format("%d:%d",
                       TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                       TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                               TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                       finalTime))));
          //      int currentPos=mp.getCurrentPosition();
         //       int duration =mp.getDuration();
         //       leftTime.setText(dateFormat.format(new Date(currentPos)));


          //      rightTime.setText(dateFormat.format(new Date(duration - currentPos)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btPlay:
                if (mp.isPlaying()){
                  //  btPlay.setText(">");
                    btPlay.setBackgroundResource(android.R.drawable.ic_media_play);
                    mp.pause();
                }
                else{
                    //btPlay.setText("||");
                    btPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
                    mp.start();
                }

                break;
            case R.id.btFF:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;

            case R.id.btFB:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;

            case R.id.btNxt:
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                u=Uri.parse(mySongs.get(position).toString());
                mp= MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;

            case R.id.btPv:
                mp.stop();
                mp.release();
                position=(position-1<0)?mySongs.size()-1:position-1;
                /*if(position-1<0){
                    position=mySongs.size()-1;

                }else {
                    position=position-1;
                }*/
                u=Uri.parse(mySongs.get(position).toString());
                mp= MediaPlayer.create(getApplicationContext(),u);
                mp.start();
               sb.setMax(mp.getDuration());
                break;



        }

    }
}
