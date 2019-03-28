package tn.igc.projectone.documentList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.adapters.CorrectionRecyclerViewAdapter;
import tn.igc.projectone.documentList.adapters.DocumentRecyclerViewAdapterInCorrection;
import tn.igc.projectone.documentList.classes.CorrectionDoc;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.classes.User;
import tn.igc.projectone.upload.fragments.NewFragment;
import tn.igc.projectone.upload.fragments.UploadFragmentSubject;

public class CorrectionList extends Fragment {

    ProgressBar progressBar;
    BottomNavigationView bottomNavigationView;

    View v;

    private RecyclerView mRecyclerView;

    private RecyclerView cRecyclerView;

    private ArrayList<Document> DocList;

    private ArrayList<CorrectionDoc> CorList;

    public APIInterface apiInterface;

    DocumentRecyclerViewAdapterInCorrection recyclerViewAdapter;

    CorrectionRecyclerViewAdapter cRecyclerViewAdapter;

    TextView textView1;
    private Button btn_uploadCorrection;
    private TextView tv_description;
    private String letter;
    private String b_description;


    public CorrectionList() {
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);


    }
    @Override
    public void onResume() {
        super.onResume();
      /*  if (bottomNavigationView.getSelectedItemId()!=R.id.search_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.search_button);
        }*/

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Correction List");

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        v = inflater.inflate(R.layout.correction_fragment, container, false);

        btn_uploadCorrection =(Button) v.findViewById(R.id.Buttonuploadcorrection);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.mtr_list);

        cRecyclerView = (RecyclerView) v.findViewById(R.id.corr);

        textView1 =(TextView) v.findViewById(R.id.Textview2);
        tv_description =(TextView) v.findViewById(R.id.textView12);
        TextView textView= (TextView) v.findViewById(R.id.description);
        TextView tv_correction= (TextView) v.findViewById(R.id.textView9);
        TextView tv_uploadcorrection= (TextView) v.findViewById(R.id.Textuploadcorrection);
        ImageView iconeupload = (ImageView) v.findViewById(R.id.iconupload);


        textView1.setText("Aucune correction");

        textView1.setVisibility(View.INVISIBLE);


        DocList = new ArrayList<Document>();

        CorList = new ArrayList<CorrectionDoc>();

        Bundle bundle = getArguments();

        final String _id = bundle.getString("b_id");

        String b_avatar = bundle.getString("b_avatar");

        final boolean b_verifiedByProf = bundle.getBoolean("b_verifiedByProf");

        final String b_title = bundle.getString("b_title");

        String b_firstName = bundle.getString("b_firstName");

        String b_lastName= bundle.getString("b_lastName");

        String b_filePath=bundle.getString("b_filePath");

        b_description=bundle.getString("b_description");

        getActivity().setTitle(b_title);



        textView.setText(b_description);
        ((MainActivity) getActivity()).setActionBarTitle("Document");

        //deseable description if description ""
        if ((b_description.equals(""))){
            tv_description.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        //deseable if ...
        letter = Character.toString(b_title.charAt(0));
        if(letter.equals("C")){
            btn_uploadCorrection.setVisibility(View.INVISIBLE);
            tv_correction.setVisibility(View.INVISIBLE);
            tv_uploadcorrection.setVisibility(View.INVISIBLE);
            iconeupload.setVisibility(View.INVISIBLE);
            cRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            textView1.setVisibility(View.INVISIBLE);
        }

        btn_uploadCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFragmentSubject uploadFragmentSubject = UploadFragmentSubject.newInstance(_id,b_title);
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, uploadFragmentSubject).commit();


            }
        });

        Document doc = new Document(_id,b_verifiedByProf,b_title,new User(b_firstName,b_lastName,b_avatar),b_filePath);

        DocList.add(doc);


        recyclerViewAdapter = new DocumentRecyclerViewAdapterInCorrection(getContext(), DocList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(recyclerViewAdapter);





       APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_one_corr = apiInterface.getOneCorr(_id);
        call_one_corr.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    JsonArray corrs_array = response.body().getAsJsonArray();

               for (int i = 0; i < corrs_array.size(); i++)

                {
                    JsonObject obj =  corrs_array.get(i).getAsJsonObject();
                    JsonObject oUser= obj.get("user").getAsJsonObject();
                    //Correction init
                    Boolean approved ;
                    if (obj.get("approved")==null) {
                        approved = false;                        }
                    else{
                        approved = obj.get("approved").getAsBoolean();                        }
//document attributs init
                    Boolean verifiedByProf ;
                    if (obj.get("verifiedByProf")==null) {
                        verifiedByProf = false;
                    }
                    else{
                        verifiedByProf = obj.get("verifiedByProf").getAsBoolean();                            }
                    int score=0;
                    String _id ;
                    if (obj.get("_id")==null) {
                        _id = "";                            }
                    else{
                        _id = obj.get("_id").getAsString();
                    }
                    String title ;
                    if (obj.get("title")==null) {
                        title = "";
                    }
                    else{
                        title = obj.get("title").getAsString();
                    }
                    String filePath ;

                    if (obj.get("filePath")==null){
                        filePath = "";
                    }
                    else {
                        filePath = obj.get("filePath").getAsString();
                    }
                    String document ;

                    if (obj.get("document")==null){
                         document ="";
                    }
                    else{
                        document =obj.get("document").getAsString();
                    }

                    String createdAt;

                    if (obj.get("createdAt")==null){
                        createdAt="";
                    }
                    else{
                        createdAt = obj.get("createdAt").getAsString();

                    }
                    String updatedAt;
                    if (obj.get("updatedAt")==null){
                        updatedAt = "";
                    }
                    else{
                        updatedAt = obj.get("updatedAt").getAsString();

                    }
                    //user init
                    String avatar = null;
                    if (oUser.has("avatar"))
                        avatar = oUser.get("avatar").getAsString();

                    String firstName=oUser.get("firstName").getAsString();
                    String lastName=oUser.get("lastName").getAsString();



                        CorList.add(new CorrectionDoc(approved,verifiedByProf,score,_id,title,filePath,new User(firstName,lastName,avatar),document,createdAt,updatedAt));




}


                    cRecyclerViewAdapter = new CorrectionRecyclerViewAdapter(getContext(), CorList);

                    cRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    cRecyclerView.setAdapter(cRecyclerViewAdapter);

                    if (CorList.size()==0) {
                        if (letter.equals("C")) {
                            textView1.setVisibility(View.INVISIBLE);
                        } else {
                            textView1.setVisibility(View.VISIBLE);
                        }
                    }

                }


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                if(ClassisOnline.isOnline()==false){
                    textView1.setText("Aucune connexion trouvée");
                    textView1.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    cRecyclerViewAdapter = new CorrectionRecyclerViewAdapter(getContext(), CorList);

                    cRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    cRecyclerView.setAdapter(cRecyclerViewAdapter);
                }
                else
                    textView1.setText("Aucune correction trouvée");
            }
        });



        return v;
    }



}


