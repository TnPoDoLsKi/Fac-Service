package tn.igc.projectone.upload.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;


public class DocumentFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBERj
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private APIInterface apiInterface;

    private ArrayList<String> pathlist ;

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
        EditText et_annee = (EditText) view.findViewById(R.id.et_anne) ;
        EditText et_desc = (EditText) view.findViewById(R.id.et_desc) ;

        final String year = et_annee.getText().toString();
        final String desc =  et_desc.getText().toString();


        //Retrieve the filePath
        pathlist = getArguments().getStringArrayList("pathlist");
        Toast.makeText(getContext(),pathlist.get(0), Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(),pathlist.get(1), Toast.LENGTH_LONG).show();


        Button btn_ajouter = view.findViewById(R.id.btn_ajouter);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList=new ArrayList<>();
                arrayList.add("hi");
                arrayList.add("hii");
                apiInterface = APIClient.getClientWithToken("Bearer "+"885188feb75030cefdb87bb6e8af0ee7116d20ad27046db6ef84862f260d0459").create(APIInterface.class);
                Call<JsonObject> call_create_task = apiInterface.createdocument("DS",pathlist,"5c892b3b6ffe7e798d20b3d7",year,desc,"Rattrapage");
                Toast.makeText(getContext(),"hhhhhhhhhhh", Toast.LENGTH_LONG).show();

                call_create_task.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code()==400){
                            Toast.makeText(getContext(),"400", Toast.LENGTH_LONG).show();

                        }
                        if(response.code()==401){
                            Toast.makeText(getContext(),"401", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(),"non mrighel " + t.toString(), Toast.LENGTH_LONG).show();
                        Log.e("failure1", " ->  " + t.toString());




                    }
                });


            }
        });




        return view;
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
        void onFragmentInteraction(Uri uri);
    }
}
