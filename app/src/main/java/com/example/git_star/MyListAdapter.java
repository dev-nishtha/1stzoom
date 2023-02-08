package com.example.git_star;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<GitHubRepoModel> {
   private Context context;
   private List<GitHubRepoModel> repoList=new ArrayList<>();

      public MyListAdapter(Context ctx, ArrayList<GitHubRepoModel> list) {
              super(ctx, 0, list);
                context = ctx;
                repoList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null)
            rowView = LayoutInflater.from(context).inflate(R.layout.my_list_item,parent,false);
        GitHubRepoModel currentRepo=repoList.get(position);
        LinearLayout list_tile=rowView.findViewById(R.id.list_tile);
        TextView title_text =  rowView.findViewById(R.id.fav_repo);
        TextView subtitle_text =  rowView.findViewById(R.id.fav_desc);
        ImageView avatar_text = (ImageView) rowView.findViewById(R.id.avatar);
        ImageView share_text = (ImageView) rowView.findViewById(R.id.share);

        title_text.setText(currentRepo.getRepo_name());
        subtitle_text.setText(currentRepo.getRepo_desc());
        Picasso.get().load(currentRepo.getRepo_avatar()).into(avatar_text);
        list_tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(currentRepo.getRepo_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        share_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this repo! ");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, currentRepo.getRepo_url());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        return rowView;
    }
}
