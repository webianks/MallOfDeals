package com.webianks.mallofdeals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.webianks.mallofdeals.Adpaters.FragmentAdapter;
import com.webianks.mallofdeals.Login.LoginFragment;
import com.webianks.mallofdeals.Login.SignUpFragment;
import com.webianks.mallofdeals.Shoppers.ShopperEvents;
import com.webianks.mallofdeals.TabsLayout.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {


    private Toolbar toolbar;
    private SharedPreferences sharedpreferences;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        mSlidingTabLayout= (SlidingTabLayout) findViewById(R.id.slide_tabs);




            sharedpreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            boolean logged_in_user = sharedpreferences.getBoolean("logged_in", false);

            if (!logged_in_user) {

                Fragment fragment= new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,"LoginFragmentTag")
                        .commit();

            }



            else{

                String user=sharedpreferences.getString("user"," ");
                String email=sharedpreferences.getString("email"," ");
                String type=sharedpreferences.getString("type"," ");
                setUpEverthing(this,user,email,type);

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



    public  void setUpEverthing(Activity activity,String username,String email,String type) {

        setSupportActionBar(toolbar);
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        Drawable drawable=getResources().getDrawable(R.drawable.profile);

         if(type.contains("shopper")){
             drawable=getResources().getDrawable(R.drawable.shopper);
         }else if(type.contains("mall_admin")){
             drawable=getResources().getDrawable(R.drawable.mall);
         }else if(type.contains("retailer")){
             drawable=getResources().getDrawable(R.drawable.retailer);
         }



        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(username).withEmail(email).withIcon(drawable)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public void onProfileChanged(View view, IProfile profile) {

                    }
                })
                .build();


        Drawer.Result result = new Drawer()
                .withActivity(activity)
                .withHeader(R.layout.header)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
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

        finish();

        startActivity(new Intent(this,MainActivity.class));

    }
}
