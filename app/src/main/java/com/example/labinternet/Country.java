package com.example.labinternet;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

public class Country {

    public enum CountryProps {
        name, topLevelDomain, alpha2Code, alpha3Code, callingCodes, capital,
        altSpellings, region, subregion, population, latlng, demonym, area,
        gini, timezones, borders, nativeName, numericCode, currencies,
        languages, translations, flag, regionalBlocs, cioc
    };

    private String Name;
    private String TopLevelDomain;
    private String Alpha2Code;
    private String Alpha3Code;
    private String CallingCodes;
    private String Capital;
    private String AltSpellings;
    private String Region;
    private String Subregion;
    private String Population;
    private String LatLng;
    private String Demonym;
    private String Area;
    private String Gini;
    private String Timezones;
    private String Borders;
    private String NativeName;
    private String NumericCode;
    private String Currencies;
    private String Languages;
    private String Translations;
    private String FlagLink;
    private String RegionalBlocs;
    private String Cioc;

    public Country(){ }

    public void setValues (JSONObject json) {
        CountryProps props[] = CountryProps.values();
        for(CountryProps prop : props){
            try{
                Log.println(Log.ERROR, "InputStream", String.valueOf(prop));
                Log.println(Log.ERROR, "InputStream", json.getString(String.valueOf(prop)));
            }catch (Exception e){
                Log.println(Log.ERROR, "InputStream", e.getLocalizedMessage());
            }
        }

        try{
            this.Name = json.getString("name");
            this.Capital = json.getString("capital");
            this.Region = json.getString("region");
            this.Subregion = json.getString("subregion");
            this.Population = json.getString("population");
            this.Demonym = json.getString("demonym");
            this.NativeName = json.getString("nativeName");
            this.FlagLink = json.getString("flag");
        } catch (Exception e){
            this.Name = "";
            this.Capital = "";
            this.Region = "";
            this.Subregion = "";
            this.Population = "";
            this.Demonym = "";
            this.NativeName = "";
            this.FlagLink = "";
        }
    }

    public String getName(){ return this.Name; }

    public ArrayList<String> getProperties(){
        ArrayList<String> ret = new ArrayList<String>();

        ret.add(this.Name);
        ret.add(this.Capital);
        ret.add(this.Region);
        ret.add(this.Subregion);
        ret.add(String.valueOf(this.Population));
        ret.add(this.Demonym);
        ret.add(this.NativeName);

        return ret;
    }

    public String getFlagLink(){
        return this.FlagLink;
    }

}
