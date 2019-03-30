package tn.igc.projectone.Settings;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.R;
import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.authentification.activities.LoginActivity;
import tn.igc.projectone.filiere.Utils.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    private Spinner spinnerFil;
    private APIInterface apiInterface;

    private ArrayList<Data> majors;
    private APIInterface apiInterfaceToken;
    private EditText fname, lname, password, oldPassword;
    private TextView t, t1, t2, t3;
    private Button saveBtn, retBtn;
    private String oldMajorId;
    private int oldMajorPos;
    private ProgressBar loadIco;
    private String token;
    private String majorId;
    private boolean ver = true;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for signOut btn
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Settings");
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //texts
        t = view.findViewById(R.id.text);
        t1 = view.findViewById(R.id.text1);
        t2 = view.findViewById(R.id.text2);
        t3 = view.findViewById(R.id.text3);
        //majors spinner
        spinnerFil = view.findViewById(R.id.spinner_filier);
        //textFields:
        fname = view.findViewById(R.id.editNom);
        lname = view.findViewById(R.id.editPrenom);
        password = view.findViewById(R.id.editPassword);
        oldPassword = view.findViewById(R.id.editPasswordOld);
        //loading icon
        loadIco = view.findViewById(R.id.settingsProgressBar);
        //button
        saveBtn = view.findViewById(R.id.btnSave);
        retBtn = view.findViewById(R.id.btnReturn);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1=s.toString();
                EditText view1 = (EditText) getActivity().getCurrentFocus();

                if(s1.length()<8 && s1.length()>0){
                    view1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    view1.setError("mot de passe courte");
                    ver = false;

                }
                else{
                    ver = true;
                    view1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lightBlue)));
                }

            }
        };
        password.addTextChangedListener(textWatcher);



        majorsList();

    }

    @Override
    public void onResume() {
        super.onResume();
        token = SaveSharedPreference.getToken(getContext());

        if (token.equals("")) {
            //hiding Layout Parts
            t.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            fname.setVisibility(View.GONE);
            lname.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            oldPassword.setVisibility(View.GONE);
            loadIco.setVisibility(View.GONE);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    majorId = spinnerFil.getSelectedItem().toString();
                    majorId = Data.getIdFromName(majorId, majors);
                    SaveSharedPreference.setMajor(getContext(), majorId);
                }
            });
            //return to login btn
            retBtn.setVisibility(View.VISIBLE);
            retBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveSharedPreference.setToken(getContext(), "");
                    SaveSharedPreference.setMajor(getContext(), "");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });

            majorsList();
        } else {
            majorsList();
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    majorId = spinnerFil.getSelectedItem().toString();
                    majorId = Data.getIdFromName(majorId, majors);
                    updateUserInfo(majorId);
                }
            });
        }
    }

    public void majorsList() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        majors = new ArrayList<>();
        oldMajorId = SaveSharedPreference.getMajor(getContext());

        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();
        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                JsonArray resArr = response.body().getAsJsonArray();


                int size = resArr.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = resArr.get(i).getAsJsonObject();
                    //getting all majors names
                    majors.add(new Data(obj.get("_id").getAsString(), obj.get("name").getAsString()));

                    if (obj.get("_id").getAsString().equals(oldMajorId))
                        oldMajorPos = i;
                }

                final String filArr[] = Data.copyValues(majors);
                ArrayAdapter<String> adapterFil = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, filArr);
                spinnerFil.setAdapter(adapterFil);
                //select the old major
                spinnerFil.setSelection(oldMajorPos);

                //fill the form with current settings
                if (!token.equals(""))
                    getUserInfo();

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.fillInStackTrace());

            }
        });


    }

    public void getUserInfo() {
        //show loading icon
        loadIco.setVisibility(View.VISIBLE);

        apiInterfaceToken = APIClient.getClientWithToken(token).create(APIInterface.class);

        Call<JsonObject> call_user_info = apiInterfaceToken.getUserInfo();
        call_user_info.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() == null) {
                    Log.d("Oops", "User info"+token+" " + response.code());
                    return;
                }
                JsonObject obj = response.body().getAsJsonObject();
                fname.setText(obj.get("firstName").getAsString());
                lname.setText(obj.get("lastName").getAsString());

                oldMajorId = obj.get("major").getAsString();
                String oldMajor = Data.getNameFromId(oldMajorId, majors);

                oldMajorPos = majors.indexOf(Data.getDataFromName(oldMajor, majors));
                spinnerFil.setSelection(oldMajorPos);


                //hide loading icon
                loadIco.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    public void updateUserInfo(final String majorId) {


        if (!verifyData()) {
            new IOSDialog.Builder(getContext())
                .setTitle("Champ vide")
                .setMessage("S'il vous plait remplissez tous les champs")
                .setPositiveButton("OK",null).show();
            return;
        }
        if (password.getText().toString().length()<8 && password.getText().toString().length()>0){
            new IOSDialog.Builder(getContext())
                .setTitle("Courte mot de passe")
                .setMessage("Le mot de passe entré est courte")
                .setPositiveButton("OK",null).show();
            ver=true;
            return;
        }

        apiInterfaceToken = APIClient.getClientWithToken(token).create(APIInterface.class);
        //getting the major id

        Call<Void> call_update_user = apiInterfaceToken.updateUser(
            fname.getText().toString(), lname.getText().toString(), majorId,
            oldPassword.getText().toString(),
            password.getText().toString()
        );
        call_update_user.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 400) {
                    new IOSDialog.Builder(getContext())
                        .setTitle("Notification")
                        .setMessage("le mot de passe entré est incorrect")
                        .setPositiveButton("OK", null).show();
                }
                if (response.code()==401){
                    new IOSDialog.Builder(getContext())
                        .setTitle("Session expiré")
                        .setMessage("S'il vous plait reconnecter")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SaveSharedPreference.setMajor(getContext(),"");
                                Intent i = new Intent(getContext(),LoginActivity.class);
                                startActivity(i);
                            }
                        }).show();



                }
                 if (response.isSuccessful()) {
                     new IOSDialog.Builder(getContext())
                         .setTitle("Modification réussie")
                         .setMessage("Vos données ont été modifiées avec succès")
                         .setPositiveButton("OK", null).show();
                     SaveSharedPreference.setMajor(getContext(), majorId);
                 }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.getMessage());
                if(ClassisOnline.isOnline()==false){
                    new IOSDialog.Builder(getContext())
                        .setTitle("connexion")
                        .setMessage("Aucune connexion internet")
                        .setPositiveButton("OK",null).show();
                }
                else{
                    new IOSDialog.Builder(getContext())
                        .setTitle("Ressayer")
                        .setMessage("")
                        .setPositiveButton("OK",null).show();                            Log.e("errrreur", " ->  " + t.toString());

                }

            }
        });

    }

    private Boolean verifyData() {
        if (fname.getText().toString().trim().equals("")||lname.getText().toString().trim().equals("")||(password.getText().toString().length()!=0&& oldPassword.getText().toString().trim().equals(""))||(password.getText().toString().trim().equals("")&& oldPassword.getText().toString().length()!=0))
        {
            return false;
        }

  /*      if (password.getText().toString().trim().equals(""))
            return false;
        if (oldPassword.getText().toString().trim().equals(""))
            return false;*/
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_bar, menu);
        MenuItem signOutBtn = menu.findItem(R.id.sign_out_btn);
        if (SaveSharedPreference.getToken(getContext()).equals(""))
            signOutBtn.setVisible(false);
        else
            signOutBtn.setVisible(true);

        //Sign out Button
        signOutBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SaveSharedPreference.setMajor(getContext(), "");
                SaveSharedPreference.setToken(getContext(), "");
                onResume();
                item.setVisible(false);
                return false;
            }
        });
    }
}
