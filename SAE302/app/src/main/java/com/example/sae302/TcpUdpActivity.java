package com.example.sae302;
//IMPORT
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
//////


public class TcpUdpActivity extends AppCompatActivity {
    //declaration des variables ici :
    private Socket client;
    private PrintWriter printwriter;
    private EditText textField;
    private Button button;
    private String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_udp);//ASSOCIE A L'ECRAN ACITVITY_TCP_UDP.xml
        //VARIABLES ICI :

        // référence au champ de texte
        textField =  findViewById(R.id.editText1);

        // reference au champ bouton
        button =  findViewById(R.id.button1);

        // Pour ecouter
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                // récupère le message texte sur le champ de texte
                message = textField.getText().toString();

                // démarre le Thread pour se connecter au serveur
                new Thread(new ClientThread(message)).start();

            }
        });
    }


    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                // l'adresse IP et le port doivent être corrects pour établir une connexion
                // Crée un socket de flux et le connecte au numéro de port spécifié sur l'hôte nommé.
                client = new Socket("192.168.72.240", 4444);  // connecte au serveur


                printwriter = new PrintWriter(client.getOutputStream(),true);

                printwriter.write(message); // écrit le message dans le flux de sortie

                printwriter.flush();
                printwriter.close();

                // fermeture de la connexion
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textField.setText("");
                }
            });
        }
    }
}