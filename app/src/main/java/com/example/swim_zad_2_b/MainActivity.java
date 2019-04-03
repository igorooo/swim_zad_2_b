package com.example.swim_zad_2_b;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView tv1;

    public static final String SHARED_PREFS = "prefs";
    public static final String RGB_BACK = "BackgroundColor";
    public static final String THEME_PREFS = "ThemePrefs";

    public int Theme;
    public float RGB_BACKGROUND;

    float[] hsvColor = {0, 1, 1};

    private Button B1,B2,B3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getThemePrefs();
        setContentView(R.layout.activity_main);
        B1 = (Button) findViewById(R.id.button3);
        B2 = (Button) findViewById(R.id.button2);
        B3 = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView);


        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
            }
        });

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        int oldTheme = Theme;
        getThemePrefs();

        if(oldTheme != Theme)
            restartACtivity();


        backgroundColor();
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

    public void openSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    public void openAdd(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

    }

    public void openList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }




    private void restartACtivity(){

        setTheme(Theme);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }


}
