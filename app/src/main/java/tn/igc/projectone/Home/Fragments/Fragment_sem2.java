package tn.igc.projectone.Home.Fragments;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.Home.Classes.APIinterface;
import tn.igc.projectone.Home.Classes.Client;
import tn.igc.projectone.Home.Classes.Matiere;
import tn.igc.projectone.Home.Adapters.RecyclerViewAdapter;
import tn.igc.projectone.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_sem2 extends Fragment {

    View v;
    //Realm mRealm;

    private RecyclerView mRecyclerView;
    private List<Matiere> matiereList ;
    RecyclerViewAdapter recyclerViewAdapter;
    public APIinterface apiInterfaceToken;

    public Fragment_sem2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sem2_fragment,container,false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.sem2_recyclerView);


        return v ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matiereList = new ArrayList<>();

        final String Mid = "5c64773e38e7f64f8d07dc1b";
        //mRealm = Realm.getDefaultInstance();

        APIinterface apiInterface = Client.getClient().create(APIinterface.class);
        Call<JsonObject> call_one_maj = apiInterface.getMajor(Mid);
        call_one_maj.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Matiere m;
                //Realm realm = null;

                JsonArray subs_array = response.body().getAsJsonArray("subjects");
                for (int i = 0; i < subs_array.size(); i++)

                {
                    JsonObject sub = (JsonObject) subs_array.get(i);
                    JsonElement sub_sem = sub.get("semestre");

                    if (sub_sem.getAsString().equals("2")) {
                        JsonElement sub_name = sub.get("name");
                        JsonElement sub_id = sub.get("_id");
                        String namee = sub_name.toString();
                        final String id = sub_id.toString();
                        int j = namee.length();
                        final String name = namee.substring(1, j - 1);
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