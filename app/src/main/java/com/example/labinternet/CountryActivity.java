package com.example.labinternet;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_COUNTRY);
        completeCountryInfo(data);
    }

    private void completeCountryInfo(String data) {
        JSONObject country = new JSONObject();
        try{
            country = new JSONObject(data);
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }
        TextView tv = findViewById(R.id.tv);
        try{
            tv.setText(country.getString("name"));
            new DownloadImageTask(findViewById(R.id.flag_imageView))
                    .execute(country.getString("flag"));
        } catch (Exception e){
            tv.setText("Failed to retrieve Country Name");
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap image = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
                Log.println(Log.ERROR, null, image.toString());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}