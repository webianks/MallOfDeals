package com.webianks.mallofdeals;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by R Ankit on 3/14/2015.
 */
public class ApplicationClass extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        String app_id=getResources().getString(R.string.app_id);
        String client_key=getResources().getString(R.string.client_key);
        Parse.initialize(this,app_id,client_key);
    }
}
