package tn.igc.projectone.authentification.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import tn.igc.projectone.R;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;

public class SignUpActivity extends AppCompatActivity {
    Spinner formation;
    Map<String,String> majorvsid=new HashMap<>() ;
    String text;
    Button cnxlogin,cnxsignup;
    EditText nom,prenom,email,pass;
    Boolean isnomValidated,isPrenomValidated,isEmailValidated,isPassValidated;
    ArrayList<String> forma_list=new ArrayList<String>();
    ArrayAdapter<String> forma_adapter;
    private APIInterface apiInterface;
    String EMAIL_PATTERN = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";
    private int idUser;
    private EditText confirm;


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
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nom=(EditText)findViewById(R.id.editText3);
        prenom=(EditText)findViewById(R.id.editText4);
        email=(EditText)findViewById(R.id.editText5);
        pass=(EditText)findViewById(R.id.editText6);
        cnxlogin=(Button)findViewById(R.id.button5);
        formation=(Spinner) findViewById(R.id.spinner);
        confirm=(EditText)findViewById(R.id.confirm);
        buildMajorsdropdown();

        formation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text=formation.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //controls
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateEmail(email.getText().toString())==false){
                    email.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    email.setError("E-mail incorrect");

                }
                else{
                    email.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String etpasschange=s.toString();
                String confirmed=confirm.getText().toString();
                if(!(etpasschange.equals(confirmed))){
                    confirm.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    confirm.setError("mot de passe incorrect");
                }
                else
                {confirm.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));}
                if(etpasschange.length()<8){
                    pass.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    pass.setError("mot de passe courte");

                }
                else{
                    pass.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });
        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass1change = s.toString();
                String password = pass.getText().toString();


                if(!(pass1change.equals(password))){
                    confirm.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    confirm.setError("mot de passe incorrect");

                }
                else{
                    confirm.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });
        cnxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        //perform signup
        cnxsignup=(Button)findViewById(R.id.CNX);
        cnxsignup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String str_mail=email.getText().toString().trim();
                String str_pass=pass.getText().toString().trim();
                String strlastName = nom.getText().toString().trim();
                String strfirst= prenom.getText().toString().trim();
                text=formation.getSelectedItem().toString().trim();
                String id=majorvsid.get(text);

                apiInterface= APIClient.getClient().create(APIInterface.class);
                Call<Void> tentative=apiInterface.basicsignup(str_mail,str_pass,strfirst,strlastName,id);
                tentative.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()){
                            nom.setText("");
                            prenom.setText("");
                            email.setText("");
                            pass.setText("");
                            // error response, no access to resource?
                            new IOSDialog.Builder(SignUpActivity.this)
                                .setTitle("notification")
                                .setMessage("Utilisateur crée avec succés")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    //start your activity here
                                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(i);

                                }

                            }, 2000);


                        }else {
                            new IOSDialog.Builder(SignUpActivity.this)
                                .setTitle("notification")
                                .setMessage("Utilisateur existe déja!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();



                        }





                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Error", t.getMessage());

                    }
                });

                if (null == strlastName || strlastName.length() == 0  )
                {
                    nom.setError( "Entrer votre nom" );
                    isnomValidated = false;
                }
                else {
                    isnomValidated=true;
                }
                if (null == strfirst || strfirst.length() == 0)
                {
                    isPrenomValidated = false;
                    prenom.setError( "Entrer votre prenom" );
                }
                else {
                    isPrenomValidated=true;
                }
                if (null == str_mail || str_mail.length() == 0)
                {
                    isEmailValidated = false;
                    email.setError( "Entrer votre email" );
                }
                else {
                    isEmailValidated=true;
                }
                if (null == str_pass || str_pass.length() == 0)
                {
                    isPassValidated = false;
                    pass.setError( "Entrer votre mot de passe" );
                }
                else {
                    isPassValidated=true;
                }

            }



        });










    }
    //get majors from api
    private void buildMajorsdropdown() {

        apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> tentative=apiInterface.getAllMajors();
        tentative.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    JsonObject obj = response.body().get(i).getAsJsonObject();
                    majorvsid.put((String) obj.get("name").getAsString(),(String) obj.get("_id").getAsString());
                    forma_list.add((String) obj.get("name").getAsString());
                    Log.d("name major",obj.get("name").toString());

                }
                forma_adapter=new ArrayAdapter<String>(SignUpActivity.this,R.layout.formation_spinner_row,forma_list);
                forma_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
                formation.setAdapter(forma_adapter);




            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());

            }
        });






    }

}
