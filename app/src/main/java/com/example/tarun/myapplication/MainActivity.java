package com.example.tarun.myapplication;

import android.Manifest;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;
import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;

import android.widget.MediaController.MediaPlayerControl;
import android.widget.Toast;

import com.example.tarun.myapplication.pulsem.Pulse_Activity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import static java.nio.file.Paths.get;

public class MainActivity extends AppCompatActivity implements MediaPlayerControl {
    private ArrayList<Song> songList;
    private ListView songView;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;


    MaterialSearchView searchView;
    String[] lstSource={"one","two","three","fore","Apple","nokia","Micro","hat"};

  //  SearchView searchView;
 //   MenuItem searchMenuItem;
   // AutoCompleteTextView auto;

    // Search EditText
   // private EditText inputSearch;

  //  SongAdapter adapter;
   // String[] Name;


    // Listview Adapter
  //  ArrayAdapter<String> adapter;

   // private SearchAdapter adapter;
    ArrayList<Integer> search;


    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    // ArrayList for Listview
  ArrayList<HashMap<String, String>> productList;


    private ImageView albumcover;

    //faltu
  // ArrayAdapter<String> adapter;

    private MusicControler controller;


    private boolean paused = false, playbackPaused = false;
    private boolean amp;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ArrayBlockingQueue Albumid;
    private Object[] Album_id;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //albumcover = (ImageView) findViewById(R.id.cover);


        // Intent intent= new Intent(this,MediaBox.class);
        //startActivity(intent);

//        inputSearch = (EditText) findViewById(R.id.inputSearch);
  //      auto= (AutoCompleteTextView) findViewById(R.id.autocomplete_country);



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Music");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView=(MaterialSearchView)findViewById(R.id.search_view);
      //  ActionBar actionbar = getSupportActionBar();
       // actionbar.setDisplayHomeAsUpEnabled(true);
      //  actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //for DrawerToggle

        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

       NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        int id = menuItem.getItemId();
                        FragmentManager fragmentManager = getFragmentManager();
                        if (id == R.id.nav_camera) {
                           // fragmentManager.beginTransaction().replace(R.id.content_frame, new firstfragment()).commit();
                            fragmentManager.addOnBackStackChangedListener(null);
                            Toast.makeText(MainActivity.this,"Developement Phase....\n Wait For Updates",Toast.LENGTH_LONG).show();

                        }
                        if (id == R.id.nav_gallery) {
                            Intent intent=new Intent(MainActivity.this, MediaBox.class);
                            startActivity(intent);

                            Toast.makeText(MainActivity.this,"Developement Phase....\n Wait For Updates",Toast.LENGTH_LONG).show();

                        }
                        if (id == R.id.nav_slideshow) {

                            Toast.makeText(MainActivity.this,"Developement Phase....\n Wait For Updates",Toast.LENGTH_LONG).show();

                        }
                        if (id == R.id.nav_wifi) {
                            Intent intent=new Intent(MainActivity.this, Pulse_Activity.class);
                            startActivity(intent);


                            Toast.makeText(MainActivity.this,"Developement Phase....\n Wait For Updates",Toast.LENGTH_LONG).show();

                        }
                        if (id == R.id.nav_searchsong) {


                            Toast.makeText(MainActivity.this,"Developement Phase....\n Wait For Updates",Toast.LENGTH_LONG).show();

                        }


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here


                        return true;
                    }
                });


        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

// MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
// app-defined int constant

                return;
            }
        }



   /*   songView = (ListView) findViewById(R.id.song_list);
     //try.........
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource);
        songView.setAdapter(adapter);


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //if closed Serach view,listview return default....
                songView = (ListView) findViewById(R.id.song_list);

                ArrayAdapter adapter= new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,lstSource);
                songView.setAdapter(adapter);


            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty()){
                    List<String> lstFound=new ArrayList<String>();
                    for(String item:lstSource){
                        if (item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter= new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,lstFound);
                    songView.setAdapter(adapter);
                }
                else {
                    // if search text is null return defult
                    ArrayAdapter adapter= new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,lstSource);
                    songView.setAdapter(adapter);
                }
                return true;
            }
        });   */

        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<Song>();
        getSongList();


      //  recyclerViewcard=(RecyclerView)findViewById(R.id.new_list_viewrec);
       // recyclerViewcard.setHasFixedSize(true);
       // recyclerViewcard.setLayoutManager(new LinearLayoutManager(this));

     //   adapter=new MyAdapter(this,songList);
     //   recyclerViewcard.setAdapter(adapter);
        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        final SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
      //  auto.setAdapter();



        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songlist",songList) );
            }
        });

        //  adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songView);


        // Get the intent, verify the action and get the query
     /*   Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }*/




        //adapter = new SearchAdapter(this, Collections.emptyList());

        // Adding items to listview
        //adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.song_title,get());
       //songView.setAdapter(adapter);
       // ArrayAdapter<Song> adapter=new ArrayAdapter<Song>(this,android.R.layout.simple_list_item_1,songList);
       // songView.setAdapter(adapter);


       // musicHelper=new MusicHelper();
     //   musicHelper.getSongList(songList);
      //  controller=new MusicControler(this);




      /* songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                songPosition=position;

                try{
                    Song song=(Song)songView.getItemAtPosition(position);
                    musicHelper.getSong(song);

                    musicHelper.playSong();


                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error in playing", Toast.LENGTH_LONG).show();
                }
                try{
                    setController();

                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error in media control", Toast.LENGTH_LONG).show();
                }
                //Intent intent=new Intent(MainActivity.this,MusicService.class);

                //startService(intent);
            }

        });*/
       
       /* inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){
           @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
               if(actionId == EditorInfo.IME_ACTION_SEARCH){
                   performSearch();
                   return true;
               }
               return false;
           }

            private void performSearch() {
            }
        });*/
      /* Name=new String[]{};
       for(int i=0;i<Name.length;i++){
           Addnew PN=new Addnew(Name[i]);
       }*/


     /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
              return true;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
              return false;
          }
      });
*/
    //    inputSearch.addTextChangedListener(new TextWatcher() {

         //   @Override
           // public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
             //   String eho=inputSearch.getText().toString().toLowerCase()(Locale.getDefault());
             //   adapter.myFilter(eho);
                // When user changed the Text
               //
        //      MainActivity.this..getFilter().filter(cs);

                /*Collections.sort(songList, new Comparator<Song>() {
                    public int compare(Song a, Song b) {
                        return a.getTitle().compareTo(b.getTitle());
                    }
                });*/
               // MainActivity.this..getFilter().filter(cs);

               //  MainActivity.this.adapter.getFilter().filter(cs);
              //  MainActivity.this..getFilter().filter(cs);
               // MainActivity.this.notifyDataSetChanged();
          //  }

          //  @Override
        //    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
            //                              int arg3) {
                // TODO Auto-generated method stub

         //   }

         //   @Override
        //    public void afterTextChanged(Editable arg0) {
         //       // TODO Auto-generated method stub
        //    }
       // });


        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this,"heeeee",Toast.LENGTH_SHORT).show();
            }
        });

       setController();

        // imageView =(ImageView)findViewById(R.id.image);
        //getArtUriFromMusicFile(this,null);


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }

    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            Long albumId = musicCursor.getLong(musicCursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            int durationColumn=musicCursor.getColumnIndex
                   (android.provider.MediaStore.Audio.Media.DURATION);
            // int artColumn = musicCursor.getColumnIndex
            //(android.provider.MediaStore.Audio.Albums.ALBUM_ART);
          /*  Cursor cursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    new String[] {String.valueOf(albumcover)},
                    null);
            if (cursor.moveToFirst()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                // do whatever you need to do
            }*/
            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);


            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                //Image thisImage = getArtUr
                //getArtUriFromMusicFile(). thisImage=
                // String Albumids = musicCursor.getString(album);
                // getArtUriFromMusicFile(this,);

               long thisDuration = musicCursor.getLong(durationColumn);

                if (thisDuration>2000)//2:33 min 140000
                {
                songList.add(new Song(thisId, thisTitle, thisArtist,thisDuration));
               }


            }
            while (musicCursor.moveToNext());
        }
    }

    //faltu kuch


    public static Uri getArtUriFromMusicFile(Context context, File file) {
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = {MediaStore.Audio.Media.ALBUM_ID};

        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND " + MediaStore.Audio.Media.DATA + " = '"
                + file.getAbsolutePath() + "'";
        final Cursor cursor = context.getApplicationContext().getContentResolver().query(uri, cursor_cols, where, null, null);
    /*
     * If the cusor count is greater than 0 then parse the data and get the art id.
     */
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
            cursor.close();
            return albumArtUri;
        }
        return Uri.EMPTY;


    }


    public void songPicked(View view) {


        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
       controller.show(0);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_shuffle:
                //shuffle
                musicSrv.setShuffle();
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv = null;
                System.exit(0);
                break;

        }
        return super.onOptionsItemSelected(item);


    }

    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


        // Get the SearchView and set the searchable configuration
     //   SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // SearchView searchView = (SearchView) menu.findItem(R.id.searchnew).getActionView();
        // Assumes current activity is the searchable activity
       // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

    /*    SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.searchnew);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);   */

        return true;
    }

 /*   @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        SongAdapter.getFilter().filter(newText);

        return true;
    }*/

    private void setController() {
        //set the controller up

        controller = new MusicControler(this);

       controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);




    }

    //play next
    private void playNext() {
        musicSrv.playNext();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
       controller.show(0);
    }

    //play previous
    private void playPrev() {
        musicSrv.playPrev();
        if (playbackPaused) {
           setController();
            playbackPaused = false;
        }
       controller.show(0);

    }


    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (musicSrv != null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();

        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null & musicBound && musicSrv.isPng())
            return musicSrv.getPosn();

        else return 0;

    }

    @Override
    public void seekTo(int i) {
        musicSrv.seek(i);
    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    protected void onPause() {
        super.onPause();

        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
           setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
      controller.hide();
        super.onStop();

    }


}