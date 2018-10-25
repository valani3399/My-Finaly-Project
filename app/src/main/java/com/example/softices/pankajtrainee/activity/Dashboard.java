package com.example.softices.pankajtrainee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.db.DbHelper;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DebugUtils.backupDatabase(this, DbHelper.DATABASE_NAME);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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

        if (id == R.id.nav_profile) {
            Intent fp = new Intent(Dashboard.this, ProfileActivity.class);
            startActivity(fp);

        } else if (id == R.id.nav_username) {
            Intent fp = new Intent(Dashboard.this, RecyclerviewdemoActivity.class);
            startActivity(fp);


        } else if (id == R.id.nav_map) {
            Intent fp = new Intent(Dashboard.this, MapsActivity.class);
            startActivity(fp);


        } else if (id == R.id.nav_webservices) {
            Intent fp = new Intent(Dashboard.this, WebServisesActivity.class);
            startActivity(fp);


        } else if (id == R.id.nav_share) {
            Intent fp = new Intent(Dashboard.this, BckgroundWebServicesActivity.class);
            startActivity(fp);


        } else if (id == R.id.nav_con_provider) {
            Intent fp = new Intent(Dashboard.this, ContentproviderActivity.class);
            startActivity(fp);

        } else if (id == R.id.nav_broadcast) {
            Intent fp = new Intent(Dashboard.this, BrodcastActivity.class);
            startActivity(fp);

        } else if (id == R.id.nav_dialogbox) {
            Intent fp = new Intent(Dashboard.this, DialogBoxActivity.class);
            startActivity(fp);

        } else if (id == R.id.nav_tablayout) {
            Intent fp = new Intent(Dashboard.this, FragmentActivity.class);
            startActivity(fp);

        } else if (id == R.id.nav_logout) {
            //login false means direct redirect signin activity
            SigninActivity.shareprefer(this, false, "");
            Intent fp = new Intent(Dashboard.this, SigninActivity.class);
            startActivity(fp);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
