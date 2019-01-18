package tn.igc.projectone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tn.igc.projectone.Classes.Matiere;
import tn.igc.projectone.Adapters.RecyclerViewAdapter;
import tn.igc.projectone.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_sem1 extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private List<Matiere> matiereList ;

    public Fragment_sem1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sem1_fragment,container,false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.sem1_recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),matiereList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapter);
        return v ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matiereList = new ArrayList<>();
        matiereList.add(new Matiere("Algorithme et Structure des Données",R.mipmap.ic_code));
        matiereList.add(new Matiere("Mathématiques Discrétes",R.mipmap.ic_math));
        matiereList.add(new Matiere("Français 1",R.mipmap.ic_iffel));
        matiereList.add(new Matiere("Culture d'Entreprise 1",R.mipmap.ic_soc));
        matiereList.add(new Matiere("Programmation C 1",R.mipmap.ic_code));
        matiereList.add(new Matiere("Algorithme et Structure des Données",R.mipmap.ic_code));
        matiereList.add(new Matiere("Probabilité et Statistiques",R.mipmap.ic_math));
        matiereList.add(new Matiere("Mathématiques Discrétes",R.mipmap.ic_math));

    }
}
