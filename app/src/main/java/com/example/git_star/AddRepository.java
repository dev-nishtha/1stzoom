package com.example.git_star;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AddRepository extends AppCompatActivity  {

    TextInputLayout layoutOne, layoutTwo;
    TextInputEditText editTextOne, editTextTwo;
    String req_username,req_repo;
    Button add;
    GitStarDatabaseHelper myDb;


    public boolean formValidation(TextInputEditText editTextOne, TextInputLayout userLayout, TextInputEditText editTextTwo, TextInputLayout repoLayout){
        req_username=editTextOne.getText().toString();
        req_repo=editTextTwo.getText().toString();


        if(!req_username.isEmpty() && !req_repo.isEmpty()){

            return true;
        }
        else{
            if(req_username.isEmpty()){
                userLayout.setError("Please enter the name of the owner");
            }
            if(req_repo.isEmpty()){
                repoLayout.setError("Please enter the name of the repository");
            }
            return false;
        }


    }

    @Override
    public void  onBackPressed(){
        myDb=new GitStarDatabaseHelper(this);
        Cursor data= myDb.getListContents();
        if(data.getCount()==0)
        {
            Intent intent = new Intent(AddRepository.this, LandingScreen.class);
            startActivity(intent);
        }
        else{

            Intent intent = new Intent(AddRepository.this, RepoListScreen.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context;
        context = getApplicationContext();
        setContentView(R.layout.activity_add_repository);
        getSupportActionBar().hide();
        editTextOne=findViewById(R.id.username);
        editTextTwo=findViewById(R.id.repo);
        layoutOne=findViewById(R.id.user_input);
        layoutTwo=findViewById(R.id.repo_input);
        add= findViewById(R.id.add_btn);
        myDb=new GitStarDatabaseHelper(this);
        final GitHubDataService gitHubDataService= new GitHubDataService(context);


        editTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutOne.setError(null);
            }
        });

        editTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutTwo.setError(null);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formValidation(editTextOne, layoutOne, editTextTwo, layoutTwo)) {
                   gitHubDataService.getRepoDetails(req_username, req_repo, new GitHubDataService.VolleyResponseListener() {
                        @Override
                        public void onResponse(List<GitHubRepoModel> gitHubRepoModels) {
                            for(int i=0;i<gitHubRepoModels.size();i++) {
                                System.out.println("Add repository returned: " + gitHubRepoModels.get(i).getRepo_id());
                                String db_id=gitHubRepoModels.get(i).getRepo_id();
                                String db_repo=gitHubRepoModels.get(i).getRepo_name();
                                String db_desc=gitHubRepoModels.get(i).getRepo_desc();
                                String db_url=gitHubRepoModels.get(i).getRepo_url();
                                String db_avatar=gitHubRepoModels.get(i).getRepo_avatar();
                                String db_owner=gitHubRepoModels.get(i).getRepo_owner();
                                String db_visible=gitHubRepoModels.get(i).getRepo_visibility();
                                boolean idExists=myDb.isPresent(db_id);
                                if(idExists){
                                    Toast errorMsg = Toast.makeText(context, "Repository already added", Toast.LENGTH_SHORT);
                                    View toastView = errorMsg.getView();
                                    toastView.setBackgroundColor(Color.parseColor("#FFFF0000"));
                                    TextView toastText = toastView.findViewById(android.R.id.message);
                                    toastText.setTextColor(Color.parseColor("#FFFFFF"));
                                    errorMsg.setGravity(Gravity.TOP | Gravity.LEFT, 300, 200);
                                    errorMsg.show();
                                    editTextOne.setText("");
                                    editTextTwo.setText("");
                                }
                                else{
                                    boolean inserted = myDb.addData(db_id, db_repo,db_desc,db_url,db_avatar, db_owner, db_visible);
                                    if (inserted) {
                                        System.out.println(db_id + " insertion successful");
                                        editTextOne.setText("");
                                        editTextTwo.setText("");
                                        Intent intent=new Intent(AddRepository.this, RepoListScreen.class);
                                        startActivity(intent);
                                    } else {
                                        System.out.println(db_id + " insertion failed");

                                    }
                                }
                            }

                        }

                        @Override
                        public void onError(String message) {
                            System.out.println(message);

                        }
                    });
                }
            }
        });




    }
}