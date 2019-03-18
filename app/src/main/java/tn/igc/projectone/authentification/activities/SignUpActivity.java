package tn.igc.projectone.authentification.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;

public class SignUpActivity extends AppCompatActivity {
    Spinner formation;
    String text;
    Button cnxlogin, cnxsignup;
    EditText nom, prenom, email, pass;
    Boolean isnomValidated, isPrenomValidated;
    ArrayList<String> forma_list = new ArrayList<String>();
    ArrayAdapter<String> forma_adapter;
    private APIInterface apiInterface;
    String EMAIL_PATTERN = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";
    private int idUser;


    public final boolean validateEmail(String target) {
        if (target != null && target.length() > 1) {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nom = (EditText) findViewById(R.id.editText3);
        prenom = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        cnxlogin = (Button) findViewById(R.id.button5);
        formation = (Spinner) findViewById(R.id.spinner);
        buildMajorsdropdown();
        //forma_list.add("fia1");

        formation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),adapterView.getItemIdAtPosition(i), Toast.LENGTH_LONG).show();
                text = formation.getSelectedItem().toString();

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
                if (validateEmail(email.getText().toString()) == false) {
                    email.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    email.setError("E-mail incorrect");

                } else {
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
                String etpasschange = s.toString();
                Toast.makeText(getApplicationContext(), etpasschange, Toast.LENGTH_SHORT).show();
                if (etpasschange.length() < 8) {
                    pass.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    pass.setError("mot de passe courte");

                } else {
                    pass.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });
        cnxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        //perform signup
        cnxsignup = (Button) findViewById(R.id.CNX);
        cnxsignup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String str_mail = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                String strlastName = nom.getText().toString().trim();
                String strfirst = prenom.getText().toString().trim();
                Toast.makeText(SignUpActivity.this, text, Toast.LENGTH_LONG).show();

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<HashMap> tentative = apiInterface.basicsignup(str_mail, str_pass, "student", strfirst, strlastName, text);
                tentative.enqueue(new Callback<HashMap>() {
                    @Override
                    public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                        //Compte dataResponse= response.body();

                        Toast.makeText(SignUpActivity.this, response.code() + "", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onFailure(Call<HashMap> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Error", t.getMessage());

                    }
                });

                if (null == strlastName || strlastName.length() == 0) {
                    // showToast("Enter Your Name");
                    nom.setError("entrer votre nom");
                    isnomValidated = false;
                } else {
                    isnomValidated = true;
                }
                if (null == strfirst || strfirst.length() == 0) {
                    // showToast("Enter Your Password");
                    isPrenomValidated = false;
                    prenom.setError("entrer votre prenom");
                } else {
                    isPrenomValidated = true;
                }

            }


        });


    }

    //get majors from api
    private void buildMajorsdropdown() {

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> tentative = apiInterface.getAllMajors();
        tentative.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                //try{JSONArray major = response.body();
                for (int i = 0; i < response.body().size(); i++) {
                    JsonObject obj = response.body().get(i).getAsJsonObject();
                    forma_list.add((String) obj.get("name").getAsString());
                    Log.d("name major", obj.get("name").toString());

                    Toast.makeText(SignUpActivity.this, obj.get("name").toString(), Toast.LENGTH_LONG).show();
                }
                forma_adapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.formation_spinner_row, forma_list);
                forma_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
                formation.setAdapter(forma_adapter);


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });


    }

}
