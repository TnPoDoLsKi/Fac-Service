package tn.igc.projectone.documentList.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tn.igc.projectone.R;
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;
import tn.igc.projectone.documentList.classes.Document;

public class DocumentList extends Fragment implements SearchView.OnQueryTextListener {
    public DocumentList() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Document List");

        return inflater.inflate(R.layout.document_fragment, container, false);
    }

    RecyclerView recyclerView;

    ArrayList<Document> documents;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewAdapter recyclerViewAdapter2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);


        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        documents = new ArrayList<>();
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme DS", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algebre Cours", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Analyse TD", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Math signal Cours", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme TP", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme Exams", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Transmission TD", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algebre TD", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Analyse Exams", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Transmission COurs", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algebre Exams", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme Cours", "Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index, R.drawable.ic_check_circle_24px, "Algorithme TD", "Masmoudi Ousema"));

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), documents);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main1, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        ArrayList<Document> newList = new ArrayList<>();
        for (Document name : documents) {
            if (name.getDocName().toLowerCase().contains(userInput)) {
                newList.add(name);
            }
        }
        recyclerViewAdapter.updateList(newList);
        return false;
    }
}
