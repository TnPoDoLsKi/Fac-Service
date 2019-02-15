package tn.igc.projectone.upload.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import tn.igc.projectone.R;
import tn.igc.projectone.upload.adapters.AdapterFile;
import tn.igc.projectone.upload.other.FileImage;


public class uploadFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public uploadFragment2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static uploadFragment2 newInstance(String param1, String param2) {
        uploadFragment2 fragment = new uploadFragment2();
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
        View v = inflater.inflate(R.layout.fragment_upload_fragment2, container, false);

        ListView listView = v.findViewById(R.id.listview1);

        FileImage f1 = new FileImage("https://i.imgur.com/tGbaZCY.jpg","smyle","50%");
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

        listView.setAdapter(adapterFile);

        return v;
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
