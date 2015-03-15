package com.webianks.mallofdeals.Crop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.widget.Toast;
import com.webianks.mallofdeals.R;

/**
 * Created by R Ankit on 3/15/2015.
 */
public class CropClass {

    public static int REQUEST_PICK=2;

    @SuppressLint({ "InlinedApi", "NewApi" })
    public static void pickImage(Activity activity) {

        try {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            activity.startActivityForResult(
                    intent, REQUEST_PICK);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, R.string.crop__pick_error,
                    Toast.LENGTH_SHORT).show();
        }

    }
}
