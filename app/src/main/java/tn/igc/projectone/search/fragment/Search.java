package tn.igc.projectone.search.fragment;



import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;

public class    Search extends Fragment {
    public Search() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        getActivity().setTitle("");
        return inflater.inflate(R.layout.search_fragment, container, false);
    }



    ArrayList<Document> documents;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        documents = new ArrayList<>();
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme DS","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algebre Cours","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Analyse TD","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Math signal Cours","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme TP","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme Exams","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Transmission TD","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algebre TD","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Analyse Exams","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Transmission COurs","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algebre Exams","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme Cours","Masmoudi Ousema"));
        documents.add(new Document(R.drawable.index,R.drawable.ic_check_circle_24px,"Algorithme TD","Masmoudi Ousema"));

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),documents);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        //searchView.setIconifiedByDefault(true);
        //searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setQueryHint("Type something...");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
       searchView.setMaxWidth(1800);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.GRAY);

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.DKGRAY);
                searchText.setHintTextColor(Color.DKGRAY);
            }
        }
    }
}
