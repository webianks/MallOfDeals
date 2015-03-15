package com.webianks.mallofdeals.Retailers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.webianks.mallofdeals.Crop.CropClass;
import com.webianks.mallofdeals.ImageUtils.MatrixMethods;
import com.webianks.mallofdeals.ImageUtils.ReadDimenAndType;
import com.webianks.mallofdeals.ImageUtils.RotationIfNeeded;
import com.webianks.mallofdeals.ParseWorks.ParseFeedingWorksForRetailers;
import com.webianks.mallofdeals.R;


public class PostActivity extends ActionBarActivity implements View.OnClickListener {

    ImageButton postEverthing;
    RelativeLayout UploadImage;
    ImageButton cameraButton;
    private EditText questionAsked;
    private Bitmap imageToBePosted = null;
    private TextView userName;
    static Context con;
    String username;
    Typeface tfSlab;
    String inTable;
    TextView title;
    private SharedPreferences prefs = null;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        con = getApplicationContext();
        setContentView(R.layout.activity_post);

        questionAsked= (EditText) findViewById(R.id.etOption1);
        title=(TextView) findViewById(R.id.etQuestion);

        Intent intent=getIntent();
        if(intent!=null) {
            username = intent.getStringExtra("username");
            inTable  =intent.getStringExtra("inTable");
        }

        if(inTable.contains("sale")) {
            title.setText("Give the title of the sale !");
        }
         else if(inTable.contains("event")) {
            title.setText("Give the title of the event !");
        }
        else if(inTable.contains("coupon")) {
            title.setText("Give the title of the coupon !");
        }






        tfSlab = Typeface.createFromAsset(con.getAssets(),
                "fonts/RobotoSlab-Regular.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        UploadImage = (RelativeLayout) findViewById(R.id.postImage);
        cameraButton = (ImageButton) findViewById(R.id.uploadPic);
        UploadImage.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // imageToBePosted

        switch (v.getId()) {
            case R.id.postImage:
                CropClass.pickImage(PostActivity.this);
                break;
        }
    }




    public void uploadIt() {

        // imageToBePosted

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Posting..");

        String question;

        question = questionAsked.getText().toString();

        if (!question.equals("") && imageToBePosted!=null) {

            // start uploading post to server
            ParseFeedingWorksForRetailers.uploadPostToParse(this,username,question,imageToBePosted,inTable);
            finish();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropClass.REQUEST_PICK
                && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();

            MatrixMethods mm = new MatrixMethods();
            String filePath = mm.mainMatrixMethod(PostActivity.this,
                    selectedImage);

            ReadDimenAndType rdat = new ReadDimenAndType();
            rdat.readDimenAndTypeOfImage(filePath);
            imageToBePosted = rdat.decodeSampledBitmapFromFile(filePath, 400,
                    300);

            RotationIfNeeded rin = new RotationIfNeeded();
            imageToBePosted = rin.getRotatedImage(filePath, imageToBePosted);

            if (imageToBePosted != null) {

                Log.d("Webi", "image_is_not_null");
                Drawable background = new BitmapDrawable(getResources(),
                        imageToBePosted);
                UploadImage.setBackgroundDrawable(background);
                cameraButton.setVisibility(View.INVISIBLE);

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_post:

                uploadIt();
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void parseSavingCallback(boolean success_key, Context con) {
        // TODO Auto-generated method stub
        if (success_key) {
            Toast.makeText(con, "Successfully posted.", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(con, "Post unsuccessfull.", Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
