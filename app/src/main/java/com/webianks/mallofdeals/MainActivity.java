package com.webianks.mallofdeals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.webianks.mallofdeals.Login.LoginFragment;
import com.webianks.mallofdeals.Login.SignUpFragment;


public class MainActivity extends ActionBarActivity {


    private Toolbar toolbar;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {


            sharedpreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);


            boolean logged_in_user = sharedpreferences.getBoolean("logged_in", false);

            if (!logged_in_user) {

                Fragment fragment= new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,"LoginFragmentTag")
                        .commit();

            }

            else{
                setDrawer(this);
            }

          }



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    public  void setDrawer(Activity activity) {

        Drawer.Result result = new Drawer()
                .withActivity(activity)
                .withHeader(R.layout.header)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withTag("logout")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // do something with the clicked item :D


                        if (drawerItem.getTag() == "logout") {
                            logoutFromApp();
                        }
                    }
                })
                .build();
    }


    private void logoutFromApp() {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("logged_in",false);
        editor.putString("user"," ");
        editor.commit();

        MainActivity.this.finish();
        startActivity(new Intent(this,MainActivity.class));

    }


}
