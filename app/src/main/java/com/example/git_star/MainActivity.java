package com.example.git_star;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
GitStarDatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        myDb=new GitStarDatabaseHelper(this);
        Cursor data= myDb.getListContents();
       new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(data.getCount()==0)
                {
                    Intent intent = new Intent(MainActivity.this, LandingScreen.class);
                    startActivity(intent);
                }
                else{

                    Intent intent = new Intent(MainActivity.this, RepoListScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);

    }
}