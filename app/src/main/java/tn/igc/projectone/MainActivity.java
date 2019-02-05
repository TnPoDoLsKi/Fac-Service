package tn.igc.projectone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import tn.igc.projectone.documentList.fragments.DocumentList;
import tn.igc.projectone.filiere.API.APIInterface;
import tn.igc.projectone.search.fragment.Search;
import tn.igc.projectone.uploadEnonce.MainUploadFragment;

// Hello from the other side
// From Wael
// From Achouri
//From Mariam
//from masmoudi
// Hello from wassim ^_^
// Nouri says Hi
//from chaima
public class MainActivity extends AppCompatActivity {
    public static APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        Fragment fragment = new DocumentList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navigationItemReselectedListener);

/*
        apiInterface = APIClient.getClient().create(APIInterface .class);
        Call<JsonArray> call_all_majors = apiInterface.getAllMajors();

        call_all_majors.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                JsonArray majorsArray = response.body();
                Log.d("123456", "onResponse: "+majorsArray);


                int size = majorsArray.size();
                for (int i = 0; i < size; i++) {
                    JsonObject obj = response.body().get(i).getAsJsonObject();
                    JsonElement majorName = obj.get("name");



                }

            }


            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("","onFailure:"+t);

            }
        });*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {

                    case R.id.home_button:
                        selectedFragment = new AddFragment();
                        break;
                    case R.id.add_button:
                        selectedFragment = new AddFragment();
                        break;
                    case R.id.parametre_button:
                        selectedFragment = new MainUploadFragment();
                        break;
                    case R.id.search_button:
                        selectedFragment = new Search();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            }


        };


}
