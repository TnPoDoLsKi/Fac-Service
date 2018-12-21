package tn.igc.projectone.search;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;

public class    Search extends Fragment {
    public Search() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    SearchView searchView;
    ListView listView;
    ArrayList<String> cours;
    ArrayList<String> td;
    ArrayList<String> exams;
    ArrayList<String> all;
    ArrayList<String> changes;
    /*ArrayAdapter<String > adapter;*/
    SearchAdapter searchAdapter;

    SearchAdapter searchAdapter_td;
    SearchAdapter searchAdapter_exams;
    SearchAdapter searchAdapter_cours;

    CheckBox checkBox_exams;
    CheckBox checkBox_td;
    CheckBox checkBox_cours;


    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            searchView = (SearchView) getView().findViewById(R.id.searchView);
            listView = (ListView) getView().findViewById(R.id.lv1);
            checkBox_cours = (CheckBox) getView().findViewById(R.id.checkbox_cours);
            checkBox_td = (CheckBox) getView().findViewById(R.id.checkBox_td);
            checkBox_exams = (CheckBox) getView().findViewById(R.id.checkbox_exams);


            cours = new ArrayList<>();
            cours.add("cours algeber");
            cours.add("cours analyse");
            cours.add("cours algo");


            td = new ArrayList<>();
            td.add("td algeber");
            td.add("td analyse");
            td.add("td algo");

            exams = new ArrayList<>();
            exams.add("exams algeber");
            exams.add("exams analyse");
            exams.add("exams algo");

            all = new ArrayList<>();
            all.addAll(exams);
            all.addAll(td);
            all.addAll(cours);

            searchAdapter_cours=new SearchAdapter(getContext(),cours);
            searchAdapter_td=new SearchAdapter(getContext(),td);
            searchAdapter_exams=new SearchAdapter(getContext(),exams);

            searchAdapter = new SearchAdapter(getContext(), all);
            listView.setAdapter(searchAdapter);
       /* checkBox_td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(searchAdapter_td);
            }
        });
*/
       checkBox_exams.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (checkBox_exams.isChecked()) {


                        listView.setAdapter(searchAdapter_exams);
                    }

                    else {

                        listView.setAdapter(searchAdapter);
                    }
                }
        });
       checkBox_cours.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox_cours.isChecked()) {


                        listView.setAdapter(searchAdapter_cours);
                    }

                    else {

                        listView.setAdapter(searchAdapter);
                    }
                }
            });
            checkBox_td.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox_td.isChecked()) {


                        listView.setAdapter(searchAdapter_td);
                    }

                    else {

                        listView.setAdapter(searchAdapter);
                    }
                }
            });



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    searchAdapter.getFilter().filter(newText);
                    searchAdapter_cours.getFilter().filter(newText);
                    searchAdapter_exams.getFilter().filter(newText);
                    searchAdapter_td.getFilter().filter(newText);


                    return false;
                }
            });
        }


}

