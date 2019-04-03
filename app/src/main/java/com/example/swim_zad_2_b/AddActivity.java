package com.example.swim_zad_2_b;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "prefs";
    public static final String RGB_BACK = "BackgroundColor";
    public static final String THEME_PREFS = "ThemePrefs";

    public static final String LIST_SHARED_PREFS = "ListPrefs";
    public static final String LIST = "List";
    public static final String ListCheck = "ListCheck";

    private EditText etx1,etx2,etx3;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2, RB;
    private RatingBar ratingBar;
    private Button b1,b2;



    ArrayList<Position> arrayList;

    public float RGB_BACKGROUND;

    float[] hsvColor = {0, 1, 1};
    int Theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemePrefs();
        setContentView(R.layout.activity_add);
        backgroundColor();

        etx1 = (EditText) findViewById(R.id.editText2);
        etx2 = (EditText) findViewById(R.id.editText3);
        etx3 = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb1= (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        b1 = (Button) findViewById(R.id.button4);
        b2 = (Button) findViewById(R.id.button5);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPosition();
            }
        });





    }


    public void addPosition(){

        boolean isFilm = true;
        String str = etx1.getText() + "  ~" + etx2.getText() +" [" + etx3.getText() + "]  ocena: " + Float.toString(ratingBar.getRating()) + "( /4)";
        RB = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        if(RB == rb1){
           // str += "F";
            isFilm = true;
        }
        else{
           // str += "K";
            isFilm = false;
        }

        Position position = new Position(etx1.getText()+"",etx2.getText()+"",etx3.getText()+"", ratingBar.getRating(), isFilm);

        loadData();

        arrayList.add(position);

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(LIST,json);
        editor.apply();

        String str1 ="";
        for(Position pos:arrayList){
            str1 += pos;
        }

        etx1.setText(str1);
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
