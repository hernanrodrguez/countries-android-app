package com.example.labinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CountryActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Country countryClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_COUNTRY);
        dialog = new ProgressDialog(this);
        completeCountryInfo(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SvgLoader.pluck().close();
    }

    private void completeCountryInfo(String data) {
        JSONObject country = new JSONObject();
        try{
            countryClass = new Country(new JSONObject(data));
            country = new JSONObject(data);
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
        TextView tvCountry = findViewById(R.id.tv_country_name);
        TextView tvNative = findViewById(R.id.tv_native);
        TextView tvCapital = findViewById(R.id.tv_capital);
        TextView tvRegion = findViewById(R.id.tv_region);
        TextView tvSubregion = findViewById(R.id.tv_subregion);
        TextView tvPopulation = findViewById(R.id.tv_population);
        TextView tvDemonym = findViewById(R.id.tv_demonym);
        ImageView image = findViewById(R.id.flag_imageView);

        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(R.layout.activity_country, null);

        ScrollView sv = findViewById(R.id.scrollView);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        ArrayList<String> props = countryClass.getProperties();
        for(String prop : props){
            TextView tv = new TextView(this);
            tv.setLayoutParams(
                    new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            tv.setText(prop);
            ll.addView(tv);
            //Toast.makeText(CountryActivity.this, prop, Toast.LENGTH_SHORT).show();
        }
        sv.addView(ll);

        //setContentView(v);
        /*
        try{
            tvCountry.setText(country.getString("name"));
            tvNative.setText(country.getString("nativeName"));
            tvCapital.setText(country.getString("capital"));
            tvRegion.setText(country.getString("region"));
            tvSubregion.setText(country.getString("subregion"));
            tvPopulation.setText(country.getString("population"));
            tvDemonym.setText(country.getString("demonym"));

            SvgLoader.pluck()
                    .with(this)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(countryClass.getFlagLink(), image);

        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}