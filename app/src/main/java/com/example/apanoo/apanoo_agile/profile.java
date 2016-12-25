package com.example.apanoo.apanoo_agile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import customfonts.MyTextView;

/**
 * Created by APANOO on 11/21/2016.
 */

public class profile extends AppCompatActivity {
    private DatabaseHelper helper=new DatabaseHelper(this);
    private Users user;
    private MyTextView rank;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private LinearLayout back;
    private MyTextView username;
    private ImageView propic;
    private MyTextView signout;
    private View mContentView;
    private TextView scoreofuser;
    private TextView Engscoreofuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edits();
        setContentView(R.layout.profile);
        user=(Users)getIntent().getParcelableExtra("Users");
        mContentView = findViewById(R.id.fullscreen_content);
        username=(MyTextView)findViewById(R.id.usernameprof);
        username.setText(user.getUname());
        rank = (MyTextView) findViewById(R.id.Rank);
        propic=(ImageView)findViewById(R.id.propic);
        signout=(MyTextView)findViewById(R.id.signout);
        scoreofuser=(TextView)findViewById(R.id.scoreofprofile);
        scoreofuser.setText(String.valueOf(user.getMathScore()));
        Engscoreofuser=(TextView)findViewById(R.id.Engscore);
        Engscoreofuser.setText(String.valueOf(user.getEngScore()));
        signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    startActivity(new Intent(profile.this, main.class));
                    finish();
            }
        });
        hide();
        back = (LinearLayout)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(profile.this,welcome.class);
                it.putExtra("Users", user);
                startActivity(it);
                finish();
            }
        });
        //=========================================================================
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        propic.setImageURI(Uri.parse(user.getProfilepic()));
        //=========================================================================
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(profile.this,Rank.class);
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
        Intent it = new Intent(profile.this,welcome.class);
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
