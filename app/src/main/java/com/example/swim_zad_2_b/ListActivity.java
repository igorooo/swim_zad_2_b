package com.example.swim_zad_2_b;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "prefs";
    public static final String RGB_BACK = "BackgroundColor";
    public static final String THEME_PREFS = "ThemePrefs";

    public static final String LIST_SHARED_PREFS = "ListPrefs";
    public static final String LIST = "List";
    public static final String ListCheck = "ListCheck";


    ArrayList<Position> arrayList;

    public float RGB_BACKGROUND;

    float[] hsvColor = {0, 1, 1};
    int Theme;

    private ListView listView;
    private PositionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getThemePrefs();
        setContentView(R.layout.activity_list);
        backgroundColor();

        loadData();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new PositionListAdapter(this, R.layout.adapter_view_layout, arrayList);
        listView.setAdapter(adapter);


    }








    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(LIST_SHARED_PREFS,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST,"");

        Type type = new TypeToken<ArrayList<Position>>(){}.getType();

        arrayList = gson.fromJson(json, type);

        if(arrayList == null){
            arrayList = new ArrayList<Position>();
        }
    }

    public void backgroundColor(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        RGB_BACKGROUND = sharedPreferences.getFloat(RGB_BACK, 0);

        hsvColor[0] = RGB_BACKGROUND;

        getWindow().getDecorView().setBackgroundColor(Color.HSVToColor(hsvColor));
    }

    public void getThemePrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Theme = sharedPreferences.getInt(THEME_PREFS, R.style.AppTheme);
        setTheme(Theme);

    }
}
