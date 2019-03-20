package tn.igc.projectone.upload.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;


public class DocumentFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBERj
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private APIInterface apiInterface;

    private ArrayList<String> pathlist;

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
        EditText et_annee = view.findViewById(R.id.et_anne);
        EditText et_desc = view.findViewById(R.id.et_desc);

        String annee = et_annee.getText().toString();
        String desc = et_desc.getText().toString();


        //Retrieve the filePath
        pathlist = getArguments().getStringArrayList("pathlist");
        // Toast.makeText(getContext(),pathlist.get(0).toString(), Toast.LENGTH_LONG).show();


        Button btn_ajouter = view.findViewById(R.id.btn_ajouter);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiInterface = APIClient.getClientWithToken("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWUiLCJmaXJzdE5hbWUiOiJDaGFkeSIsImxhc3ROYW1lIjoiTXJhZCIsImVtYWlsIjoiY2hhZHlAZ21haWwuY29tIiwidHlwZSI6ImFkbWluIiwibWFqb3IiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWMiLCJhdmF0YXIiOiIvdXBsb2Fkcy9hdmF0YXIuanBnIiwiaWF0IjoxNTUwMTg5MTU3LCJleHAiOjE1NTA3OTM5NTd9.JDrrQzILE8zs1EB-b0byxYaxO2G7odXcj3_LdCOpcRo").create(APIInterface.class);
                Call<JsonObject> call_create_task = apiInterface.createdocument("ds analyse 2018", "DS", "/uploads/jdhgfhd.jpg", "5c2426542a7e2f361896f812", "5c41df5e0000d416fc5158fd", "5c41b2d82383c111b4ffad1a", "2017", "1", "profX", "Controle", null);
                Toast.makeText(getContext(), "hhhhhhhhhhh", Toast.LENGTH_LONG).show();

                call_create_task.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "mrighel", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "non mrighel " + t.toString(), Toast.LENGTH_LONG).show();
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
