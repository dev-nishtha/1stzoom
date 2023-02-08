package com.example.git_star;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class GitHubDataService {
    public static final String QUERY_FOR_REPO_DATA = "https://api.github.com/repos/";
    Context context;
    String res_id, res_username, res_repo, res_url, res_description, res_visibility, res_avatar;
    List<GitHubRepoModel> addedRepos=new ArrayList<>();

    public GitHubDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onResponse(List<GitHubRepoModel> gitHubRepoModels);
        void onError(String message);
    }
    public void getRepoDetails(String req_username, String req_repo, VolleyResponseListener volleyResponseListener) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = QUERY_FOR_REPO_DATA + req_username + "/" + req_repo;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            GitHubRepoModel repoDetails= new GitHubRepoModel();
                            //id
                            res_id=response.get("id").toString();
                            repoDetails.setRepo_id(res_id);
                            //username
                            res_username = response.getJSONObject("owner").getString("login");
                            repoDetails.setRepo_owner(res_username);
                            System.out.println("Username " + res_username);
                            //repo
                            res_repo = response.getString("name");
                            System.out.println("Repository " + res_repo);
                            repoDetails.setRepo_name(res_repo);
                            //visibility
                            res_visibility = response.getString("visibility");
                            repoDetails.setRepo_visibility(res_visibility);
                            System.out.println("Visibility " + res_visibility);
                            //description
                            res_description = response.getString("description");
                            repoDetails.setRepo_desc(res_description);
                            //url
                            res_url = response.getString("html_url");
                            repoDetails.setRepo_url(res_url);
                            //avatar url
                            res_avatar = response.getJSONObject("owner").getString("avatar_url");
                            repoDetails.setRepo_avatar(res_avatar);
                            System.out.println("Username " + res_avatar);
                            addedRepos.add(repoDetails);
                            volleyResponseListener.onResponse(addedRepos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error.toString());
                volleyResponseListener.onError(error.toString());
                if (error.toString().equals("com.android.volley.ClientError")) {
                    System.out.println("Inside Error");
                    Toast errorMsg = Toast.makeText(context, "Repository not found", Toast.LENGTH_SHORT);
                    View toastView = errorMsg.getView();
                    toastView.setBackgroundColor(Color.parseColor("#FFFF0000"));
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(Color.parseColor("#FFFFFF"));
                    errorMsg.setGravity(Gravity.TOP | Gravity.LEFT, 300, 200);
                    errorMsg.show();
                }
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
