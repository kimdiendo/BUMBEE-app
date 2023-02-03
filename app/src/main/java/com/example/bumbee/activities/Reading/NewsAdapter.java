package com.example.bumbee.activities.Reading;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bumbee.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final ArrayList<Articles> articlesArrayList;
    private final Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_about_toeic,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
                Articles articles = articlesArrayList.get(position);
                holder.titleTV.setText(articles.getTitle());
                holder.subtitleTV.setText(articles.getDescription());
                Picasso.get().load(articles.getUrltoimage()).into(holder.imageTV);
                holder.itemView.setOnClickListener(view -> {
                  Intent i =new Intent(context, NewsDetail.class);
                  i.putExtra("title" , articles.getTitle());
                  i.putExtra("description" , articles.getDescription());
                  i.putExtra("content" ,articles.getContent());
                  i.putExtra("image" , articles.getUrltoimage());
                  i.putExtra("url", articles.getUrl());
                  context.startActivity(i);
                });
    }
    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView subtitleTV;
        private final ImageView imageTV;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTV = itemView.findViewById(R.id.title);
            subtitleTV = itemView.findViewById(R.id.subtitle);
            imageTV = itemView.findViewById(R.id.image_news);
        }
    }
}
