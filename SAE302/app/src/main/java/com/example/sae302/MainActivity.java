package com.example.sae302;
//// IMPORT ///
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.view.View;

import android.widget.Button;

import android.os.Bundle;
////      ////

public class MainActivity extends AppCompatActivity {



    //VARIABLE ICI :



    Button ftp,ping,tcpudp,server; // Les différents boutons .




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Le fichier est associé à l'écran Activity_Main.xml



        ping =  findViewById(R.id.ping); //Pas besoin de préciser ping = (Button) findViewById(R.id.ping)
        ftp =  findViewById(R.id.ftp);   //Pas besoin de préciser ftp = (Button) findViewById(R.id.ftp)
        tcpudp =  findViewById(R.id.tcpudp); //Pas besoin de préciser tcpudp = (Button) findViewById(R.id.tcpudp)
        server = findViewById(R.id.server);  //Pas besoin de préciser server = (Button) findViewById(R.id.server)

        ///
        //CHANGER D'ACTIVITE AVEC UN CLICK SUR LE BOUTON FTP OU PING ACTIVITY OU CLIENT / SERVER
        ftp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ftp = new Intent(MainActivity.this, FtpActivity.class);
                startActivity(ftp);

            }
        });
        //LANCEMENT DE L'ACTIVITE Ping Activité au click
        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ping = new Intent(MainActivity.this,PingActivity.class);
                startActivity(ping);

            }
        });
        //LANCEMENT DE L'ACTIVITE TcpUdpActivity (Client FTP) au click
        tcpudp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tcpudp = new Intent(MainActivity.this,TcpUdpActivity.class);
                startActivity(tcpudp);

            }
        });
        //LANCEMENT DE L'ACTIVITE ServerActivity (Serveur FTP) au click
        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent server = new Intent(MainActivity.this,ServerActivity.class);
                startActivity(server);

            }
        });







    }


}