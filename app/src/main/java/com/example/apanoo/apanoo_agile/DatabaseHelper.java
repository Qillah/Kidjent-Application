package com.example.apanoo.apanoo_agile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by APANOO on 11/18/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "APPDB.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "USERNAME";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_PASS = "PASS";
    private static final String COLUMN_PROFILE_PIC = "PROFILE_PIC";
    private static final String COLUMN_MATHSCORE= "MATHSCORE";
    private static final String COLUMN_ENGSCORE= "ENGSCORE";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table users(ID integer primary key not null, " +
            "USERNAME varchar(255) unique not null, EMAIL  varchar(255) unique not null, PASS text not null, PROFILE_PIC varchar(255) not null ,MATHSCORE integer not null ,ENGSCORE integer not null);";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String query = "DROP TABLE IF EXSITS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }


    public void insertUser(Users c) {///henaaaaaa
        int score=0;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String qq = "select * from users";
        Cursor crr = db.rawQuery(qq, null);
        int count = crr.getCount();
        Uri path = Uri.parse("android.resource://com.example.apanoo.apanoo_agile/" + R.drawable.defaultprofile);
        values.put(COLUMN_ID,count);
        values.put(COLUMN_USERNAME, c.getUname());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASS, c.getPass());
        values.put(COLUMN_PROFILE_PIC, path.toString());
        values.put(COLUMN_MATHSCORE, score);
        values.put(COLUMN_ENGSCORE, score);
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
    public void Update_Picture(String Username,String Old,String New){
        db = this.getReadableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COLUMN_PROFILE_PIC,New);
        db.update(TABLE_NAME, contentvalues, COLUMN_USERNAME + " = ? AND " + COLUMN_PROFILE_PIC+ " = ?", new String[]{Username,Old});
    }
    public void Update_MathScore(String Username,int Old,int New){
        db = this.getReadableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COLUMN_MATHSCORE,New);
        db.update(TABLE_NAME, contentvalues, COLUMN_USERNAME + " = ? AND " + COLUMN_MATHSCORE+ " = ?", new String[]{Username,String.valueOf(Old)});
    }
    public void Update_EngScore(String Username,int Old,int New){
        db = this.getReadableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COLUMN_ENGSCORE,New);
        db.update(TABLE_NAME, contentvalues, COLUMN_USERNAME + " = ? AND " + COLUMN_ENGSCORE+ " = ?", new String[]{Username,String.valueOf(Old)});
    }

    public void GetRank(int Type,TextView Rank,TextView Name,TextView Score,String Uname){
        db = this.getReadableDatabase();
        Cursor cr;
        if(Type==5)
            cr=db.query(false,TABLE_NAME,null,null,null,null,null,COLUMN_MATHSCORE+" DESC",null);
        else
            cr=db.query(false,TABLE_NAME,null,null,null,null,null,COLUMN_ENGSCORE+" DESC",null);
        Rank.setText("");
        Name.setText("");
        Score.setText("");
        Rank.setSingleLine(false);
        Name.setSingleLine(false);
        Score.setSingleLine(false);
        int ranking=1;
        while (cr.moveToNext()){
            if(cr.getString(1).equals(Uname))
            {
                String sourceString ="<b>"+ranking+"</b>";
                Rank.append(Html.fromHtml(sourceString));
                Rank.append("\n");
                sourceString ="<b>"+cr.getString(1)+"</b>";
                Name.append(Html.fromHtml(sourceString));
                Name.append("\n");
                sourceString ="<b>"+cr.getString(Type)+"</b>";
                Score.append(Html.fromHtml(sourceString));
                Score.append("\n");
                ranking++;
                continue;
            }
            else{
                Rank.append(ranking+"\n");
                Name.append(cr.getString(1)+"\n");
                Score.append(cr.getString(Type)+"\n");
                ranking++;
        }
        }
        cr.close();
        db.close();

    }
    public String GetColumn (String uname,String Column_name){
        db = this.getReadableDatabase();
        String Result="Not Found" ,a;
        String Query="select * from "+ TABLE_NAME;
        Cursor cr = db.rawQuery(Query ,null  );
        switch (Column_name){
            case "Username":
                if (cr.moveToFirst())
                {
                    do {
                        a = cr.getString(1);
                        if(a.equals(uname))
                        {
                            Result = cr.getString(1);
                            break;
                        }
                    }
                    while(cr.moveToNext());

                }
                break;
            case "Email":
                if (cr.moveToFirst())
                {
                    do {
                        a = cr.getString(1);
                        if(a.equals(uname))
                        {
                            Result = cr.getString(2);
                            break;
                        }
                    }
                    while(cr.moveToNext());

                }
                break;
            case "ProfilePic":
                if (cr.moveToFirst())
                {
                    do {
                        a = cr.getString(1);
                        if(a.equals(uname))
                        {
                            Result = cr.getString(4);
                            break;
                        }
                    }
                    while(cr.moveToNext());

                }
                break;
            case "MathScore":
                if (cr.moveToFirst())
                {
                    do {
                        a = cr.getString(1);
                        if(a.equals(uname))
                        {
                            Result = String.valueOf(cr.getInt(5));
                            break;
                        }
                    }
                    while(cr.moveToNext());

                }
                break;
            case "EngScore":
                if (cr.moveToFirst())
                {
                    do {
                        a = cr.getString(1);
                        if(a.equals(uname))
                        {
                            Result = String.valueOf(cr.getInt(6));
                            break;
                        }
                    }
                    while(cr.moveToNext());

                }
                break;
        }
        return Result;
    }
    public String searchPass(String uname) {
        db = this.getReadableDatabase();
        String q = "select USERNAME, PASS from "+TABLE_NAME;
        Cursor cur = db.rawQuery(q, null);
        String a, b;
        b = "not found";
        if (cur.moveToFirst())
        {
            do {
                a = cur.getString(0);
                if(a.equals(uname))
                {
                    b = cur.getString(1);
                    break;
                }
            }
            while(cur.moveToNext());

        }
        return b ;
    }
    public boolean UserNameExist(String uname) {
        db = this.getReadableDatabase();
        String q = "select USERNAME from "+TABLE_NAME;
        Cursor cur = db.rawQuery(q, null);
        String a;
        boolean b;
        b = false;
        if (cur.moveToFirst())
        {
            do {
                a = cur.getString(0);
                if(a.equals(uname))
                {
                    b=true;
                    break;
                }
            }
            while(cur.moveToNext());

        }
        return b ;
    }
    public boolean EmailExist(String email) {
        db = this.getReadableDatabase();
        String q = "select "+COLUMN_EMAIL+" from "+TABLE_NAME;
        Cursor cur = db.rawQuery(q, null);
        String a;
        boolean b;
        b = false;
        if (cur.moveToFirst())
        {
            do {
                a = cur.getString(0);
                if(a.equals(email))
                {
                    b=true;
                    break;
                }
            }
            while(cur.moveToNext());

        }
        return b ;
    }

}

