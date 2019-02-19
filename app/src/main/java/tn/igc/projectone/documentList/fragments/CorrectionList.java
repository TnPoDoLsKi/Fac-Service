package tn.igc.projectone.documentList.fragments;

import android.app.DownloadManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.APIClient;
import tn.igc.projectone.APIInterface;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.adapters.CorrectionRecyclerViewAdapter;
import tn.igc.projectone.documentList.adapters.DocumentRecyclerViewAdapterInCorrection;
import tn.igc.projectone.documentList.adapters.RecyclerViewAdapter;
import tn.igc.projectone.documentList.classes.CorrectionDoc;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.classes.User;

import static android.content.ContentValues.TAG;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class CorrectionList extends Fragment {
    ProgressBar progressBar;
    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView cRecyclerView;
    private ArrayList<Document> DocList;
    private ArrayList<CorrectionDoc> CorList;
    public APIInterface apiInterface;
    DocumentRecyclerViewAdapterInCorrection recyclerViewAdapter;
    CorrectionRecyclerViewAdapter cRecyclerViewAdapter;


    public CorrectionList() {
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Correction List");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        v = inflater.inflate(R.layout.correction_fragment, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.mtr_list);
        cRecyclerView = (RecyclerView) v.findViewById(R.id.corr);

        DocList = new ArrayList<Document>();
        CorList = new ArrayList<CorrectionDoc>();
        Bundle bundle = getArguments();
        String _id = bundle.getString("b_id");
        int b_avatar = bundle.getInt("b_avatar");
        final boolean b_verifiedByProf = bundle.getBoolean("b_verifiedByProf");
        String b_title = bundle.getString("b_title");
        String b_firstName = bundle.getString("b_firstName");
        String b_lastName= bundle.getString("b_lastName");
        String b_filePath=bundle.getString("b_filePath");
        String b_description=bundle.getString("b_description");
        getActivity().setTitle(b_title);
        TextView textView= (TextView) v.findViewById(R.id.description);
        textView.setText(b_description);



        Document doc = new Document(_id,b_verifiedByProf,b_title,new User(b_firstName,b_lastName,b_avatar),b_filePath);
        DocList.add(doc);


        recyclerViewAdapter = new DocumentRecyclerViewAdapterInCorrection(getContext(), DocList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapter);





       APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call_one_corr = apiInterface.getOneCorr(_id);
        call_one_corr.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    JsonArray corrs_array = response.body().getAsJsonArray();

               for (int i = 0; i < corrs_array.size(); i++)

                {
                    JsonObject corr =  corrs_array.get(i).getAsJsonObject();
                    JsonObject oUser= corr.get("user").getAsJsonObject();
                    //Correction init
                    Boolean approved=corr.get("approved").getAsBoolean();
                    Boolean verifiedByProf = corr.get("verifiedByProf").getAsBoolean();
                    int score=corr.get("score").getAsInt();
                    String _id = corr.get("_id").getAsString();
                    String title = corr.get("title").getAsString();
                    String  filePath=corr.get("filePath").getAsString();
                    String document =corr.get("document").getAsString();
                    String createdAt=corr.get("createdAt").getAsString();
                    String updatedAt=corr.get("updatedAt").getAsString();
                    //user init
                    String uType=oUser.get("type").getAsString();
                    Boolean deleted =oUser.get("deleted").getAsBoolean();
                    String u_id=oUser.get("_id").getAsString();
                    String email=oUser.get("email").getAsString();
                    String firstName=oUser.get("firstName").getAsString();
                    String lastName=oUser.get("lastName").getAsString();
                   // int _v=oUser.get("__v").getAsByte();
                    if (uType.equals("Prof"))
                    {

                        CorList.add(new CorrectionDoc(approved,verifiedByProf,score,_id,title,filePath,new User(uType,deleted,u_id,email,"Corrige de l'enseignant","",R.drawable.ic_check_circle_24px),document,createdAt,updatedAt));

                    }
else {


                        CorList.add(new CorrectionDoc(approved, verifiedByProf, score, _id, title, filePath, new User(uType, deleted, u_id, email, firstName, lastName, 0), document, createdAt, updatedAt));
                    }
}
                  //  CorList.add(new CorrectionDoc(true,true,0,"dfs","dfsd","fsf",new User("dfsf",false,"fsdf","dsfds","fsdds","fsdf",0),"sdfsd","dsfsd","dfdsf"));


                    cRecyclerViewAdapter = new CorrectionRecyclerViewAdapter(getContext(), CorList);
                    cRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    cRecyclerView.setAdapter(cRecyclerViewAdapter);
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });



        return v;
    }


}


