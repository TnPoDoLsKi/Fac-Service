package tn.igc.projectone.upload.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.JsonArray;

import java.io.File;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.Api.APIClient;
import tn.igc.projectone.upload.Api.APIInterface;
import tn.igc.projectone.upload.adapters.MyAdapter;
import tn.igc.projectone.upload.other.FileImage;
import tn.igc.projectone.upload.other.ProgressRequestBody;


public class NewFragment extends Fragment implements ProgressRequestBody.UploadCallbacks {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int conteur_nbre_file_upload=1;


    private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;

    private ArrayList<FileImage> filelist = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MyAdapter myAdapter;

    public NewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewFragment newInstance(String param1, String param2) {
        NewFragment fragment = new NewFragment();
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
        View v = inflater.inflate(R.layout.fragment_new, container, false);

        Button btn_add = v.findViewById(R.id.btn);
        Button btn_valider = v.findViewById(R.id.btn_valider);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new MyAdapter(getContext());
        recyclerView.setAdapter(myAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pix.start(getActivity(), Options.init().setRequestCode(100).setCount(2).setFrontfacing(true));

            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<MultipartBody.Part> multipart = new ArrayList<>();

                for(int i=0;i<filelist.size();i++){
                    multipart.add(filelist.get(i).getPart());
                }
                Log.e("multipartsize", " ->  " + multipart.size());
                APIInterface apiInterface = APIClient.getClientWithToken("Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWUiLCJmaXJzdE5hbWUiOiJDaGFkeSIsImxhc3ROYW1lIjoiTXJhZCIsImVtYWlsIjoiY2hhZHlAZ21haWwuY29tIiwidHlwZSI6ImFkbWluIiwibWFqb3IiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWMiLCJhdmF0YXIiOiIvdXBsb2Fkcy9hdmF0YXIuanBnIiwiaWF0IjoxNTUwOTE5Mjk2LCJleHAiOjE1NTE1MjQwOTZ9.YaR2mQB7NYyj_v6y8BvRIyYvZmssfEzwwvkKs_2cmZw").create(APIInterface.class);
                Call<JsonArray> call_create_task = apiInterface.uploadimage(multipart);
                Toast.makeText(getContext(), multipart.size()+"", Toast.LENGTH_LONG).show();

                call_create_task.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().size();i++) {
                                Log.e("image", " ->  " +response.body().getAsJsonArray().get(i).toString() );

                                Toast.makeText(getContext(), response.body().getAsJsonArray().get(i).toString(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Toast.makeText(getContext(),"non mrighel " + t.toString(), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("connexion");
                        alertDialog.setMessage("Aucune connexion internet");
                        alertDialog.show();



                    }
                });
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                   // myAdapter.addImage(returnValue);
                    for (String s : returnValue) {
                        Log.e("val", " ->  " + s);
                        File file = new File(s);
                        ProgressRequestBody fileBody = new ProgressRequestBody(MediaType.parse("image/*"),file, this);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", ".jpeg", fileBody);
                        FileImage fileImage = new FileImage(s,"",part);
                        filelist.add(fileImage);
                    }
                    myAdapter.addImage(filelist);
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(getActivity(), Options.init().setRequestCode(100).setCount(1));
                } else {
                    Toast.makeText(getActivity(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onProgressUpdate(int percentage, long uploaded, long total) {
        updateProgressView(percentage, uploaded, total);

    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Uploaded Failed!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFinish() {
        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_LONG).show();
        conteur_nbre_file_upload++;
    }

    @Override
    public void uploadStart() {

    }

    private ProgressDialog createProgressDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("télechargement en cours  "+conteur_nbre_file_upload+"/"+filelist.size());
        dialog.setProgress(0);
        dialog.setProgressNumberFormat("");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return dialog;
    }

    private void updateProgressView(int percentage, long uploaded, long total) {
        if (mProgressDialog == null) {
            mProgressDialog = createProgressDialog();
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        mProgressDialog.setProgress(percentage);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
