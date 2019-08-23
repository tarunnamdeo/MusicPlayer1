package com.example.tarun.myapplication;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tarun.myapplication.Utility.Utility;

import org.w3c.dom.Text;

/**
 * Created by tarun on 3/6/2018.
 */

public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private int selectedPosition;
   // Context context;

    LinearLayout songLay;


    public SongAdapter(Context c, ArrayList<Song> theSongs) {
        //this.context = c;
        songs = theSongs;
        songInf = LayoutInflater.from(c);
        // this.peopleArr.addAll(titlee);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        // TODO Auto-generated method stub
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.song,parent,false);
      //  ImageView playbt = (ImageView) songLay.findViewById(R.id.iv_play);

     /*   Song song = songs.get(position);
        if (song != null) {

            if (selectedPosition == position) {
                  convertView.setBackground(ContextCompat.getColor(context,R.color.colorPrimary));
                playbt.setVisibility(View.VISIBLE);
            } else {
                  convertView.setBackground(ContextCompat.getColor(context,R.color.darkGray));
                playbt.setVisibility(View.INVISIBLE);
            }
        }*/


            //map to song layout
            RelativeLayout songLay = (RelativeLayout) songInf.inflate
                    (R.layout.song, parent, false);
            //get title and artist views
            TextView songView = (TextView) songLay.findViewById(R.id.song_title);
            TextView artistView = (TextView) songLay.findViewById(R.id.song_artist);
            ImageView artView = (ImageView) songLay.findViewById(R.id.image);


            TextView songDuration = (TextView) songLay.findViewById(R.id.tv_duration);
            //get song using position
            Song currSong = songs.get(position);
            //get title and artist strings
            songView.setText(currSong.getTitle());
            artistView.setText(currSong.getArtist());
            String duration = Utility.convertDuration(currSong.getDuration());
            songDuration.setText(duration);


            //set position as tag
            songLay.setTag(position);
            return songLay;
        }

  /*  public void myFilter(String name){
        name=name.toLowerCase(Locale.getDefault());
        songs.clear();
        if(name.length()==0){
            songs.addAll(peopleArr);
        }else {
            for(Addnew PL:peopleArr){
                if(PL.getTitle().toLowerCase(Locale.getDefault()).contains(name)){
                    songs.add(PL);
                }
            }
        }

  notifyDataSetChanged();


    }*/

    }


