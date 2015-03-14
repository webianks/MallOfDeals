package com.webianks.mallofdeals.Login;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.webianks.mallofdeals.MainActivity;
import com.webianks.mallofdeals.R;
import com.webianks.mallofdeals.TypefaceSpan;


public class LoginFragment extends Fragment {
    SharedPreferences sp;
    EditText etUser, etPas;
    Button btLog, btReg;
    String userName, userPassword;
    public ProgressDialog mProgressDialog;
    String error;
    public static final String USER_KEY = "placeKey";
    private static final int RC_SIGN_IN = 0;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment_login,container,false);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        sp = getActivity().getSharedPreferences("my_prefs", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first_run", false);
        editor.commit();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity

        }



        etUser=(EditText) getActivity().findViewById(R.id.etLogUserName);
        etPas=(EditText)  getActivity().findViewById(R.id.etLogUserPassword);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoSlab-Regular.ttf");
        etUser.setTypeface(type);
        etPas.setTypeface(type);

        btLog=(Button)    getActivity().findViewById(R.id.btLogin);
        btLog.setTransformationMethod(null);
        btReg=(Button)    getActivity().findViewById(R.id.btRegister);
        btReg.setTransformationMethod(null);




        btLog.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        userName=etUser.getText().toString();
                        userPassword=etPas.getText().toString();

                        if(isConnectedNet()){
                            mProgressDialog.setMessage("Please, wait..");
                            if(!userName.equals("") && !userPassword.equals("")){
                                mProgressDialog.show();
                                loginUser(userName,userPassword);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("All fields must be filled.")
                                        .setTitle("Error")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Connect to internet.")
                                    .setTitle("Error")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                });

        btReg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                Fragment fragment= new SignUpFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,"SignUpFragmentTag")
                        .addToBackStack("LoginFragmentTag").commit();


            }

        });


        SpannableString usernameHint = new SpannableString("  Username");
        usernameHint.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0, usernameHint.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString passwordHint = new SpannableString("  Password");
        passwordHint.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0, passwordHint.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString btLoginText = new SpannableString("Log in");
        btLoginText.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0, btLoginText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString btRegText = new SpannableString("Sign  Up");
        btRegText.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0, btRegText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etUser.setHint(usernameHint);
        etPas.setHint(passwordHint);
        btLog.setText(btLoginText);
        btReg.setText(btRegText);


    }





    private void loginUser(final String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                // TODO Auto-generated method stub
                if (user != null) {
                    mProgressDialog.dismiss();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("logged_in",true);
                    editor.putString("user",username);
                    editor.commit();

                    navigateToDashboard(true,username);

                } else {
                    // Login failed!
                    mProgressDialog.dismiss();

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("logged_in",false);
                    editor.putString("user"," ");
                    editor.commit();




                    if(e.getMessage().contains("ConnectTimeoutException")){
                        error="Time out ! Check connection.";
                    }else{
                        error=e.getMessage();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(error)
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }



    public void navigateToDashboard(boolean success,String username){

        if(success==true){

            /*Intent intent =new Intent(getActivity(),Dashboard.class);
            intent.putExtra("username",username);
            intent.putExtra("logged_through","Pollstap");
            intent.putExtra("logged_number",1);
            getActivity().finish();
            startActivity(intent);*/

            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("LoginFragmentTag");
            if(fragment != null) {



                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                ((MainActivity)getActivity()).setDrawer(getActivity());


            }
        }


    }

    boolean isConnectedNet(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {


        if (requestCode == RC_SIGN_IN) {



        }
        else{
            Log.d("Webi", "onActivityResult of FacebookRequestCode");
            ParseFacebookUtils.finishAuthentication(requestCode, responseCode, intent);
            super.onActivityResult(requestCode, responseCode, intent);
        }


    }


}
