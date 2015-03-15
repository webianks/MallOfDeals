package com.webianks.mallofdeals.Login;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.webianks.mallofdeals.MainActivity;
import com.webianks.mallofdeals.R;
import com.webianks.mallofdeals.TypefaceSpan;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpFragment extends Fragment implements
        OnClickListener {

    EditText regUser, regPas, resRePas;
    AutoCompleteTextView regEmail;
    Button regMain;
    TextView tvRegistrationErrors;
    String error;
    String[] userData = new String[4];
    boolean success = false;
    AlertDialog myDialog;
    ProgressDialog mProgressDialog;
    String changing_email, changing_user, changing_pass, changing_repass;
    SharedPreferences sharedpreferences;
    String username;
    TextView tvSignUp;
    String email;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_layout, container, false);


        regUser = (EditText) view.findViewById(R.id.etRegUserName);
        regPas = (EditText) view.findViewById(R.id.etRegUserPassword);
        resRePas = (EditText) view.findViewById(R.id.etRegUserPasswordAgain);
        regEmail = (AutoCompleteTextView) view.findViewById(R.id.etRegEmail);
        regMain = (Button) view.findViewById(R.id.btRegisterMain);
        tvSignUp = (TextView) view.findViewById(R.id.tvSignUp);


        regMain.setTransformationMethod(null);
        regMain.setOnClickListener(this);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedpreferences = getActivity().getSharedPreferences("my_prefs",
                Context.MODE_PRIVATE);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/RobotoSlab-Regular.ttf");

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/RobotoSlab-Regular.ttf");


        regUser.setTypeface(type);
        regPas.setTypeface(type);
        resRePas.setTypeface(type);
        regEmail.setTypeface(type);
        tvSignUp.setTypeface(tf);


        final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$",
                Pattern.CASE_INSENSITIVE);
        Account[] accounts = AccountManager.get(getActivity()).getAccounts();
        Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (EMAIL_PATTERN.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        regEmail.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<String>(emailSet)));


        SpannableString usernameHint = new SpannableString("  Username");
        usernameHint.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0,
                usernameHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString passwordHint = new SpannableString("  Password");
        passwordHint.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0,
                passwordHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString passwordAgain = new SpannableString("  Password Again");
        passwordAgain.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0,
                passwordAgain.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString emailText = new SpannableString("  Email");
        emailText.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0,
                emailText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString btRegText = new SpannableString("Sign Up");
        btRegText.setSpan(new TypefaceSpan(getActivity(), "Roboto-Thin.ttf"), 0,
                btRegText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        regUser.setHint(usernameHint);
        regUser.addTextChangedListener(new TextWatcher() {

                                           @Override
                                           public void afterTextChanged(Editable s) {
                                               // TODO Auto-generated method stub
                                               changing_user = s.toString();

                                           }

                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count,
                                                                         int after) {
                                               // TODO Auto-generated method stub

                                           }

                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before,
                                                                     int count) {
                                               // TODO Auto-generated method stub

                                           }
                                       }

        );

        regUser.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (changing_user == null) {
                        regUser.setError("Please enter a username.");
                    } else if (changing_user.length() <= 5)
                        regUser.setError("Username length should be greater than 5 characters.");

                    else if (changing_user.contains(" "))
                        regUser.setError("Username can't contain blank spaces.");

                }

            }
        });

        regPas.setHint(passwordHint);
        regPas.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                changing_pass = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

        });

        regPas.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (changing_pass == null) {
                        regPas.setError("Please enter a password.");
                    } else if (changing_pass.length() <= 4)
                        regPas.setError("Password too short.");

                }

            }
        });

        resRePas.setHint(passwordAgain);
        resRePas.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                changing_repass = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

        });

        resRePas.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (changing_repass == null) {
                        resRePas.setError("Please enter a password again.");
                    }

                    if (changing_pass != null && changing_repass != null) {

                        if (!changing_repass.equals(changing_pass))
                            resRePas.setError("Password not matched.");

                    }

                }

            }
        });

        regEmail.setHint(emailText);
        regEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                changing_email = s.toString();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

        });

        regEmail.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (changing_email == null) {
                        regEmail.setError("Please enter a email.");
                    } else {
                        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
                        Matcher m = p.matcher(changing_email);
                        boolean matchFound = m.matches();
                        if (!matchFound) {
                            regEmail.setError("Enter a valid email address.");
                        }

                    }
                }

            }

        });

        regMain.setText(btRegText);

    }

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {

            case R.id.btRegisterMain:

                userData[0] = regUser.getText().toString();// username
                userData[1] = regPas.getText().toString();// password
                userData[2] = resRePas.getText().toString();// passwordAgain
                userData[3] = regEmail.getText().toString();// email
                if (isConnectedNet()) {

                    if (!userData[0].equals("") && !userData[1].equals("")
                            && !userData[2].equals("") && !userData[3].equals("")) {
                        mProgressDialog.setMessage("Please, wait..");
                        mProgressDialog.show();
                        registerProcess(userData);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity());
                        builder.setMessage("All fields must be filled.")
                                .setTitle("Error")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    break;
                }

                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage("Connect to internet.").setTitle("Error")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
        }
    }

    private void registerProcess(String[] userData2) {

        username = userData2[0];
        String password = userData2[1];
        // String password_again=userData2[2];
        email = userData2[3];

        final ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {

                if (e == null) {

                    // User already exists ? then login
                    if (parseUsers.size() > 0) {
                        // loginUser(username, "Fake Password");
                    } else {
                        Log.d("Webi", "Going to signup");
                        showDialog(user);

                    }
                } else {

                    mProgressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage(e.getMessage()).setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Log.d("Webi", " " + e.getMessage());
                }
            }
        });

        Log.d("Webi", " " + success);

    }

    private void signupUser(ParseUser user, final String type) {


        user.put("type",type);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // navigateToHome();
                    success = true;
                    mProgressDialog.dismiss();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("logged_in", true);

                    editor.putString("user", username);
                    editor.putString("email",email);
                    editor.putString("type",type);

                    editor.commit();


                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("SignUpFragmentTag");
                    if(fragment != null) {
                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        if(type.contains("shopper"))
                            ((MainActivity)getActivity()).setUpEverthingForShopper(getActivity(), username, email, type);
                        else if(type.contains("retailer")){
                            ((MainActivity)getActivity()).setUpEverthingForRetailer(getActivity(), username, email, type);
                        }
                    }


                } else {
                    // Fail!
                    Log.d("Webi", " " + e.getMessage());
                    success = false;

                    if (e.getMessage().contains("ConnectTimeoutException")) {
                        error = "Time out ! Check connection.";
                    } else {
                        error = e.getMessage();
                    }

                    mProgressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage(error).setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


    boolean isConnectedNet() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }







    public void showDialog (final ParseUser user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_role)
                .setItems(R.array.roles_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String type="";

                       switch (which){
                           case 0:
                               type="mall_admin";
                               break;
                           case 1:
                               type="retailer";
                               break;
                           case 2:
                               type="shopper";
                               break;
                       }

                        signupUser(user,type);

                    }
                });
        Dialog dialog=builder.create();
        dialog.show();
    }



}