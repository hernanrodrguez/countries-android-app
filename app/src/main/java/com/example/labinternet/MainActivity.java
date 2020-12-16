package com.example.labinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.AccessNetworkConstants;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private ListView lvCountries;
    private List<String> listCountries;
    private ArrayAdapter<String> adapter;
    private JSONArray json;

    public static final String EXTRA_COUNTRY = "COUNTRY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCountries = new ArrayList<String>();
        lvCountries = findViewById(R.id.lvCountries);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listCountries);
        lvCountries.setAdapter(adapter);
        lvCountries.setOnItemClickListener(this::onItemClick);
        dialog = new ProgressDialog(this);
        new GetCountries().execute("https://restcountries.eu/rest/v2/all");
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean flag = true;
        JSONObject country = new JSONObject();
        Intent intent = new Intent(this, CountryActivity.class);

        try {
            country = json.getJSONObject(position);
        }catch (Exception e){
            flag = false;
            Toast.makeText(MainActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
        if(flag){
            intent.putExtra(EXTRA_COUNTRY, country.toString());
            startActivity(intent);
        }
    }

    public class GetCountries extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading... Please Wait");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream inputStream = null;
            String result = "";
            try{
                inputStream = new URL(urls[0]).openStream();
                if(inputStream != null){
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = buffer.readLine()) != null)
                        result += line;
                    inputStream.close();
                } else {
                    //ERROR
                    Log.println(Log.ERROR, null, "InputStream is null");
                }
            } catch (Exception e){
                //ERROR
                Log.println(Log.ERROR, "InputStream", e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.cancel();
            try{
                json = new JSONArray(s);
                JSONObject country;
                for(int i=0; i < json.length(); i++) {
                    country = json.getJSONObject(i);
                    String country_name = country.getString("name");
                    listCountries.add(country_name);
                    Log.println(Log.DEBUG, null, country_name);
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listCountries);
                lvCountries.setAdapter(adapter);
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}