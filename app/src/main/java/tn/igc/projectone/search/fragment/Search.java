package tn.igc.projectone.search.fragment;



import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.APIClient;
import tn.igc.projectone.APIInterface;
import tn.igc.projectone.AddFragment;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.CorrectionDoc;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;
import tn.igc.projectone.documentList.classes.User;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static java.nio.file.Paths.get;

public class    Search extends Fragment implements SearchView.OnQueryTextListener, View.OnTouchListener {
    ArrayList<Document> documents;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar progressBar;
    public APIInterface apiInterface;
    Realm realm;
    RealmConfiguration configuration;
    RecyclerView recyclerView;
    RadioButton tous;
    RadioButton exams;
    RadioButton tp;
    RadioButton td;
    RadioButton cours;
    RadioButton ds;
    SearchView searchView;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioGroup FIL ;
    String major ;
    RadioButton fil;
    RadioButton Tousfil;
    String T;
    String searchText;


    public Search() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState!=null){
            searchText=savedInstanceState.getString("searchText");

        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setQuery(searchText,true);

        searchView.setMaxWidth(2000);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Type something...");
        searchView.setIconified(false);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.bg_white_background);

        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.DKGRAY);


        int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        closeButtonImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
       final View v = inflater.inflate(R.layout.search_fragment, container, false);
        getActivity().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        tous = (RadioButton) v.findViewById(R.id.Tous);
        cours = (RadioButton) v.findViewById(R.id.Cours);
        td = (RadioButton) v.findViewById(R.id.TD);
        tp = (RadioButton) v.findViewById(R.id.TP);
        ds = (RadioButton) v.findViewById(R.id.DS);
        fil=(RadioButton)  v.findViewById(R.id.Major);
        Tousfil=(RadioButton) v.findViewById(R.id.TousFiliere);
        FIL =(RadioGroup)  v.findViewById(R.id.FIL);
        exams = (RadioButton) v.findViewById(R.id.Exams);
        radioGroup1 = (RadioGroup) v.findViewById(R.id.rg1);
        radioGroup2 = (RadioGroup) v.findViewById(R.id.rg2);


       FIL.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (fil.isChecked()){
                    major="5c64773e38e7f64f8d07dc1b";
                    if (searchView.getQuery().toString().equals("")) {


                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        apiTypeMajor(searchView.getQuery().toString(), T, major);
                    }
                }
                if (Tousfil.isChecked()){
                    major="";

                    if (searchView.getQuery().toString().equals("")) {


                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        apiTypeMajor(searchView.getQuery().toString(), T, major);
                    }

                }
            }
        });



        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if (tous.isChecked()||exams.isChecked()||ds.isChecked()){
                   radioGroup2.clearCheck();
                   if (tous.isChecked()){
                       T="";
                   }
                   if (ds.isChecked()){
                       T="DS";
                   }
                   if (exams.isChecked()){
                       T="EX";
                   }

                   if (searchView.getQuery().toString().equals("")) {


                   }else {
                       progressBar.setVisibility(View.VISIBLE);
                       apiTypeMajor(searchView.getQuery().toString(), T, major);
                   }
               }




            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (tp.isChecked()||td.isChecked()||cours.isChecked()){
                    radioGroup1.clearCheck();
                    if (tp.isChecked()){
                        T="TP";
                    }
                    if (td.isChecked()){
                        T="TD";
                    }
                    if (cours.isChecked()){
                        T="Cours";
                    }

                    if (searchView.getQuery().toString().equals("")) {


                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        apiTypeMajor(searchView.getQuery().toString(), T, major);
                    }
                }



            }
        });


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.search_list);
        recyclerView.setOnTouchListener(this);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarSearch);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;

    }


    @Override
    public boolean onQueryTextChange(String newText) {
        progressBar.setVisibility(View.VISIBLE);
        apiTypeMajor(searchView.getQuery().toString(), T, major);
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        return false;

    }


    public void apiTypeMajor(String name,String type,String major) {
        documents = new ArrayList<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_search = apiInterface.getFilterTypeMajor(type,major,name);
        call_search.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), documents);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                recyclerView.setAdapter(recyclerViewAdapter);

                if (response.isSuccessful()) {


                    JsonArray resArr = response.body().getAsJsonArray();

                    for (int i = 0; i < resArr.size(); i++) {
                        JsonObject obj = resArr.get(i).getAsJsonObject();
                        Boolean approved = obj.get("approved").getAsBoolean();
                        if (approved) {
                            JsonObject oUser = obj.get("user").getAsJsonObject();
//document attributs init
                            String type = obj.get("type").getAsString();
                            int semestre = obj.get("semestre").getAsInt();
                            int NBDownloads = obj.get("NBDowloads").getAsInt();
                            Boolean verifiedByProf = obj.get("verifiedByProf").getAsBoolean();
                            String session = obj.get("session").getAsString();
                            String _id = obj.get("_id").getAsString();
                            String title = obj.get("title").getAsString();
                            String filePath = obj.get("filePath").getAsString();
                            //String major = obj.get("major").getAsString();
                            //String subject = obj.get("subject").getAsString();
                            int year = obj.get("year").getAsInt();
                            String profName = obj.get("profName").getAsString();
                            String description = obj.get("description").getAsString();
                            String createdAt = obj.get("createdAt").getAsString();
                            String updatedAt = obj.get("updatedAt").getAsString();
// user attribut init
                            String uType = oUser.get("type").getAsString();
                            String u_id = oUser.get("_id").getAsString();
                            String email = oUser.get("email").getAsString();
                            String firstName = oUser.get("firstName").getAsString();
                            String lastName = oUser.get("lastName").getAsString();

                            documents.add(new Document(type, semestre, approved, NBDownloads, verifiedByProf, session, _id, title, filePath, new User(uType, false, u_id, email, firstName, lastName, 0), "", "", year, profName, description, createdAt, updatedAt));
                        }
                    }

                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), documents);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    recyclerView.setAdapter(recyclerViewAdapter);

                }


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchText",searchView.getQuery().toString());
    }

}
