package com.example.webservice2;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    JSONObject json;
    JSONArray clasesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("prueba","Reiniciando app");

// llamamos AsynTask para ejecutarlo en un hilo por separado
        new HttpAsyncTask().execute("http://fich.unl.edu.ar/reservas-fich/service/webservice-bedelia-movil.php");
    }

    private boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute muestra los resultados de AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            ArrayList<Clase> clases = new ArrayList<>();
            try {
                json = new JSONObject(result);
                clasesArray = json.getJSONArray("clases");
                int cantidad = json.getInt("cantidad");
                Log.d("prueba",String.valueOf(cantidad) + " clases en json");
                for(int i = 0; i<cantidad; i++){
                    JSONObject clase = clasesArray.getJSONObject(i);
                    String area = clase.getString("area");
                    String aula = clase.getString("aula");
                    String evento = clase.getString("evento");
                    String horaInicio = clase.getString("hora_inicio");
                    String horaFin = clase.getString("hora_fin");
                    int estado = clase.getInt("estado");
                    clases.add(new Clase(area,aula,evento,horaInicio,horaFin,estado));
                }
//                comprobar como se ve cuando estado != 0
//                clases.get(2).setEstado(1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ClaseAdapter adapter = new ClaseAdapter(getApplicationContext(), clases);
            ListView lstClases = (ListView)findViewById(R.id.lstClases);
            lstClases.setAdapter(adapter);

            Log.d("prueba", String.valueOf(clases.size()) + " clases fueron cargadas");

        }

        public String GET(String url){
            InputStream inputStream = null;
            String result = "";
            try {
                // creo el cliente http HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                // hago una peticion GET a la URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
                // recibo la respuesta inputStream
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "No funcionó!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws
                IOException {
            BufferedReader bufferedReader = new BufferedReader( new
                    InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }
    }
}