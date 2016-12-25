package com.example.apanoo.apanoo_agile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import customfonts.MyTextView;

/**
 * Created by APANOO on 11/22/2016.
 */

public class categories extends AppCompatActivity {
    private Users user;
    private LinearLayout back;
    private View mContentView;
    MyTextView English;
    MyTextView Math;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edits();
        setContentView(R.layout.categories);
        mContentView = findViewById(R.id.fullscreen_content);
        English=(MyTextView)findViewById(R.id.english);
        Math=(MyTextView)findViewById(R.id.math);
        user=(Users)getIntent().getParcelableExtra("Users");
        hide();
        back =(LinearLayout)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent it=new Intent(categories.this,welcome.class);
                it.putExtra("Users", user);
                startActivity(it);
                finish();
            }
        });
        Math.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent it=new Intent(categories.this,MathGame.class);
                it.putExtra("Users", user);
                startActivity(it);
                finish();
            }
        });
        English.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent it=new Intent(categories.this,EnglishGame.class);
                it.putExtra("Users", user);
                startActivity(it);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent it=new Intent(categories.this,welcome.class);
        it.putExtra("Users", user);
        startActivity(it);
        finish();

    }
    @Override
    protected void onStart(){
        super.onStart();
        hide();
    }
    private void edits() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    private void hide() {
        mContentView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
