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
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.authentification.util.SaveSharedPreference;

public class Fragment_sem1 extends Fragment {

    View v;
    String Mid;
    //Realm mRealm;
    private RecyclerView mRecyclerView;
    private List<Matiere> matiereList ;
    public APIInterface apiInterface;
    ProgressBar progressBar;
    RecyclerViewAdapter recyclerViewAdapter;


    public Fragment_sem1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sem1_fragment,container,false);
       /* mRecyclerView = (RecyclerView)v.findViewById(R.id.sem1_recyclerView);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarS1);
        progressBar.setVisibility(View.VISIBLE);*/


        return v ;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.sem1_recyclerView);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarS1);
        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mid= "5c8922066b5a61762e227a99";
                //SaveSharedPreference.getMajor(getContext());
        apiMatieresS1();

    }

    @Override
    public void onResume() {
        super.onResume();

        apiMatieresS1();
    }

    public void apiMatieresS1(){
        matiereList = new ArrayList<>();
        //mRealm = Realm.getDefaultInstance();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_one_maj = apiInterface.getMajorSujects(Mid);
        call_one_maj.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                ((MainActivity) getActivity()).setInvisibleProgressBar();

                Matiere m;
                //Realm realm = null;
                if(matiereList!=null){
                    matiereList.clear();
                }

                JsonArray subs_array = response.body().getAsJsonArray();
                for (int i = 0; i < subs_array.size(); i++)

                {
                    JsonObject sub = (JsonObject) subs_array.get(i);
                    JsonElement sub_sem = sub.get("semestre");

                    if (sub_sem.getAsString().equals("1")) {
                        JsonElement sub_name = sub.get("name");
                        JsonElement sub_id = sub.get("_id");
                        String name = sub_name.getAsString();
                        JsonObject doc_count = sub.getAsJsonObject("documentsCount");

                        int ds_c = doc_count.get("DS").getAsInt();
                        int ex_c =  doc_count.get("EX").getAsInt();
                        int tp_c = doc_count.get("C").getAsInt();
                        int c_c = doc_count.get("TD").getAsInt();
                        int td_c = doc_count.get("TP").getAsInt();
                        final String id = sub_id.getAsString();

                        m = new Matiere(id, name, R.mipmap.ic_soc,c_c,td_c,ds_c,ex_c,tp_c);
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
                                    mat.sem=1;
                                    mat.maj_id=Mid;
                                    realm.copyToRealm(mat);
                                    Toast.makeText(getContext(), ""+name+" Added to realm", Toast.LENGTH_SHORT).show();
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

    /*  matiereList.add(new Matiere("1", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("2", "Mathématiques Discrétes", R.mipmap.ic_math));
                matiereList.add(new Matiere("3", "Français 1", R.mipmap.ic_iffel));
                matiereList.add(new Matiere("4", "Culture d'Entreprise 1", R.mipmap.ic_soc));
                matiereList.add(new Matiere("5", "Programmation C 1", R.mipmap.ic_code));
                matiereList.add(new Matiere("6", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("7", "Probabilité et Statistiques", R.mipmap.ic_math));
                matiereList.add(new Matiere("8", "Mathématiques Discrétes", R.mipmap.ic_math));*/

 }
