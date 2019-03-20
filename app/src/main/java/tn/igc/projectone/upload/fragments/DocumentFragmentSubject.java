package tn.igc.projectone.upload.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.R;
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
        Button btn_ajouter = v.findViewById(R.id.btn_ajouter);
        pathlist = getArguments().getStringArrayList("pathlist");
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList=new ArrayList<>();
                arrayList.add("hi");
                arrayList.add("hii");
                APIInterface apiInterface = APIClient.getClientWithToken("Bearer "+"1402961e1a10d96891b60503992cf39e4b7887c48e5244ba8aafa00f8ecc84da").create(APIInterface.class);
                Call<JsonObject> call_create_task = apiInterface.createcorrection(pathlist,"5c8930956ffe7e798d20b3e2");
                Toast.makeText(getContext(),"hhhhhhhhhhh", Toast.LENGTH_LONG).show();

                call_create_task.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code()==400){
                            Toast.makeText(getContext(),"400", Toast.LENGTH_LONG).show();

                        }
                        if (response.code()==401){
                            Toast.makeText(getContext(), "session expiré", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getContext(), LoginActivity.class);
                            startActivity(i);


                        }
                        if(response.code()==500){
                            Toast.makeText(getContext(),"500", Toast.LENGTH_LONG).show();

                        }

                        if(response.isSuccessful()){
                            Toast.makeText(getContext(),"mrighel", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        if(ClassisOnline.isOnline()==false){
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("connexion");
                            alertDialog.setMessage("Aucune connexion internet");
                            alertDialog.show();
                        }
                        else{
                            Toast.makeText(getContext(),"réessayer " + t.toString(), Toast.LENGTH_LONG).show();

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
