package tn.igc.projectone.Home.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import tn.igc.projectone.Home.Adapters.DRecyclerViewAdapter;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.classes.User;

public class DocumentList extends Fragment {
    View v;
    //Realm mRealm;
    private RecyclerView mRecyclerView;
    private ArrayList<Document> docList ;
    public APIInterface apiInterface;
    DRecyclerViewAdapter recyclerViewAdapter;
    BottomNavigationView bottomNavigationView;


    public DocumentList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.document_fragment,container,false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.docs_recyclerView);


        return v ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docList = new ArrayList<>();
        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);

        //mRealm = Realm.getDefaultInstance();
        //final String Mid = "5c64773e38e7f64f8d07dc1b";

        Bundle data = getArguments();

        String type = data.getString("type");

        String id_doc = data.getString("mat_id");


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_subject_type = apiInterface.getSubject_type(id_doc,type);
        call_subject_type.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Document d;

                JsonArray docs = response.body();

                if (docs!=null)
                {
                    for (int i = 0; i < docs.size(); i++)

                    {
                        JsonObject doc = (JsonObject) docs.get(i);
                        JsonElement doc_title = doc.get("title");
                        JsonElement doc_id = doc.get("_id");
                        JsonElement doc_ver = doc.get("verifiedByProf");
                        JsonObject doc_user = doc.getAsJsonObject("user");
                        JsonElement doc_path = doc.get("filePath");


                        String d_title = doc_title.getAsString();
                        String d_id = doc_id.getAsString();
                        Boolean d_ver = doc_ver.getAsBoolean();
                        String d_path = doc_path.getAsString();

                        JsonElement us_fn = doc_user.get("firstName");
                        JsonElement us_ln = doc_user.get("lastName");
                        String u_fn = us_fn.getAsString();
                        String u_ln = us_ln.getAsString();

                        User u = new User(u_fn, u_ln,"");


                        d = new Document(d_id, d_ver, d_title, u, d_path);
                        docList.add(d);
                    }
                }


                    recyclerViewAdapter = new DRecyclerViewAdapter(getContext(), docList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerViewAdapter);


                }


            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getContext(),"Offline Use ",Toast.LENGTH_LONG).show();

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
        if (bottomNavigationView.getSelectedItemId()!=R.id.home_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.home_button);
        }
    }
}
