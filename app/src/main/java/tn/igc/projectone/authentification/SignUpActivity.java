package tn.igc.projectone.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import tn.igc.projectone.R;

public class SignUpActivity extends AppCompatActivity {
    Spinner formation,filiere;
    Button cnxlogin,cnxsignup;
    EditText nom,prenom;
    Boolean isnomValidated,isPrenomValidated;
    List forma_list,filiereing_list,filiereprep_list,filierelmd_list;
    ArrayAdapter forma_adapter,filiereing_adapter,filiereprep_adapter,filierelmd_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nom=(EditText)findViewById(R.id.editText3);
        prenom=(EditText)findViewById(R.id.editText4);
        cnxlogin=(Button)findViewById(R.id.button5);
        cnxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        cnxsignup=(Button)findViewById(R.id.CNX);
        cnxsignup.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        String strlastName = nom.getText().toString().trim();
                        String strfirst= prenom.getText().toString().trim();
                        if (null == strlastName || strlastName.length() == 0  )
                        {
                            // showToast("Enter Your Name");
                            nom.setError( "entrer votre nom" );
                            isnomValidated = false;
                        }
                        else {
                            isnomValidated=true;
                        }
                        if (null == strfirst || strfirst.length() == 0)
                        {
                            // showToast("Enter Your Password");
                            isPrenomValidated = false;
                            prenom.setError( "entrer votre prenom" );
                        }
                        else {
                            isPrenomValidated=true;
                        }

                    }



        });
        formation=(Spinner) findViewById(R.id.spinner);
        filiere=(Spinner)findViewById(R.id.spinner2);
        forma_list=new ArrayList();
        filiereing_list=new ArrayList();
        filierelmd_list=new ArrayList();
        filiereprep_list=new ArrayList();
        forma_list.add("formation");
        forma_list.add("Licence");
        forma_list.add("Ingénieur");
        forma_list.add("Préparatoire");

        filiereing_list.add("Filiére");
        filiereing_list.add("FIA1");
        filiereing_list.add("FIA2-GL");
        filiereing_list.add("FIA2-II");
        filiereing_list.add("FIA3-GL-AL");
        filiereing_list.add("FIA3-II");
        filiereing_adapter=new ArrayAdapter(this,R.layout.formation_spinner_row,filiereing_list);
        filiereing_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);

        filierelmd_list.add("LAII-A1");
        filierelmd_list.add("LAII-A2");
        filierelmd_list.add("LAII-A3");
        filierelmd_list.add("LFSI-A1");
        filierelmd_list.add("LFSI-A2");
        filierelmd_list.add("LFSI-A3");
        filierelmd_adapter=new ArrayAdapter(this,R.layout.formation_spinner_row,filierelmd_list);
        filierelmd_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);

        filiereprep_list.add("PREP-A1");
        filiereprep_list.add("PREP-A2");
        filiereprep_adapter=new ArrayAdapter(this,R.layout.formation_spinner_row,filiereprep_list);
        filiereprep_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);


        forma_adapter=new ArrayAdapter(this,R.layout.formation_spinner_row,forma_list);
        forma_adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        formation.setAdapter(forma_adapter);
        formation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    filiere.setAdapter(filierelmd_adapter);
                    filiere.setVisibility(View.VISIBLE);
                } else if (position == 2){
                    filiere.setVisibility(View.VISIBLE);
                    filiere.setAdapter(filiereing_adapter);
                }
                else if(position== 3){
                    filiere.setAdapter(filiereprep_adapter);
                    filiere.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filiere.setVisibility(View.GONE);

            }
        });








    }
}
