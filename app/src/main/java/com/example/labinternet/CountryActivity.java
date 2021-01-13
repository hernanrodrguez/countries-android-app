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
import android.util.TypedValue;
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
        Country country = new Country();
        try{
            country.setValues(new JSONObject(data));
        } catch (Exception e){
            Toast.makeText(CountryActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
        }

        TextView tvCountry = findViewById(R.id.tv_country_name);
        tvCountry.setText(country.getName());
        setSVGImage(country.getFlagLink(), findViewById(R.id.flag_imageView));

        ScrollView sv = findViewById(R.id.scrollView);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        ArrayList<String> props = country.getProperties();
        String[] labels = getResources().getStringArray(R.array.country_props);

        int i = 0;
        for(String label : labels) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            String show_text = label + props.get(i);
            tv.setText(show_text);
            ll.addView(tv);
            i++;
        }
        sv.addView(ll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setSVGImage(String link, ImageView image){
        SvgLoader.pluck()
                .with(this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(link, image);
    }
}