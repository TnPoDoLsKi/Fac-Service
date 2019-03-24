package tn.igc.projectone.Settings;


import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    private EditText email, fname, lname, password, oldPassword;
    private TextView t, t1, t2, t3;
    private Button saveBtn, retBtn;
    private String oldMajorId;
    private int oldMajorPos;
    private ProgressBar loadIco;
    private String token;
    private String majorId;
    private String majorName;
    BottomNavigationView bottomNavigationView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for signOut btn
        setHasOptionsMenu(true);
        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);


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
        email = view.findViewById(R.id.editEmail);
        password = view.findViewById(R.id.editPassword);
        oldPassword = view.findViewById(R.id.editPasswordOld);
        //loading icon
        loadIco = view.findViewById(R.id.settingsProgressBar);
        //button
        saveBtn = view.findViewById(R.id.btnSave);
        retBtn = view.findViewById(R.id.btnReturn);
        majorsList();

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Settings");
        if (bottomNavigationView.getSelectedItemId()!=R.id.parametre_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.parametre_button);
        }
        token = SaveSharedPreference.getToken(getContext());
//        majorId = Data.getIdFromName(spinnerFil.getSelectedItem().toString(),majors);

        if (token.equals("")) {
            //hiding Layout Parts
            t.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            fname.setVisibility(View.GONE);
            lname.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            oldPassword.setVisibility(View.GONE);
            loadIco.setVisibility(View.GONE);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    majorName = spinnerFil.getSelectedItem().toString();
                    majorId = Data.getIdFromName(majorName, majors);

                    SaveSharedPreference.setMajor(getContext(), majorId);
                    SaveSharedPreference.setMajorName(getContext(), majorName);
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
                    majorName = spinnerFil.getSelectedItem().toString();
                    majorId = Data.getIdFromName(majorName, majors);
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
                    Log.d("userInfo", "onResponse: " + response.code());
                    return;
                }
                JsonObject obj = response.body().getAsJsonObject();
                fname.setText(obj.get("firstName").getAsString());
                lname.setText(obj.get("lastName").getAsString());
                email.setText(obj.get("email").getAsString());

                oldMajorId = obj.get("major").getAsString();
                String oldMajor = Data.getNameFromId(oldMajorId, majors);

                oldMajorPos = majors.indexOf(Data.getDataFromName(oldMajor, majors));
                spinnerFil.setSelection(oldMajorPos);


                //hide loading icon
                loadIco.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                try {
                    Toast.makeText(getContext(), "No Connection", Toast.LENGTH_LONG).show();
                } catch (Exception ignored) {

                }
            }
        });

    }

    public void updateUserInfo(final String majorId) {
        apiInterfaceToken = APIClient.getClientWithToken(token).create(APIInterface.class);
        //getting the major id

        Call<Void> call_update_user = apiInterfaceToken.updateUser(
            fname.getText().toString(), lname.getText().toString(), email.getText().toString(), majorId,
            oldPassword.getText().toString(),
            password.getText().toString()
        );
        call_update_user.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 400) {
                    Toast.makeText(getContext(), "Pas autorisé" + response.body(), Toast.LENGTH_SHORT).show();
                    return;
                } else if (response.code() == 200)
                    Toast.makeText(getContext(), "Succès", Toast.LENGTH_LONG).show();

                SaveSharedPreference.setMajor(getContext(), majorId);
                SaveSharedPreference.setMajorName(getContext(), majorName);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Oops", "onFailure: " + t.getMessage());

            }
        });

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
                Toast.makeText(getContext(), "Déconnecté", Toast.LENGTH_LONG).show();
                onResume();
                item.setVisible(false);
                return false;
            }
        });
    }
}
