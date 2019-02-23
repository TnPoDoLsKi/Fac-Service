package tn.igc.projectone.upload.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.Api.APIClient;
import tn.igc.projectone.upload.Api.APIInterface;
import tn.igc.projectone.upload.adapters.AdapterFile;
import tn.igc.projectone.upload.other.FileImage;
import tn.igc.projectone.upload.other.ProgressRequestBody;


public class uploadFragment1 extends Fragment implements AdapterView.OnItemClickListener, ProgressRequestBody.UploadCallbacks {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private APIInterface apiInterface;
    private Button btn_valider;
    private Call<JsonArray> call_create_task;
    private ProgressBar progressBarShow;
    private FileImage fileImage;
    private Button btn_upload;
    private int conteur_nbre_file_upload=1;

    private ArrayList<FileImage> filelist = new ArrayList<>();
    private ArrayList<MultipartBody.Part> multipart = new ArrayList<>();

    private String mFileName;
    private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;


    public uploadFragment1() {
        // Required empty public constructor
    }


    public static uploadFragment1 newInstance(String param1, String param2) {
        uploadFragment1 fragment = new uploadFragment1();
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

       View v= inflater.inflate(R.layout.fragment_upload_fragment1, container, false);
          listView = v.findViewById(R.id.lv4);
          progressBarShow = v.findViewById(R.id.progressBarshow);
          btn_upload = v.findViewById(R.id.button4);
          btn_valider = v.findViewById(R.id.button5);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pix.start(getActivity(), Options.init().setRequestCode(100).setCount(2).setFrontfacing(true));

            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiInterface = APIClient.getClientWithToken("Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWUiLCJmaXJzdE5hbWUiOiJDaGFkeSIsImxhc3ROYW1lIjoiTXJhZCIsImVtYWlsIjoiY2hhZHlAZ21haWwuY29tIiwidHlwZSI6ImFkbWluIiwibWFqb3IiOiI1YzY0NzczZTM4ZTdmNjRmOGQwN2RjMWMiLCJhdmF0YXIiOiIvdXBsb2Fkcy9hdmF0YXIuanBnIiwiaWF0IjoxNTUwOTE5Mjk2LCJleHAiOjE1NTE1MjQwOTZ9.YaR2mQB7NYyj_v6y8BvRIyYvZmssfEzwwvkKs_2cmZw").create(APIInterface.class);
                call_create_task = apiInterface.uploadimage(multipart);
                Toast.makeText(getContext(), "hii", Toast.LENGTH_LONG).show();

                call_create_task.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().size();i++) {
                                Log.e("image", " ->  " +response.body().getAsJsonArray().get(i).toString() );

                                Toast.makeText(getContext(), response.body().getAsJsonArray().get(i).toString(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                            /*DocumentFragment documentFragment = new DocumentFragment();
                                        Bundle args = new Bundle();
                                        args.putString("filePath", "YourValue");
                                        documentFragment.setArguments(args);*/
                            //getFragmentManager().beginTransaction().add(R.id.frag, documentFragment).commit();

                                        /*FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frag, documentFragment);
                                        fragmentTransaction.commit();*/


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {

                    progressBarShow.setVisibility(View.VISIBLE);

                    ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

                    //mettre btn valider visible
                    btn_valider.setVisibility(View.VISIBLE);

                   // myAdapter.addImage(returnValue);
                    for (String s : returnValue) {
                        Log.e("val", " ->  " + s);
                        fileImage = new FileImage(s,"","");
                        filelist.add(fileImage);
                        //upload
                        File file = new File(s);
                        //comress file
                        File compressedImage=file;
                        try {
                             compressedImage = new Compressor(getContext())
                                     .setQuality(100)
                                     .setCompressFormat(Bitmap.CompressFormat.PNG)

                                    .compressToFile(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.e("length", " ->  " + file.length());
                        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);

                        ProgressRequestBody fileBody = new ProgressRequestBody(MediaType.parse("image/*"),compressedImage, this);

                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", ".jpeg", fileBody);
                        multipart.add(part);
                        Log.e("part", " ->  " +part.toString() );


                    }
                    AdapterFile adapterFile = new AdapterFile(getActivity().getApplicationContext(),
                            R.layout.item_file,
                            R.id.tvname,
                            filelist);
                    progressBarShow.setVisibility(View.INVISIBLE);
                    listView.setAdapter(adapterFile);
                    listView.setOnItemClickListener(this);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileImage item = filelist.remove(position);
        AdapterFile adapterFile = new AdapterFile(getActivity().getApplicationContext(),
                R.layout.item_file,
                R.id.tvname,
                filelist);
        listView.setAdapter(adapterFile);
        multipart.remove(position);
        if(filelist.size()==0){
            btn_valider.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onProgressUpdate(int percentage, long uploaded, long total) {
       // Toast.makeText(getContext(), percentage + "%", Toast.LENGTH_LONG).show();
        updateProgressView(percentage, uploaded, total);


    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Uploaded Failed!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFinish() {
        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_LONG).show();

        dialog.setMessage("Its loading "+conteur_nbre_file_upload+"/"+multipart.size());

    }

    @Override
    public void uploadStart() {
        Toast.makeText(getContext(), "Uploaded started", Toast.LENGTH_LONG).show();


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //**************************


    private ProgressDialog createProgressDialog() {
       dialog = new ProgressDialog(getContext());
        // dialog.setMax(100);
        //dialog.setTitle("Upload Progress");
        //dialog.setMessage("" + mFileName + "\nis uploading to \nhttp://requestb.in/r2k92yr2");

        dialog.setMessage("Its loading "+conteur_nbre_file_upload+"/"+multipart.size());
        conteur_nbre_file_upload++;
        //dialog.setCancelable(false);

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

       // double mbUploaded = uploaded*1.0f/(1024*1024);
       // double mbLength = total*1.0f/(1024*1024);

        mProgressDialog.setProgress(percentage);
      //  mProgressDialog.setProgressNumberFormat(String.format("%.2f MB / %.2f MB", mbUploaded, mbLength));
    }
}
