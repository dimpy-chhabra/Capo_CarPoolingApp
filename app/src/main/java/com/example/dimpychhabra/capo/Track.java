package com.example.dimpychhabra.capo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
/*
*Project : CAPO, fully created by
* Dimpy Chhabra, IGDTUW, BTech, IT
* Second year (as of 2017)
* Expected Class of 2019
* Please do not circulate as your own
* Criticism is appreciated to work on memory leaks and bugs
* Contact Info : Find me on Linked in : linkedin.com/in/dimpy-chhabra
*
*/
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Track extends BaseActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainerTrack, new Main_found_Frag(), BaseActivity.found_rides_Frag)
                    .commit();
        }
    }

    // Replace Login Fragment with animation
 /*   protected void replaceFoundRidesFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainerTrack, new Main_offered_Frag(),
                        BaseActivity.offered_rides_Frag).commit();
    }
    protected void replaceOfferedRidesFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainerTrack, new Main_found_Frag(),
                        BaseActivity.found_rides_Frag).commit();
    }
*/
    @Override
    public void onBackPressed() {

/*        Fragment Main_found_Frag = fragmentManager
                .findFragmentByTag(BaseActivity.found_rides_Frag);
        Fragment Main_offered_Frag = fragmentManager
                .findFragmentByTag(BaseActivity.offered_rides_Frag);
*/
        Fragment My_offered_rides_frag = fragmentManager
                .findFragmentByTag(BaseActivity.my_offered_ride_Frag);
        Fragment My_found_ride_Frag = fragmentManager
                .findFragmentByTag(BaseActivity.my_found_ride_Frag);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (My_offered_rides_frag != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainerTrack, new Main_offered_Frag(), BaseActivity.offered_rides_Frag)
                    .commit();
        } else if (My_found_ride_Frag != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainerTrack, new Main_found_Frag(), BaseActivity.found_rides_Frag)
                    .commit();
        } else {
            Intent i = new Intent(Track.this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }

    ///////////////////        ///////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile, menu);
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

        if (id == R.id.nav_home) {

            Intent i = new Intent(Track.this, MainActivity.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(Track.this, MyProfile.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_get_capo) {
            Intent i = new Intent(Track.this, GetCapo.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_lets_capo) {
            Intent i = new Intent(Track.this, LetsCapo.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_track) {


        } else if (id == R.id.nav_share) {
            String shareBody = "This is an invite by your dear friend to come and join this community where people care and are making a change!" +
                    "Install Capo today from ... ";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Greeting from Capo");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));

        } else if (id == R.id.nav_logout) {
            SharedPreferences preferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Name, "null");
            editor.putString(IS_LOGIN, "false");
            editor.putString(Phone, "null");
            editor.putString(College, "null");
            editor.putString(Email, "null");
            editor.putString(displaypic, "null");

            editor.commit();

            Intent i = new Intent(Track.this, LoginBaseActivity.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
