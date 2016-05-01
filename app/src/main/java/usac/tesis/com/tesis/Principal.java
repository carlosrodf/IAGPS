package usac.tesis.com.tesis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import usac.tesis.com.tesis.menuFragments.newProfileFragment;
import usac.tesis.com.tesis.menuFragments.profilesFragment;
import usac.tesis.com.tesis.menuFragments.mapFragment;
import usac.tesis.com.tesis.menuFragments.settingsFragment;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private PendingIntent pendingIntent;

    public static int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int i = preferences.getInt("tesisLaunches",1);
        if(i==1){
            alarmMethod();
            editor.putInt("tesisLaunches",10);
            editor.commit();
        }

        mapFragment mf = new mapFragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mf);
        ft.commit();
        this.lastId = R.id.nav_map;

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*String data = preferences.getString("tesisEmail","null");
        TextView userData = (TextView)findViewById(R.id.userData);
        userData.setText(data);*/
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
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
            Toast.makeText(getApplicationContext(),"Sesion finalizada",Toast.LENGTH_LONG).show();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("tesisLaunches",1);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map && lastId != id) {
            mapFragment mf = new mapFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,mf);
            ft.commit();
            lastId = id;
        } else if (id == R.id.nav_profiles && lastId != id) {
            profilesFragment pf = new profilesFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,pf);
            ft.commit();
            lastId = id;
        }  else if (id == R.id.nav_manage && lastId != id) {
            settingsFragment pf = new settingsFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, pf);
            ft.commit();
            lastId = id;
        }else if(id == R.id.nav_newProfile && lastId != id){
            newProfileFragment pf = new newProfileFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, pf);
            ft.commit();
            lastId = id;
        } else if (id == R.id.nav_share && lastId != id) {
            Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send && lastId != id) {
            Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alarmMethod(){
        try{
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            int interval = 1000 * 60 * 20;

            Intent intent = new Intent(this,monitoringService.class);
            this.pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

            Toast.makeText(getApplicationContext(),"Monitoreo iniciado", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error al iniciar el monitoreo", Toast.LENGTH_LONG).show();
        }
    }

}
