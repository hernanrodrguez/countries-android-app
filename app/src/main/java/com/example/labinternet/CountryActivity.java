package com.example.labinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
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
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

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
        protected Bitmap doInBackground(String... urls) {
            String url_display = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(url_display).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            myImageView.setImageBitmap(result);
            dialog.cancel();
        }
    }
}