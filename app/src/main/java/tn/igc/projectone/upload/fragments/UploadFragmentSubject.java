package tn.igc.projectone.upload.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.R;

import tn.igc.projectone.SaveSharedPreference;
import tn.igc.projectone.authentification.activities.LoginActivity;
import tn.igc.projectone.upload.Interface.RecyclerViewClickListener;
import tn.igc.projectone.upload.adapters.MyAdapter;
import tn.igc.projectone.upload.other.FileImage;
import tn.igc.projectone.upload.other.ProgressRequestBody;


public class UploadFragmentSubject extends Fragment implements ProgressRequestBody.UploadCallbacks {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static int conteur_nbre_file_upload=1;


    private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;

    private ArrayList<FileImage> filelist = new ArrayList<>();
    private ArrayList<String> pathlist = new ArrayList<>();



    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private MyAdapter myAdapter;
    private Button btn_valider;
    private TextView tv_aucuneImage;
    private LinearLayout layout;

    public UploadFragmentSubject() {
        // Required empty public constructor
    }


    public static UploadFragmentSubject newInstance(String param1, String param2) {
        UploadFragmentSubject fragment = new UploadFragmentSubject();
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
        View v = inflater.inflate(R.layout.fragment_upload_fragment_subject, container, false);

        Button btn_add = v.findViewById(R.id.btn);
        btn_valider = v.findViewById(R.id.btn_valider);
        tv_aucuneImage = v.findViewById(R.id.tv_choisirImage);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        TextView nomMatiere = v.findViewById(R.id.tv_nomMatiere);

        nomMatiere.setText(mParam2);
        Toast.makeText(getActivity(), "params"+ mParam1+" "+mParam2, Toast.LENGTH_LONG).show();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                filelist.remove(position);
                myAdapter.addImage(filelist);
                if(filelist.size()==0){
                    btn_valider.setVisibility(View.INVISIBLE);
                    tv_aucuneImage.setVisibility(View.VISIBLE);}
            }
        };

        myAdapter = new MyAdapter(getContext(),listener);
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
                APIInterface apiInterface = APIClient.getClientWithToken("Bearer "+SaveSharedPreference.getToken(getContext())).create(APIInterface.class);
                Call<JsonArray> call_create_task = apiInterface.uploadimage(multipart);

                call_create_task.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if(response.code()==400){
                            Toast.makeText(getContext(),"400", Toast.LENGTH_LONG).show();

                        }
                        if (response.code()==401){
                            Toast.makeText(getContext(), "session expiré", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getContext(),LoginActivity.class);
                            startActivity(i);


                        }
                        if(response.code()==500){
                            Toast.makeText(getContext(),"500", Toast.LENGTH_LONG).show();

                        }
                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().size();i++) {
                                pathlist.add(response.body().getAsJsonArray().get(i).getAsString());
                            }
                            dialog.dismiss();
                            DocumentFragment documentFragment = new DocumentFragment();
                            Bundle args = new Bundle();
                            args.putStringArrayList("pathlist",pathlist);
                            args.putString("IdDoc",mParam1);
                            documentFragment.setArguments(args);

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, documentFragment);
                            fragmentTransaction.commit();

                        }


                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        if(ClassisOnline.isOnline()==false){
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("connexion");
                            alertDialog.setMessage("Aucune connexion internet");
                            alertDialog.show();
                        }
                        else{
                            Toast.makeText(getContext(),"réessayer " + t.toString(), Toast.LENGTH_LONG).show();
                            Log.e("errrreur", " ->  " + t.toString());

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
                        Log.e("file name", " ->  " + file.getName());
                        Log.e("file to string", " ->  " + file.toString());
                        ProgressRequestBody fileBody = new ProgressRequestBody(MediaType.parse("image/*"),file, this);
                        MultipartBody.Part part = MultipartBody.Part.createFormData(file.getName(), file.getName(), fileBody);
                        Log.e("part to string", " ->  " + part.toString());
                        FileImage fileImage = new FileImage(s,"",part);
                        filelist.add(fileImage);
                    }
                    myAdapter.addImage(filelist);
                    if(filelist.size()!=0)
                    {btn_valider.setVisibility(View.VISIBLE);
                        tv_aucuneImage.setVisibility(View.INVISIBLE);}


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
                    Toast.makeText(getActivity(), "Approuver les autorisations pour ouvrir", Toast.LENGTH_LONG).show();
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
        conteur_nbre_file_upload++;
        if(conteur_nbre_file_upload<=filelist.size()) {
            dialog.setMessage("télechargement en cours  " + conteur_nbre_file_upload + "/" + filelist.size());
        }else{
            conteur_nbre_file_upload--;
            dialog.setMessage("télechargement en cours  " + conteur_nbre_file_upload + "/" + filelist.size());
        }

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
        void onFragmentInteraction(Uri uri);
    }
}
