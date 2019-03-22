package tn.igc.projectone.Home.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
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
import tn.igc.projectone.SaveSharedPreference;

public class Fragment_sem2 extends Fragment {

    View v;
    //Realm mRealm;
    String Mid;
    ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private List<Matiere> matiereList ;
    RecyclerViewAdapter recyclerViewAdapter;
    public APIInterface apiInterfaceToken;
    private TextView textView1;

    public Fragment_sem2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sem2_fragment,container,false);

        return v ;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarS2);
        progressBar.setVisibility(View.VISIBLE);
        textView1 =(TextView) v.findViewById(R.id.InfoTextview);

        textView1.setText("No Subjects");

        textView1.setVisibility(View.INVISIBLE);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.sem2_recyclerView);



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mid= SaveSharedPreference.getMajor(getContext());

        apiMatieresS2();
    }
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        apiMatieresS2();

    }

    public void apiMatieresS2(){
        matiereList = new ArrayList<>();
        //mRealm = Realm.getDefaultInstance();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_one_maj = apiInterface.getMajorSujects(Mid);
        call_one_maj.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

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

                        if (sub_sem.getAsString().equals("2")) {
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




                    }
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), matiereList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerViewAdapter);
                    if (subs_array.size()==0)
                        textView1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                if(isOnline()==false){
                    textView1.setText("Aucune connexion trouvée");
                    textView1.setVisibility(View.VISIBLE);

                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), matiereList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerViewAdapter);
                }
                else
                    textView1.setText("Aucune matière trouvée");

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

 /*  matiereList.add(new Matiere("1", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("2", "Mathématiques Discrétes", R.mipmap.ic_math));
                matiereList.add(new Matiere("3", "Français 1", R.mipmap.ic_iffel));
                matiereList.add(new Matiere("4", "Culture d'Entreprise 1", R.mipmap.ic_soc));
                matiereList.add(new Matiere("5", "Programmation C 1", R.mipmap.ic_code));
                matiereList.add(new Matiere("6", "Algorithme et Structure des Données", R.mipmap.ic_code));
                matiereList.add(new Matiere("7", "Probabilité et Statistiques", R.mipmap.ic_math));
                matiereList.add(new Matiere("8", "Mathématiques Discrétes", R.mipmap.ic_math));*/

}