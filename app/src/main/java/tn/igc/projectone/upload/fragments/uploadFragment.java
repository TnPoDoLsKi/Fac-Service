package tn.igc.projectone.upload.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tn.igc.projectone.R;
import tn.igc.projectone.upload.adapters.AdapterFile;
import tn.igc.projectone.upload.other.File;


public class uploadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView namedoc;
    private Button btnajouter;
    private Button btnvalider;
    private View listView;

    public uploadFragment() {
        // Required empty public constructor
    }


    public static uploadFragment newInstance(String param1, String param2) {
        uploadFragment fragment = new uploadFragment();
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
        View v = inflater.inflate(R.layout.fragment_upload, container, false);



        namedoc =(TextView) v.findViewById(R.id.tvnamedoc);
        btnajouter =(Button)v.findViewById(R.id.buttonajouter);
        btnvalider =(Button)v.findViewById(R.id.buttonvalider);
       listView =(ListView) v.findViewById(R.id.listview);
       // View listView = getActivity().findViewById(R.id.listview);
        File f1 = new File("https://www.google.com/search?biw=1366&bih=657&tbm=isch&sa=1&ei=CCFDXIPlN42LlwS-r5TICg&q=icon+&oq=icon+&gs_l=img.3..35i39l2j0i67l2j0j0i67l2j0j0i67j0.1588.1588..2234...0.0..0.146.146.0j1......1....1..gws-wiz-img.zLq3dBWGohU#imgrc=GExnjOqs_opq5M:","smyle","50%");
        File f2 = new File("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwirtZHm6vjfAhWE1eAKHVqTAwQQjRx6BAgBEAU&url=https%3A%2F%2Fwww.flickr.com%2Fphotos%2Fozelui%2F29343251&psig=AOvVaw3nP-mifT1g4UgjPffzQV73&ust=1547952549629259","smyle","50%");
        ArrayList<File> filelist = new ArrayList<>();
        filelist.add(f1);
        filelist.add(f2);
        AdapterFile adapterFile = new AdapterFile(getActivity().getApplicationContext(),
                R.layout.item_file,
                R.id.tvname,
                filelist);



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
