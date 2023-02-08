package com.example.git_star;

import java.io.Serializable;

class GitHubRepoModel {
     private String repo_id, repo_owner, repo_name, repo_visibility, repo_desc, repo_url, repo_avatar;

     public GitHubRepoModel(String repo_id, String repo_owner, String repo_name, String repo_visibility, String repo_desc, String repo_url, String repo_avatar) {
         this.repo_id = repo_id;
         this.repo_owner = repo_owner;
         this.repo_name = repo_name;
         this.repo_visibility = repo_visibility;
         this.repo_desc = repo_desc;
         this.repo_url = repo_url;
         this.repo_avatar=repo_avatar;

     }

     public GitHubRepoModel() {

     }

    public String getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(String repo_id) {
        this.repo_id = repo_id;
    }

    public String getRepo_avatar() {
         return repo_avatar;
     }

     public void setRepo_avatar(String repo_avatar) {
         this.repo_avatar = repo_avatar;
     }

     public String getRepo_owner() {
         return repo_owner;
     }

     public void setRepo_owner(String repo_owner) {
         this.repo_owner = repo_owner;
     }

     public String getRepo_name() {
         return repo_name;
     }

     public void setRepo_name(String repo_name) {
         this.repo_name = repo_name;
     }

     public String getRepo_visibility() {
         return repo_visibility;
     }

     public void setRepo_visibility(String repo_visibility) {
         this.repo_visibility = repo_visibility;
     }

     public String getRepo_desc() {
         return repo_desc;
     }

     public void setRepo_desc(String repo_desc) {
         this.repo_desc = repo_desc;
     }

     public String getRepo_url() {
         return repo_url;
     }

     public void setRepo_url(String repo_url) {
         this.repo_url = repo_url;
     }
 }
