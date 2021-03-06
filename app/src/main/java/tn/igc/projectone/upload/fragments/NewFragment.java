package tn.igc.projectone.upload.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.JsonArray;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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


public class NewFragment extends Fragment implements ProgressRequestBody.UploadCallbacks {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private int conteur_nbre_file_upload=1;

    private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;

    private ArrayList<FileImage> filelist = new ArrayList<>();
    private ArrayList<String> pathlist = new ArrayList<>();



    private String mParam1;
    private String mParam2;
    private String mParam3;

    private OnFragmentInteractionListener mListener;
    private MyAdapter myAdapter;
    private Button btn_valider;
    private TextView tv_aucuneImage;
    private LinearLayout layout;

    public NewFragment() {
        // Required empty public constructor
    }

    public static NewFragment newInstance(String param1, String param2, String param3) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new, container, false);

        ImageView btn_add = v.findViewById(R.id.btn);
        btn_valider = v.findViewById(R.id.btn_valider);
        tv_aucuneImage = v.findViewById(R.id.tv_choisirImage);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);


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
                APIInterface apiInterface = APIClient.getClientWithToken(SaveSharedPreference.getToken(getContext())).create(APIInterface.class);

                Call<JsonArray> call_create_task = apiInterface.uploadimage(multipart);

                call_create_task.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
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
                            for(int i=0;i<response.body().size();i++) {
                                pathlist.add(response.body().getAsJsonArray().get(i).getAsString());
                            }
                            if (mParam2.equals("Examen"))
                                mParam2="EX";
                            if (mParam2.equals("Cours"))
                                mParam2="C";

                            dialog.dismiss();

                            DocumentFragment documentFragment = new DocumentFragment();

                            Bundle args = new Bundle();
                            args.putStringArrayList("pathlist",pathlist);
                            args.putString("subId",mParam1);
                            args.putString("type",mParam2);
                            args.putString("session",mParam3);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                   // myAdapter.addImage(returnValue);
                    for (String s : returnValue) {
                        File file = new File(s);
                        //***************
                        Bitmap d = new BitmapDrawable(getResources(), file.getAbsolutePath()).getBitmap();
                        OutputStream imagefile = null;
                        try {
                            imagefile = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        d.compress(Bitmap.CompressFormat.JPEG, 80, imagefile);

                        //***************
                        ProgressRequestBody fileBody = new ProgressRequestBody(MediaType.parse("image/*"),file, this);
                        MultipartBody.Part part = MultipartBody.Part.createFormData(" ", file.getName(), fileBody);
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
        Toast.makeText(getContext(), "Échec du téléchargement!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFinish() {
        conteur_nbre_file_upload++;
        if(conteur_nbre_file_upload<=filelist.size()) {
            dialog.setMessage("télechargement en cours.. " + conteur_nbre_file_upload + "/" + filelist.size());
        }else{
            conteur_nbre_file_upload--;
            dialog.setMessage("télechargement en cours.. " + conteur_nbre_file_upload + "/" + filelist.size());
        }

    }

    @Override
    public void uploadStart() {

    }

    private ProgressDialog createProgressDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("télechargement en cours.. "+conteur_nbre_file_upload+"/"+filelist.size());
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
    @Override
    public void onResume(){
        super.onResume();


    }
}
