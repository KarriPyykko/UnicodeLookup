package com.example.unicodelookup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.example.unicodelookup.ui.main.SectionsPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        initUnicodeArray();
        initFavoritesArray();
    }

    private void initUnicodeArray(){
        ArrayList<Unicode> arr = new ArrayList<Unicode>();
        SharedPreferences sp = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("unicode.csv")));

            String line;
            String[] buffer;
            while ((line = br.readLine()) != null){
                buffer = line.split(";");

                if ((buffer[0] == null) || (buffer[1] == null))
                    continue;

                arr.add(new Unicode(buffer[0], buffer[1]));
            }
            br.close();

            Gson gson = new Gson();
            String json = gson.toJson(arr);
            editor.putString("UnicodeString", json);
            editor.commit();
        }
        catch (IOException e){
            Log.e("IOException", "Error in reading the file Unicode.csv");
        }
    }

    private void initFavoritesArray(){
        ArrayList<Unicode> favorites_arr = new ArrayList<Unicode>();
        SharedPreferences sp = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        try {
            Gson gson = new Gson();
            String json = gson.toJson(favorites_arr);
            editor.putString("FavoritesString", json);
            editor.commit();
        }
        catch (JsonIOException e){
            Log.e("IOException", "Error in json conversion");
        }
    }
}