package tn.igc.projectone.upload.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tn.igc.projectone.R;
import tn.igc.projectone.upload.adapters.AdapterFile;
import tn.igc.projectone.upload.other.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link uploadFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link uploadFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class uploadFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public uploadFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadFragment1.
     */
    // TODO: Rename and change types and number of parameters
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
        ListView listView = v.findViewById(R.id.listview1);

        File f1 = new File("http://i.imgur.com/DvpvklR.png","smyle","50%");
        File f2 = new File("http://i.imgur.com/DvpvklR.png","smyle","50%");
        File f3 = new File("http://i.imgur.com/DvpvklR.png","smyle","50%");
        File f4 = new File("http://i.imgur.com/DvpvklR.png","smyle","50%");

        ArrayList<File> filelist = new ArrayList<>();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
