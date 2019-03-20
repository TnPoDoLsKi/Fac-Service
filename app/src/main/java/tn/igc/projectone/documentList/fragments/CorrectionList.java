package tn.igc.projectone.documentList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.adapters.CorrectionRecyclerViewAdapter;
import tn.igc.projectone.documentList.adapters.DocumentRecyclerViewAdapterInCorrection;
import tn.igc.projectone.documentList.classes.CorrectionDoc;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.classes.User;

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
        ((MainActivity) getActivity()).setActionBarTitle("Détailles");

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

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.mtr_list);

        cRecyclerView = (RecyclerView) v.findViewById(R.id.corr);

        textView1 =(TextView) v.findViewById(R.id.Textview2);

        textView1.setText("No Corrections");

        textView1.setVisibility(View.INVISIBLE);


        DocList = new ArrayList<Document>();

        CorList = new ArrayList<CorrectionDoc>();

        Bundle bundle = getArguments();

        String _id = bundle.getString("b_id");

        String b_avatar = bundle.getString("b_avatar");

        final boolean b_verifiedByProf = bundle.getBoolean("b_verifiedByProf");

        String b_title = bundle.getString("b_title");

        String b_firstName = bundle.getString("b_firstName");

        String b_lastName= bundle.getString("b_lastName");

        String b_filePath=bundle.getString("b_filePath");

        String b_description=bundle.getString("b_description");

        getActivity().setTitle(b_title);

        final TextView textView= (TextView) v.findViewById(R.id.description);

        textView.setText(b_description);



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

                    if (CorList.size()==0)
                        textView1.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                if(isOnline()==false){
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
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace();  return false;}
        catch (InterruptedException e) { e.printStackTrace();  return false;}


    }


}


