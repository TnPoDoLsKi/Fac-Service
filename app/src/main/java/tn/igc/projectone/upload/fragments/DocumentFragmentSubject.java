package tn.igc.projectone.upload.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.Home.Fragments.Matiere_Fragment;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.authentification.activities.LoginActivity;

public class DocumentFragmentSubject extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> pathlist ;

    private OnFragmentInteractionListener mListener;
    private String IdDoc,b_title,description;
    private EditText desc;

    public DocumentFragmentSubject() {
        // Required empty public constructor
    }

    public static DocumentFragmentSubject newInstance(String param1, String param2) {
        DocumentFragmentSubject fragment = new DocumentFragmentSubject();
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
        View v=inflater.inflate(R.layout.fragment_document_fragment_subject, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Importer correction");

        Button btn_ajouter = v.findViewById(R.id.btn_ajouter);
        TextView subj = v.findViewById(R.id.tv_subj);
        desc = v.findViewById(R.id.et_desc);

        pathlist = getArguments().getStringArrayList("pathlist");
        IdDoc = getArguments().getString("IdDoc");
        b_title = getArguments().getString("b_title");

        subj.setText(b_title);

        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description = desc.getText().toString();

                APIInterface apiInterface = APIClient.getClientWithToken(SaveSharedPreference.getToken(getContext())).create(APIInterface.class);

                Call<JsonObject> call_create_task = apiInterface.createcorrection(pathlist,IdDoc,description);

                call_create_task.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code()==400){
                            Toast.makeText(getContext(),"400", Toast.LENGTH_LONG).show();

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

                        if(response.isSuccessful()){
                            new IOSDialog.Builder(getContext())
                                .setTitle("notification")
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
        });
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
