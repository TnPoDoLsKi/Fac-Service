package tn.igc.projectone.Home.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.classes.User;

public class DocumentList extends Fragment {
    View v;
    //Realm mRealm;
    private RecyclerView mRecyclerView;
    private ArrayList<Document> docList ;
    public APIInterface apiInterface;


    RecyclerViewAdapter recyclerViewAdapter;
    BottomNavigationView bottomNavigationView;


    TextView abTitle ;
    String tType ;
    String type ;
    String id_doc;
    public DocumentList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.document_fragment,container,false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.docs_recyclerView);
        ((MainActivity) getActivity()).setVisibleProgressBar();

        return v ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);

        //mRealm = Realm.getDefaultInstance();
        //final String Mid = "5c64773e38e7f64f8d07dc1b";
        Bundle data = getArguments();
        type = data.getString("type");
        id_doc = data.getString("mat_id");
        tType = type;
        if (tType=="C")
            tType="Liste des Cours";
        else if (tType=="EX")
            tType="Liste des Examens";
        else if (tType=="DS")
            tType="Liste des DS";
        else if (tType=="TP")
            tType="Liste des TP";
        else
            tType="Liste des TD";

        ((MainActivity) getActivity()).setActionBarTitle(tType);

        apiDocument();





    }

    public void  apiDocument(){
        docList = new ArrayList<>();


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_subject_type = apiInterface.getSubject_type(id_doc,type);
        call_subject_type.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                ((MainActivity) getActivity()).setInvisibleProgressBar();
                if (docList!=null)
                    docList.clear();
                if (response.isSuccessful()) {
                    JsonArray resArr = response.body().getAsJsonArray();

                    for (int i = 0; i < resArr.size(); i++) {
                        JsonObject obj = resArr.get(i).getAsJsonObject();
                        Boolean approved ;
                        if (obj.get("approved")==null) {
                            approved = false;                        }
                        else{
                            approved = obj.get("approved").getAsBoolean();                        }
//document attributs init
                        String type;
                        if (obj.get("type")==null) {
                            type = "";
                        }
                        else{
                            type = obj.get("type").getAsString();
                        }

                        int semestre ;
                        if (obj.get("semestre")==null) {
                            semestre =0;                            }
                        else{
                            semestre = obj.get("semestre").getAsInt();                           }

                        int NBDownloads ;

                        if (obj.get("NBDowloads")==null) {
                            NBDownloads = 0;
                        }
                        else{
                            NBDownloads = obj.get("NBDowloads").getAsInt();                            }


                        Boolean verifiedByProf ;
                        if (obj.get("verifiedByProf")==null) {
                            verifiedByProf = false;
                        }
                        else{
                            verifiedByProf = obj.get("verifiedByProf").getAsBoolean();                            }


                        String session ;
                        if (obj.get("session")==null) {
                            session = "";
                        }
                        else{
                            session = obj.get("session").getAsString();                            }

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
                        String subject ="" ;

                         /*   if (obj.get("subject")==null){
                                 subject = "";
                            }
                            else {
                                 subject = obj.get("subject").getAsString();

                            }*/
                        String majorApi="";
/*                            if (obj.get("major")==null){
                                 majorApi ="";
                            }
                            else{
                                 majorApi = obj.get("major").getAsString();

                            }*/
                        int year = 0;

                        if (obj.get("year")==null){
                            year = 0;
                        }
                        else{
                            year = obj.get("year").getAsInt();

                        }
                        String profName ;

                        if (obj.get("profName")==null){
                            profName = "";
                        }
                        else{
                            profName = obj.get("profName").getAsString();

                        }
                        String description ;


                        if (obj.get("description")==null){
                            description = "";
                        }
                        else{
                            description = obj.get("description").getAsString();

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
                        JsonObject oUser = obj.get("user").getAsJsonObject();

                        String avatar = null;
                        if (oUser.has("avatar"))
                            avatar = oUser.get("avatar").getAsString();

                        String firstName = oUser.get("firstName").getAsString();
                        String lastName = oUser.get("lastName").getAsString();

                        docList.add(new Document(type, semestre, approved, NBDownloads, verifiedByProf, session, _id, title, filePath, new User( firstName, lastName, avatar), majorApi, subject, year, profName, description, createdAt, updatedAt));

                    }






                }


                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), docList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(recyclerViewAdapter);


            }


            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                /*mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Matiere m ;
                        RealmResults<RMatiere> results = realm.where(RMatiere.class).equalTo(RMatiere.PROPERTY_MAJ_ID,Mid).equalTo(RMatiere.PROPERTY_SEM,1).findAll();

                        for (RMatiere mat : results) {
                            m = new Matiere(mat.id,mat.nom,mat.img);
                            matiereList.add(m);
                        }
                    }
                });
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), matiereList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(recyclerViewAdapter);*/




            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(tType);
        apiDocument();

        if (bottomNavigationView.getSelectedItemId()!=R.id.home_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.home_button);
        }
    }
}
