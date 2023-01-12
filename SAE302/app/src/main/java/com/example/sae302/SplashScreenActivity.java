package com.example.sae302;
//IMPORT
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//IMPORT
public class SplashScreenActivity extends AppCompatActivity {

    private final int splashscreen = 2000; //TEMPS CHARGEMENT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Rediriger vers la page principale "MainActivity" après 2 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //demarrer une page
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashscreen);



    }
}