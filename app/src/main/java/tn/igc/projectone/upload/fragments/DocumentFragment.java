package tn.igc.projectone.upload.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.Home.Fragments.Matiere_Fragment;
import tn.igc.projectone.R;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.authentification.activities.LoginActivity;

import static java.lang.Integer.parseInt;


public class DocumentFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBERj
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private APIInterface apiInterface;

    private ArrayList<String> pathlist ;
    private String subId,type,session;
    private EditText et_annee,et_desc ;
    private int year;

    public DocumentFragment() {
        // Required empty public constructor
    }

    public static DocumentFragment newInstance(String param1, String param2) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);

         et_annee = (EditText) view.findViewById(R.id.et_anne) ;
         et_desc = (EditText) view.findViewById(R.id.et_desc) ;


        pathlist = getArguments().getStringArrayList("pathlist");
        subId = getArguments().getString("subId");
        type = getArguments().getString("type");
        session = getArguments().getString("session");

        et_annee.setText("");

        et_annee.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showYearDialog();
            }
        });

        Button btn_ajouter = view.findViewById(R.id.btn_ajouter);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = et_annee.getText().toString();
                String desc =  et_desc.getText().toString();
                if(year.equals("")){

                    new IOSDialog.Builder(getContext())
                        .setTitle("Notification")
                        .setMessage("Sélectionnez l'année de votre document.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            public void onClick(DialogInterface dialog, int which) {
                                showYearDialog();
                            }
                        }).show();

                }else {

                    apiInterface = APIClient.getClientWithToken(SaveSharedPreference.getToken(getContext())).create(APIInterface.class);

                    Call<JsonObject> call_create_task = apiInterface.createdocument(type, pathlist, subId, year, desc, session);

                    call_create_task.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.code() == 400) {
                                Toast.makeText(getContext(), "400", Toast.LENGTH_LONG).show();

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
                            if(response.code()==500){
                                new IOSDialog.Builder(getContext())
                                    .setTitle("Ressayer")
                                    .setMessage("")
                                    .setPositiveButton("OK",null).show();

                            }
                            if (response.isSuccessful()) {
                                new IOSDialog.Builder(getContext())
                                    .setTitle("succès")
                                    .setMessage("Votre Document a été ajouté avec succès")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Matiere_Fragment matiere_fragment = new Matiere_Fragment();

                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.container, matiere_fragment);
                                            fragmentTransaction.commit();

                                        }
                                    }).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
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

            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showYearDialog()
    {

        final Dialog d = new Dialog(getContext());
        d.setTitle("Ajouter anneé de document");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        final TextView year_text=(TextView)d.findViewById(R.id.year_text);
        //year = 2000;
        year = Calendar.getInstance().get(Calendar.YEAR);
        int intyear = Integer.parseInt(String.valueOf(year))-10;
        year_text.setHint("");
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(intyear+10);
        nopicker.setMinValue(intyear-10);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(intyear);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                et_annee.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
                year_text.setHint(""+year);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
