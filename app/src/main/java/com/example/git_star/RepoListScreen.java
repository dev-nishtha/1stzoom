package com.example.git_star;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepoListScreen extends AppCompatActivity {
    ListView listView;
    GitStarDatabaseHelper myDb;
    private long pressedTime;

    @Override
    public void onBackPressed(){
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bar_but) {
            Intent intent=new Intent(RepoListScreen.this, AddRepository.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list_screen);

        listView=findViewById(R.id.repo_list);
        myDb= new GitStarDatabaseHelper(this);

        List<GitHubRepoModel> favReposList= new ArrayList<>();
        Cursor data= myDb.getListContents();
        if(data.getCount()!=0){
            while (data.moveToNext()){
                GitHubRepoModel repoItem= new GitHubRepoModel();
                repoItem.setRepo_id(data.getString(0));
                repoItem.setRepo_name(data.getString(1));
                repoItem.setRepo_desc(data.getString(2));
                repoItem.setRepo_url(data.getString(3));
                repoItem.setRepo_avatar(data.getString(4));
                repoItem.setRepo_owner(data.getString(5));
                repoItem.setRepo_visibility(data.getString(6));
                favReposList.add(repoItem);
                System.out.println("entered repo list"+favReposList.size());
            }
        }
        else{
            System.out.println("Unable to fetch from database");
        }
        MyListAdapter adapter= new MyListAdapter(RepoListScreen.this, (ArrayList<GitHubRepoModel>) favReposList);
        listView.setAdapter(adapter);
    }
}