package us.sambaai.etablissement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import us.sambaai.etablissement.connection.Connection;

public class MainActivity extends AppCompatActivity {
    Button envoyer;
    EditText nom,prenom, classe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        classe = findViewById(R.id.classe);
        envoyer = findViewById(R.id.envoyer);
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom1 = nom.getText().toString();
                String surname = prenom.getText().toString();
                String classroom = classe.getText().toString();
                new InsertData().execute(nom1,surname, classroom);
            }
        });

    }
    class InsertData extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try
            {
                String link = Connection.API + "insertion.php";

                String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode (strings[0],  "UTF-8") ;
                data += "&" + URLEncoder.encode( "prenom", "UTF-8") + "=" + URLEncoder.encode (strings[1],  "UTF-8");
                data += "&" + URLEncoder.encode( "classe",  "UTF-8") + "=" + URLEncoder.encode (strings[2],  "UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                return reader.readLine();
            }


            catch (Exception e)
            {
                e.printStackTrace();
                return "Error! " + e.getMessage();
            }
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try
            {
                JSONObject jsonObject = new JSONObject(s);

                if(jsonObject.getString( "response").equals("success"))
                {
                    Toast.makeText(MainActivity.this, "Enregistre avec success", Toast.LENGTH_LONG).show();
                }
		        else
                {
                    Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_LONG).show();
                }

            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }
}