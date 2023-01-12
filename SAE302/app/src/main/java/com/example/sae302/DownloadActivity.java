package com.example.sae302;

//IMPORT
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
///


public class DownloadActivity extends Activity {

//VARIABLES ICI :

    FTPSClient ftps;
    Boolean isDir = false;
    String fname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);//RENVOIE SUR FICHIER XML Activity_Download.xml

        ftps = (FTPSClient)Globals.global_ftps;
        Intent myIntent = getIntent();
        fname = myIntent.getStringExtra("fichier");

        isDir = myIntent.getBooleanExtra("isDir", false);
        TextView tv = (TextView) findViewById(R.id.yousure);
        tv.setText("Êtes-vous sûr de vouloir télécharger ?" + fname + "?");
        Button btn_yes = (Button) findViewById(R.id.button_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout parent = (LinearLayout) findViewById(R.id.download_layout);
                View child = (View) findViewById(R.id.button_bar);
                parent.removeView(child);
                downloadFiles();
            }
        });
        Button btn_no = (Button) findViewById(R.id.button_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                startActivity(i);
            }
        });
    }
    private void downloadFiles() {
        String[] fileNames = null;
        ArrayList<String> folders = new ArrayList<String>();
        FTPFile[] files;
        // Mise en page/afficher des trucs
        LinearLayout linlay = (LinearLayout) findViewById(R.id.download_layout);
        LayoutParams lparams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView tv = (TextView) findViewById(R.id.yousure);
        tv.setText("tmp");
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        if (!isDir) {
            File file = new File(path, fname);
            FileOutputStream out;
            tv.setText("Fichier de téléchargement :" + fname);
            try {
                out = new FileOutputStream(file);
                if (ftps.retrieveFile(fname, out) == false) {
                    System.err.println("Erreur lors du téléchargement du fichier");
                    out.close();
                    Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                    startActivity(i);
                }
                out.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                startActivity(i);
            }
        }
        else {
            File directory = new File(path, fname);
            FileOutputStream out;
            tv.setText("Changer de répertoire : " + fname);
            if (!directory.exists()) {
                directory.mkdir();
            }
            //get files and directories
            try
            {
                ftps.cwd(fname);
                fileNames = ftps.listNames();
                files = ftps.listDirectories();
                for (FTPFile file : files) {
                    folders.add(file.getName());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                startActivity(i);
            }
            TextView textView2 = new TextView(this);
            textView2.setLayoutParams(lparams);
            textView2.setText("");
            linlay.addView(textView2);
            isDir = false;
            for (String f : fileNames) {
                for (String folder : folders) {
                    if (f.equals(folder)) {
                        isDir = true;
                    }
                }
                if (!isDir) {
                    textView2.append("Fichier de téléchargement : " + f + "\n");
                    try {
                        out = new FileOutputStream(new File(directory, f));
                        if (ftps.retrieveFile(f, out) == false) {
                            //System.err.println("Erreur lors du téléchargement du fichier");
                            Toast.makeText(this,"Erreur lors du téléchargement du fichier " ,Toast.LENGTH_SHORT).show();//Renvoie message sur mobile
                            out.close();
                            Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                            startActivity(i);
                        }
                        out.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                        startActivity(i);
                    }
                }
                isDir = false;
            }
            try
            {
                ftps.changeToParentDirectory();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }
        TextView textView3 = new TextView(this);
        textView3.setLayoutParams(lparams);
        textView3.setText("Téléchargement terminé");
        Toast.makeText(this,"Téléchargement terminé" ,Toast.LENGTH_SHORT).show();//Renvoie message sur mobile
        linlay.addView(textView3);
        Button btn = new Button(this);
        btn.setId(666);
        //SI appuie sur continuez renvoyez vers Files.Activity.class
        btn.setText("Continuez");
        linlay.addView(btn, lparams);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FilesActivity.class);
                startActivity(i);
            }
        });
    }
}





