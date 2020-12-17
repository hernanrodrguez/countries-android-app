package com.example.labinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CountryActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_COUNTRY);
        dialog = new ProgressDialog(this);
        completeCountryInfo(data);
    }

    private void completeCountryInfo(String data) {
        JSONObject country = new JSONObject();
        try{
            country = new JSONObject(data);
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
        TextView tvCountry = findViewById(R.id.tv_country_name);
        TextView tvCapital = findViewById(R.id.tv_capital);
        TextView tvRegion = findViewById(R.id.tv_region);
        TextView tvSubregion = findViewById(R.id.tv_subregion);
        TextView tvPopulation = findViewById(R.id.tv_population);
        ImageView image = findViewById(R.id.flag_imageView);
        try{
            tvCountry.setText(country.getString("name"));
            tvCapital.setText(country.getString("capital"));
            tvRegion.setText(country.getString("region"));
            tvSubregion.setText(country.getString("subregion"));
            tvPopulation.setText(country.getString("population"));
            new DownloadImageTask(findViewById(R.id.flag_imageView))
                    .execute(country.getString("flag"));
                    //.execute("https://cdn.cienradios.com/wp-content/uploads/sites/3/2020/05/Bandera-argentina.jpg");
                    // CON LA IMAGEN EN FORMATO JPG SE CARGA CORRECTAMENTE
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        ImageView myImageView;

        public DownloadImageTask(ImageView bmImage) {
            this.myImageView = bmImage;
        }

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading... Please Wait");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Drawable doInBackground(String... urls) {
            try {
                final URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                SVG svg = SVGParser.getSVGFromInputStream(inputStream);
                Drawable drawable = svg.createPictureDrawable();
                return drawable;
            }catch (Exception e){
                Log.println(Log.ERROR, "CountryActivity", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            updateImageView(result);
            dialog.cancel();
        }

        @SuppressLint("NewApi")
        private void updateImageView(Drawable drawable){
            if(drawable != null){
                // Try using your library and adding this layer type before switching your SVG parsing
                myImageView.setLayerType(View.LAYER_TYPE_NONE, null);
                myImageView.setImageDrawable(drawable);
            }
        }
    }
}