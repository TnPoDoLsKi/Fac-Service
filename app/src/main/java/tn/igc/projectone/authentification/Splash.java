package tn.igc.projectone.authentification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import tn.igc.projectone.R;

public class Splash extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            //creating thread that will sleep for 10 seconds
            Thread t=new Thread() {
                public void run() {

                    try {
                        //sleep thread for 10 seconds, time in milliseconds
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

