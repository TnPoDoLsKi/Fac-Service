package tn.igc.projectone.Home.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.Home.Adapters.DRecyclerViewAdapter;
import tn.igc.projectone.Home.Adapters.RecyclerViewAdapter;
import tn.igc.projectone.Home.Classes.APIinterface;
import tn.igc.projectone.Home.Classes.Client;
import tn.igc.projectone.Home.Classes.Document;
import tn.igc.projectone.Home.Classes.Matiere;
import tn.igc.projectone.Home.Classes.User;
import tn.igc.projectone.R;

public class DocumentList extends Fragment {
    View v;
    //Realm mRealm;
    private RecyclerView mRecyclerView;
    private ArrayList<Document> docList ;
    public APIinterface apiInterface;
    DRecyclerViewAdapter recyclerViewAdapter;


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
        //mRealm = Realm.getDefaultInstance();
        //final String Mid = "5c64773e38e7f64f8d07dc1b";
        Bundle data = getArguments();
        String type = data.getString("type");
        String id_doc = data.getString("mat_id");


        APIinterface apiInterface = Client.getClient().create(APIinterface.class);
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


                        String d_title = doc_title.toString();
                        String d_id = doc_id.toString();
                        Boolean d_ver = doc_ver.getAsBoolean();
                        String d_path = doc_path.getAsString();

                        JsonElement us_fn = doc_user.get("firstName");
                        JsonElement us_ln = doc_user.get("lastName");
                        String u_fn = us_fn.toString();
                        String u_ln = us_ln.toString();

                        User u = new User(u_fn, u_ln, R.drawable.index);


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
}
