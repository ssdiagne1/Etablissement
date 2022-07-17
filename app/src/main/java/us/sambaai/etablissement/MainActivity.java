package us.sambaai.etablissement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    Button envoyer;
    EditText nom,prenom, classe;
    ProgressDialog dialog;
    JSONParser parser= new JSONParser();
    int success;
    //String url="http://localhost/etablissement/insert.php";
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
                new Add().execute();
            }
        });

    }
    class Add extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("nom",nom.getText().toString());
            map.put("prenom",nom.getText().toString());
            map.put("classe",nom.getText().toString());
            JSONObject object= parser.makeHttpRequest("http://192.168.1.12/etablissement/add.php", "GET",map);

            try {
                success=object.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();

            if(success==1)
            {
                Toast.makeText(MainActivity.this, "Ajout effectue", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Echec !!! ", Toast.LENGTH_LONG).show();
            }
        }


    }
}