package com.example.swim_zad_2_b;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String SHARED_PREFS = "prefs";
    public static final String RGB_BACK = "BackgroundColor";
    public static final String SeekBarProg = "SeekBarProgress";
    public static final String THEME_PREFS = "ThemePrefs";
    public static final String RB_PREFS = "RBprefs";
    public static final String SPINNER_PREFS = "Spinner";

    public float RGB_BACKGROUND;
    public int seekBarProgress;
    public int Theme;
    View item;

    float[] hsvColor = {0, 1, 1};
    String[] STR = {"Hello", "Czesc", "Hola"};

    private SeekBar seekBar;

    private Button save,reset,cancel;
    private ImageView iv;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinner;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemePrefs();
        setTheme(Theme);

        setContentView(R.layout.activity_settings);
        seekBar = (SeekBar) findViewById(R.id.RseekBar);
        save = (Button) findViewById(R.id.saveButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        reset = (Button) findViewById(R.id.resetButton);
        iv = (ImageView) findViewById(R.id.view);
        radioGroup = (RadioGroup) findViewById(R.id.themeRG);
        spinner = (Spinner) findViewById(R.id.spinner);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STR);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        rbPrefs();
        getPrefs();
        getSpinnerPrefs();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(20);
                spinner.setSelection(2);
                RadioButton rb = (RadioButton) findViewById(R.id.theme2RB);

                rb.setChecked(true);

                savePrefs();
                saveThemePrefs();
                saveSpinnerPrefs(20);
                getPrefs();
                getThemePrefs();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrefs();
                getPrefs();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //savePrefs();
                changeColor();
                //backgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void changeColor(){

        seekBarProgress = seekBar.getProgress();
        float hsvColor_ = 360f *  seekBarProgress / 100;
        hsvColor[0] = hsvColor_;
        iv.setColorFilter(Color.HSVToColor(hsvColor));
    }

    public void getThemePrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Theme = sharedPreferences.getInt(THEME_PREFS, R.style.AppTheme);

    }

    public void saveThemePrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(THEME_PREFS,Theme);
        editor.putInt(RB_PREFS,radioButton.getId());
        editor.apply();

    }

    public void rbPrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        radioButton = (RadioButton) findViewById(sharedPreferences.getInt(RB_PREFS,R.id.theme1RB));

        try{
            radioButton.setChecked(true);
        }
        catch (NullPointerException e){
            radioButton = (RadioButton) findViewById(R.id.theme1RB);
        }
    }



    public void backgroundColor(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        RGB_BACKGROUND = sharedPreferences.getFloat(RGB_BACK, 0);

        hsvColor[0] = RGB_BACKGROUND;

        getWindow().getDecorView().setBackgroundColor(Color.HSVToColor(hsvColor));
    }

    public void getPrefs(){


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        RGB_BACKGROUND = sharedPreferences.getFloat(RGB_BACK, 0);
        seekBarProgress = sharedPreferences.getInt(SeekBarProg,0);

        seekBar.setProgress(seekBarProgress);
        hsvColor[0] = RGB_BACKGROUND;
        getWindow().getDecorView().setBackgroundColor(Color.HSVToColor(hsvColor));
        changeColor();
    }

    public void savePrefs(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        seekBarProgress = seekBar.getProgress();
        float hsvColor = 360f *  seekBarProgress / 100;

        editor.putInt(SeekBarProg,seekBarProgress);
        editor.putFloat(RGB_BACK,hsvColor);
        editor.apply();

    }

    public void saveSpinnerPrefs(int position){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SPINNER_PREFS,position);
        editor.apply();
    }

    public void getSpinnerPrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        spinner.setSelection(sharedPreferences.getInt(SPINNER_PREFS,0));
    }

    public void RBclick(View v){

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


        switch (radioButton.getId()){
            case R.id.theme1RB:
                Theme = R.style.AppTheme;
                break;

            case R.id.theme2RB:
                Theme = R.style.AppTheme2;
                break;

            case R.id.theme3RB:
                Theme = R.style.AppTheme3;
                break;
        }


        saveThemePrefs();
        restartACtivity();
    }

    private void restartACtivity(){

        setTheme(Theme);
        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        saveSpinnerPrefs(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
