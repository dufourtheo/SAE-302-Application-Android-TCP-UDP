package com.example.sae302;
//IMPORT ///
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//////////////////////

public class FtpActivity extends AppCompatActivity implements OnClickListener {
    private EditText  username, mdp,serveur; /// EDIT TEXT (Nom utilisateur , mot de passe , ip serveur)
    private Button Connexion; //Bouton connexion
    boolean error = false;
    String protocol = "SSL";
    FTPSClient ftps;
    public static final String SAE_302 = "SAE302";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ftps = new FTPSClient(protocol);
        ftps.addProtocolCommandListener(new PrintCommandListener(new
                PrintWriter(System.out)));

        //VARIABLES ICI :

        serveur =  findViewById(R.id.server);     // Pas besoin de preciser par exemple server= (EditText) car preciser au dessus .
        username =  findViewById(R.id.username); // Pas besoin de preciser par exemple username = (EditText) car preciser au dessus .
        mdp =  findViewById(R.id.password); // Pas besoin de preciser par exemple password = (EditText) car preciser au dessus .
        Connexion = findViewById(R.id.myButton); // Pas besoin de preciser par exemple theButton = (Button) car preciser au dessus .
        Connexion.setOnClickListener(this);
    }

    public void onClick(View v) {
        //respond to click
        if (v.getId() == Connexion.getId()) {
            // init connection
            try
            {
                int reply;
                ftps.connect(serveur.getText().toString());
               // System.out.println("Connecté à" + server.getText().toString() + ".");
                Toast.makeText(this,"Connecté à " + serveur.getText().toString() + "." ,Toast.LENGTH_SHORT).show();
                reply = ftps.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply))
                {
                    ftps.disconnect();
                    // System.err.println("Le serveur FTP a refusé la connexion.");
                    Toast.makeText(this,"Le serveur FTP a refusé la connexion. " ,Toast.LENGTH_SHORT).show(); //Renvoie message sur le mobile
                    System.exit(1);
                }
            }
            catch (IOException e)
            {
                if (ftps.isConnected())
                {
                    try
                    {
                        ftps.disconnect();
                    }
                    catch (IOException f)
                    {
                        // do nothing
                    }
                }
                //System.err.println("Le client FTP n'a pas pu se connecter au serveur.");
                Toast.makeText(this,"Le serveur FTP a refusé la connexion. " ,Toast.LENGTH_SHORT).show(); //Renvoie message sur le mobile
                e.printStackTrace();
                System.exit(1);
            }
            // Connexion
            try
            {
                ftps.setBufferSize(1000);
                if (!ftps.login(username.getText().toString(),
                        mdp.getText().toString()))
                {
                    ftps.logout();
                    error = true;
                    System.exit(1);
                }
                ftps.setFileType(FTP.BINARY_FILE_TYPE);
                ftps.enterLocalPassiveMode();
                ftps.sendCommand("OPTS UTF8 ON");
                Globals.global_ftps = ftps;
                Toast.makeText(this,"Connexion effectue",Toast.LENGTH_SHORT).show(); //Renvoie message sur le mobile

                Intent i = new Intent(getApplicationContext(),FilesActivity.class);
                startActivity(i);
            }
            catch (FTPConnectionClosedException e)
            {
                error = true;
                //System.err.println("Connexion au serveur fermée.");
                Toast.makeText(this,"Connexion effectue",Toast.LENGTH_SHORT).show(); //Renvoie message sur le mobile
                e.printStackTrace();
            }
            catch (IOException e)
            {
                error = true;
                e.printStackTrace();
            }
            System.out.println(Environment.getDataDirectory());
            System.out.println(Environment.getExternalStorageDirectory());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gonflez le menu, cela ajoute des éléments à la barre d'action si elle est présente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Gérer les clics sur les éléments de la barre d'action ici. La barre d'action
        // gère automatiquement les clics sur le bouton Home/Up, aussi longtemps
        // lorsque vous spécifiez une activité parente dans AndroidManifest.xml.

        //Plus de detail : https://stackoverflow.com/questions/28816725/mainactivity-java-action-settings-cannot-be-resolved-or-is-not-a-field

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
