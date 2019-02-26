package tn.igc.projectone.search.fragment;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.authentification.util.SaveSharedPreference;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;
import tn.igc.projectone.documentList.classes.User;
import static android.content.Context.INPUT_METHOD_SERVICE;


public class    Search extends Fragment implements SearchView.OnQueryTextListener, View.OnTouchListener {
    ArrayList<Document> documents;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar progressBar;
    public APIInterface apiInterface;
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
    TextView textView;
    BottomNavigationView bottomNavigationView;
    public Search() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomBar);

        SharedPreferences settings = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("query", "");
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bottomNavigationView.getSelectedItemId()!=R.id.search_button)
        {
            bottomNavigationView.setSelectedItemId(R.id.search_button);
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
        searchView.setQueryHint("Rechercher...");
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconified(false);
        SharedPreferences settings = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        searchView.setQuery(settings.getString("query",""), true);

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
        major=SaveSharedPreference.getMajor(getContext());


        FIL.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (fil.isChecked()){
                   major=SaveSharedPreference.getMajor(getContext());
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
        LinearLayout linearLayout=(LinearLayout) getView().findViewById(R.id.search_layout);
        linearLayout.setOnTouchListener(this);
        ScrollView scrollView=(ScrollView) getView().findViewById(R.id.scrollviewSearch);
        scrollView.setOnTouchListener(this);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarSearch);
        textView = (TextView) getView().findViewById(R.id.Textview);
        textView.setText("Aucun document ");



    }


    @Override
    public boolean onQueryTextSubmit(String query) {

                documents = new ArrayList<>();
                progressBar.setVisibility(View.VISIBLE);
                apiTypeMajor(searchView.getQuery().toString(), T, major);

                return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {

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


        textView.setVisibility(View.INVISIBLE);

        call_search.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), documents);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewAdapter);

                if (response.isSuccessful()) {
                    JsonArray resArr = response.body().getAsJsonArray();

                    for (int i = 0; i < resArr.size(); i++) {
                        JsonObject obj = resArr.get(i).getAsJsonObject();
                           Boolean approved ;
                        if (obj.get("approved")==null) {
                            approved = false;                        }
                        else{
                            approved = obj.get("approved").getAsBoolean();                        }
//document attributs init
                            String type;
                            if (obj.get("type")==null) {
                                type = "";
                            }
                            else{
                                type = obj.get("type").getAsString();
                            }

                            int semestre ;
                            if (obj.get("semestre")==null) {
                                semestre =0;                            }
                            else{
                                semestre = obj.get("semestre").getAsInt();                           }

                            int NBDownloads ;

                            if (obj.get("NBDowloads")==null) {
                                NBDownloads = 0;
                            }
                            else{
                                NBDownloads = obj.get("NBDowloads").getAsInt();                            }


                            Boolean verifiedByProf ;
                            if (obj.get("verifiedByProf")==null) {
                                verifiedByProf = false;
                            }
                            else{
                                verifiedByProf = obj.get("verifiedByProf").getAsBoolean();                            }


                            String session ;
                            if (obj.get("session")==null) {
                                session = "";
                            }
                            else{
                                session = obj.get("session").getAsString();                            }

                            String _id ;
                            if (obj.get("_id")==null) {
                                _id = "";                            }
                            else{
                                _id = obj.get("_id").getAsString();
                            }

                            String title ;
                            if (obj.get("title")==null) {
                                title = "";
                            }
                                else{
                                    title = obj.get("title").getAsString();
                                }

                                String filePath ;

                            if (obj.get("filePath")==null){
                                filePath = "";
                            }
                            else {
                                filePath = obj.get("filePath").getAsString();
                            }
                            String subject ="" ;

                         /*   if (obj.get("subject")==null){
                                 subject = "";
                            }
                            else {
                                 subject = obj.get("subject").getAsString();

                            }*/
                            String majorApi="";
/*                            if (obj.get("major")==null){
                                 majorApi ="";
                            }
                            else{
                                 majorApi = obj.get("major").getAsString();

                            }*/
                            int year = 0;

                            if (obj.get("year")==null){
                                year = 0;
                            }
                            else{
                                year = obj.get("year").getAsInt();

                            }
                            String profName ;

                            if (obj.get("profName")==null){
                                 profName = "";
                            }
                            else{
                                 profName = obj.get("profName").getAsString();

                            }
                            String description ;


                            if (obj.get("description")==null){
                                 description = "";
                            }
                            else{
                                 description = obj.get("description").getAsString();

                            }
                            String createdAt;

                            if (obj.get("createdAt")==null){
                                createdAt="";
                            }
                            else{
                                createdAt = obj.get("createdAt").getAsString();

                            }
                            String updatedAt;
                            if (obj.get("updatedAt")==null){
                                 updatedAt = "";
                            }
                            else{
                                 updatedAt = obj.get("updatedAt").getAsString();

                            }
                            if (approved==true) {
                                JsonObject oUser = obj.get("user").getAsJsonObject();
                                String uType = oUser.get("type").getAsString();

                                String u_id = oUser.get("_id").getAsString();
                                String email = oUser.get("email").getAsString();
                                String firstName = oUser.get("firstName").getAsString();
                                String lastName = oUser.get("lastName").getAsString();

                                documents.add(new Document(type, semestre, approved, NBDownloads, verifiedByProf, session, _id, title, filePath, new User(uType, false, u_id, email, firstName, lastName, 0), majorApi, subject, year, profName, description, createdAt, updatedAt));
                            }
                    }


                    if (documents.size()==0){
                        textView.setText("Aucun document trouvée");
                        textView.setVisibility(View.VISIBLE);

                    }

                }


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                if(isOnline()==false){
                    textView.setText("Aucune connexion trouvée");
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), documents);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
                else
                    textView.setText("Aucun document trouvée");



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

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("query", searchView.getQuery().toString());

        editor.apply();
    }


}
