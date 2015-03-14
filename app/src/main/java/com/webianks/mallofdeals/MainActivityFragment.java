package com.webianks.mallofdeals;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by R Ankit on 3/14/2015.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private Button findMallsButton;
    private String city="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        findMallsButton = (Button) rootView.findViewById(R.id.find_mall_button);
        findMallsButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.find_mall_button:

                showAlertDialog();

                break;
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("City");
        alertDialog.setMessage("Enter your city!");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setPositiveButton("Find",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        city = input.getText().toString();

                        findMallsFromParse(city,getActivity());

                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();


        }



    private void findMallsFromParse(String city, final Context context) {

        String malls=" ";
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Malls");
        query.whereEqualTo("LocationCity",city);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if(e==null){

                    if(parseObjects.size()!=0){

                        Toast.makeText(context,"Malls :"+parseObjects.get(0).getString("NameOfMall").toString(),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,"No malls found.",Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

}
