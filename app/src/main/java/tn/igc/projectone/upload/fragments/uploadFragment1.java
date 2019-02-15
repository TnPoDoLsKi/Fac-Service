package tn.igc.projectone.upload.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.adapters.AdapterFile;
import tn.igc.projectone.upload.other.FileImage;


public class uploadFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;

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
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_upload_fragment1, container, false);
         listView = v.findViewById(R.id.lv4);
        Button btn_upload = v.findViewById(R.id.button4);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pix.start(getActivity(), Options.init().setRequestCode(100).setCount(2).setFrontfacing(true));

            }
        });

/*        FileImage f1 = new FileImage("https://i.imgur.com/tGbaZCY.jpg","smyle","50%");
        FileImage f2 = new FileImage("https://i.imgur.com/tGbaZCY.jpg","smyle","50%");
        FileImage f3 = new FileImage("https://i.imgur.com/tGbaZCY.jpg","smyle","50%");
        FileImage f4 = new FileImage("https://i.imgur.com/tGbaZCY.jpg","smyle","50%");

        ArrayList<FileImage> filelist = new ArrayList<>();
        filelist.add(f1);
        filelist.add(f2);
        filelist.add(f3);
        filelist.add(f4);
        AdapterFile adapterFile = new AdapterFile(getActivity().getApplicationContext(),
                R.layout.item_file,
                R.id.tvname,
                filelist);

        listView.setAdapter(adapterFile);*/
       return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("hhhh", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    ArrayList<FileImage> filelist = new ArrayList<>();
                   // myAdapter.addImage(returnValue);
                    for (String s : returnValue) {
                        Log.e("val", " ->  " + s);
                        FileImage fileImage = new FileImage(s,"","");
                        filelist.add(fileImage);
                    }
                    AdapterFile adapterFile = new AdapterFile(getActivity().getApplicationContext(),
                            R.layout.item_file,
                            R.id.tvname,
                            filelist);

                    listView.setAdapter(adapterFile);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
