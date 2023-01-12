package com.example.sae302;
//IMPORT
import androidx.appcompat.app.AppCompatActivity;


import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import android.text.Editable;
import android.os.Bundle;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

///

public class PingActivity extends AppCompatActivity {


    TextView ipadd;
    Button btnIP;
    ListView listePing;
    EditText edtIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);



        // POUR RECUP L'IP

        ipadd = findViewById(R.id.ipadd);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        ipadd.setText(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress())); //PERMET DE RECUPERER L'ADRESSE LORS D'UNE CONNEXION A UN RESEAU

        //INSTANCE

        btnIP = findViewById(R.id.btn_ping); // BOUTON POUR PING
        listePing = findViewById(R.id.listView_ping) ;// VOIR LES PING
        edtIP = findViewById(R.id.edit_ip) ; // CHANGER L'IP





    }



    //COMMANDE PING
    public void fExecuterPing(View view){

        Editable host = edtIP.getText(); //Host = Adresse IP Saisi par l'utilisateur
        List<String> listeReponsePing = new ArrayList<String>();
        ArrayAdapter<String> adapterListe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeReponsePing);

        try {

            String  cmdPing = "ping -c 3 "+host; //Ping 3 fois si plus changer l'argument ping -c x
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmdPing);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLinhe;


            Toast.makeText(this,"Commande ping execute",Toast.LENGTH_SHORT).show(); //Renvoie Commande Ping Execute


            //AFFICHE RESULTAT PING

            while((inputLinhe = in.readLine())!= null){
                listeReponsePing.add(inputLinhe);
                listePing.setAdapter(adapterListe);
            }




        } catch (Exception e) {

            Toast.makeText(this , "Erreur : "+ e.getMessage().toString(),Toast.LENGTH_SHORT).show(); // Si erreur lors du test de connectivite

        }


    }

}