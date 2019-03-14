package tn.igc.projectone.Home.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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
import tn.igc.projectone.Home.Adapters.RecyclerViewAdapter;
import tn.igc.projectone.Home.Classes.Matiere;
import tn.igc.projectone.R;

public class Fragment_sem2 extends Fragment {

    View v;
    //Realm mRealm;

    private RecyclerView mRecyclerView;
    private List<Matiere> matiereList ;
    RecyclerViewAdapter recyclerViewAdapter;
    public APIInterface apiInterfaceToken;
    ProgressBar progressBar;

    public Fragment_sem2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sem2_fragment,container,false);
       /* mRecyclerView = (RecyclerView)v.findViewById(R.id.sem2_recyclerView);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarS2);*/



        return v ;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.sem2_recyclerView);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarS2);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiMatieresS2();
    }
    public void onResume() {
        super.onResume();

        apiMatieresS2();

    }

    public void apiMatieresS2(){
        matiereList = new ArrayList<>();
        final String Mid = "5c64773e38e7f64f8d07dc1b";
        //mRealm = Realm.getDefaultInstance();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call_one_maj = apiInterface.getMajor(Mid);
        call_one_maj.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Matiere m;
                //Realm realm = null;
                if(matiereList!=null){
                    matiereList.clear();
                }

                JsonArray subs_array = response.body().getAsJsonArray("subjects");
                for (int i = 0; i < subs_array.size(); i++)

                {
                    JsonObject sub = (JsonObject) subs_array.get(i);
                    JsonElement sub_sem = sub.get("semestre");

                    if (sub_sem.getAsString().equals("2")) {
                        JsonElement sub_name = sub.get("name");
                        JsonElement sub_id = sub.get("_id");
                        String name = sub_name.getAsString();
                        final String id = sub_id.getAsString();

                        m = new Matiere(id, name, R.mipmap.ic_soc);
                        matiereList.add(m);
                        /*realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {


                                try {
                                    RMatiere mat = new RMatiere();
                                    mat.id = id;
                                    mat.nom = name;
                                    mat.img = R.mipmap.ic_soc;
                                    mat.sem=2;
                                    mat.maj_id=Mid;
                                    realm.copyToRealm(mat);
                                    Toast.makeText(getContext(), "" + name + " Added to realm", Toast.LENGTH_SHORT).show();
                                } catch (RealmPrimaryKeyConstraintException e) {
                                    Toast.makeText(getContext(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                    }


                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), matiereList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(),"Offline Use ",Toast.LENGTH_LONG).show();

                /*mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Matiere m ;
                        RealmResults<RMatiere> results = realm.where(RMatiere.class).equalTo(RMatiere.PROPERTY_MAJ_ID,Mid).equalTo(RMatiere.PROPERTY_SEM,2).findAll();

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

 /*  matiereList.add(new Matiere("1", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("2", "Mathématiques Discrétes", R.mipmap.ic_math));
                matiereList.add(new Matiere("3", "Français 1", R.mipmap.ic_iffel));
                matiereList.add(new Matiere("4", "Culture d'Entreprise 1", R.mipmap.ic_soc));
                matiereList.add(new Matiere("5", "Programmation C 1", R.mipmap.ic_code));
                matiereList.add(new Matiere("6", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("7", "Probabilité et Statistiques", R.mipmap.ic_math));
                matiereList.add(new Matiere("8", "Mathématiques Discrétes", R.mipmap.ic_math));*/

}