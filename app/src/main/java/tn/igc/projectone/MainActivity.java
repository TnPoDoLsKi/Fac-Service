package tn.igc.projectone;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ligl.android.widget.iosdialog.IOSDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import tn.igc.projectone.Home.Fragments.Matiere_Fragment;
import tn.igc.projectone.Settings.SettingsFragment;
import tn.igc.projectone.authentification.activities.SignUpActivity;
import tn.igc.projectone.search.fragment.Search;
import tn.igc.projectone.uploadEnonce.MainUploadFragment;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView title ;
    ProgressBar progressBar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(R.id.action_bar_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBarS);
        setActionBarTitle("Matières");
            //******
        bottomNavigationView = findViewById(R.id.bottomBar);
        Fragment fragment = new Matiere_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navigationItemReselectedListener);
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = new Matiere_Fragment() ;
                switch (menuItem.getItemId()){

                    case R.id.home_button:
                        selectedFragment = new Matiere_Fragment();
                        setActionBarTitle("Matières");
                        //title.setText("Matières");
                        break;
                    case R.id.add_button:
                        if(SaveSharedPreference.getToken(MainActivity.this).equals(""))
                        {
                            new IOSDialog.Builder(MainActivity.this)
                                .setTitle("Aucune Autorisation")
                                .setMessage("vous devez se connecter à un compte pour ajouter des fichiers")

                                .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            SaveSharedPreference.setMajor(MainActivity.this,"");
                                            SaveSharedPreference.setMajorName(MainActivity.this,"");
                                            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                                .setNegativeButton("Annuler",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                        }else {
                            selectedFragment = new MainUploadFragment();
                            setActionBarTitle("Importer");
                            //title.setText("Ajouter");
                        }
                        break;
                    case R.id.parametre_button:
                        selectedFragment = new SettingsFragment();
                        setActionBarTitle("Paramètres");
                        // title.setText("Paramètres");
                        break;
                    case R.id.search_button:
                        selectedFragment = new Search();
                        setActionBarTitle("Recherche");
                        //title.setText("Recherche");
                        break;
                }
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container,selectedFragment).commit();
                return  true ;
            }


        };
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void setActionBarTitle(String newTitle){
        title.setText(newTitle);
    }

    public void setVisibleProgressBar( ){ progressBar.setVisibility(View.VISIBLE);}

    public void setInvisibleProgressBar(){  progressBar.setVisibility(View.INVISIBLE);}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}