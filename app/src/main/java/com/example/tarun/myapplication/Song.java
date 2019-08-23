package com.example.tarun.myapplication;

import android.graphics.Typeface;

import java.util.ArrayList;

/**
 * Created by tarun on 3/6/2018.
 */



public class Song  {
    private long id;
    private String title;
    private String artist;
    long duration;
    private Typeface typeface;
    private ArrayList<Song> filteredList;
   // private FriendFilter friendFilter;
  //  private FriendListActivity activity;
    //private URI art;

    public Song(long songID, String songTitle, String songArtist ,long duration/*Uri image*/) {
        id = songID;
        title = songTitle;
        artist = songArtist;
        this.duration=duration;
        //art = image;

      //  typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/vegur_2.otf");

      //  getFilter();
    }

    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

  /*  @Override
    public Filter getFilter() {
      //  if (friendFilter == null) {
       //     friendFilter = new FriendFilter();
      //  }
       // return friendFilter;
  //  }
    // public URI getImage() {
  return null;}

*/

}
