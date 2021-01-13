package com.example.labinternet;

import org.json.JSONObject;

import java.util.ArrayList;

public class Country {

    private String Name;
    private String Capital;
    private String Region;
    private String Subregion;
    private int Population;
    private String Demonym;
    private String NativeName;
    private String FlagLink;

    public Country(){ }

    public void setValues (JSONObject json) {
        try{
            this.Name = json.getString("name");
            this.Capital = json.getString("capital");
            this.Region = json.getString("region");
            this.Subregion = json.getString("subregion");
            this.Population = json.getInt("population");
            this.Demonym = json.getString("demonym");
            this.NativeName = json.getString("nativeName");
            this.FlagLink = json.getString("flag");
        } catch (Exception e){
            this.Name = "";
            this.Capital = "";
            this.Region = "";
            this.Subregion = "";
            this.Population = 0;
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
