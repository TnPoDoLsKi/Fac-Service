package tn.igc.projectone.authentification.activities;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.filiere.FiliereActivity;

public class LoginActivity extends Activity {
    EditText userName,userPassword;
    Button connect,inscrip,btn_continue;
    boolean isPasswordValidated,isUserValidated;
    private APIInterface apiInterface;
    private static String token;
    String EMAIL_PATTERN = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";
    private ConstraintLayout loginForm;

    public final boolean validateEmail(String target) {
        if (target !=null && target.length() > 1) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(target);
            return matcher.matches();
        } else if (target.length() == 0) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inscrip=(Button)findViewById(R.id.inscrip);
        loginForm = (ConstraintLayout) findViewById(R.id.loginform);
        btn_continue = (Button) findViewById(R.id.button3);

        //session
        if(!SaveSharedPreference.getMajor(getApplicationContext()).equals("")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("major",SaveSharedPreference.getMajor(getApplicationContext()));
            startActivity(intent);
        } else {
            loginForm.setVisibility(View.VISIBLE);
        }

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, FiliereActivity.class);
                startActivity(i);
            }
        });


        apiInterface= APIClient.getClient().create(APIInterface.class);
        inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        userName=(EditText)findViewById(R.id.editText);
        userPassword=(EditText)findViewById(R.id.editText2);
        connect=(Button)findViewById(R.id.button2);
        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String etpasschange=s.toString();
                if(etpasschange.length()<8){
                    userPassword.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    userPassword.setError("mot de passe courte");

                }
                else{
                    userPassword.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateEmail(userName.getText().toString())==false){
                    userName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    userName.setError("E-mail incorrect");

                }
                else{
                    userName.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strUserName = userName.getText().toString();
                final String strPassword = userPassword.getText().toString();

                Call<JsonObject> tentative=apiInterface.basicLogin(strUserName,strPassword);
                tentative.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()){
                            //new activity+realm
                            String token=response.body().get("token").getAsString();
                            String major=response.body().get("major").getAsString();
                            String majorname=response.body().get("majorName").getAsString();
                            SaveSharedPreference.setMajor(getApplicationContext(), major);
                            SaveSharedPreference.setToken(getApplicationContext(),token);
                            SaveSharedPreference.setMajorName(getApplicationContext(),majorname);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            // error response, no access to resource?
                            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);

                            dlgAlert.setMessage("wrong password or Email");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();

                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("Error", t.getMessage());

                    }
                });
            }
        });


    }


}
