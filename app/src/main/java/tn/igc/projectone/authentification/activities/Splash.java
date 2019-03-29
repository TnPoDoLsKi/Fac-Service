package tn.igc.projectone.authentification.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.igc.projectone.API.APIClient;
import tn.igc.projectone.API.APIInterface;
import tn.igc.projectone.ClassisOnline;
import tn.igc.projectone.R;


public class Splash extends Activity {

    public Dialog onCreateDialog(Bundle savedInstanceState,String message) {

        IOSDialog.Builder builder = new IOSDialog.Builder(this);

        builder.setTitle("Nouvelle Version").setMessage(message).setCancelable(false)

            .setPositiveButton("Mise à jour", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    final String appPackageName = getPackageName();

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    }
                    catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

                    }

                }

            })
            .setNegativeButton("Ulterieurement", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i=new Intent(Splash.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        return builder.create();
    }




    public Dialog onCreateDialogExit(Bundle savedInstanceState,String message) {
        IOSDialog.Builder builder = new IOSDialog.Builder(this);
        builder.setTitle("Nouvelle Version").setMessage(message).setCancelable(false)
            .setPositiveButton("Mise à jour", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName();

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

                }

            }
        })
            ;
        return builder.create();
    }




APIInterface apiInterface;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (ClassisOnline.isOnline()==false){
            IOSDialog.Builder builder = new IOSDialog.Builder(this);
            builder.setMessage("aucune connexion n'est disponible").setCancelable(false).setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);             }


            }).create().show();
        }
        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionNumber = pinfo.versionCode;
        String versionName = pinfo.versionName;




        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call_version = apiInterface.isUpdated(versionName);
        call_version.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){

                    JsonObject version=response.body().getAsJsonObject();
                    if (version.get("newUpdate").getAsBoolean()==true){
                        if (version.get("version").getAsJsonObject().get("forceUpdate").getAsBoolean()==false){
                            String mes="La version "+version.get("version").getAsJsonObject().get("version").getAsString()+" est disponible.\nDescription: "+version.get("version").getAsJsonObject().get("description").getAsString();
                            onCreateDialog(savedInstanceState,mes).show();
                        }else {
                            String mes="La version "+version.get("version").getAsJsonObject().get("version").getAsString()+" est disponible.\nDescription: "+version.get("version").getAsJsonObject().get("description").getAsString();

                            onCreateDialogExit(savedInstanceState,mes).show();
                        }
                    }
                    else{
                        Thread t=new Thread() {
                            public void run() {

                                try {

                                    //sleep thread for 1 seconds, time in milliseconds
                                    sleep(1000);

                                    //start new activity
                                    Intent i=new Intent(Splash.this,LoginActivity.class);
                                    startActivity(i);

                                    //destroying Splash activity
                                    finish();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        //start thread
                        t.start();

                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }
}

