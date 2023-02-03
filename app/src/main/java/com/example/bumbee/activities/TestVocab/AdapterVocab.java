package com.example.bumbee.activities.TestVocab;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bumbee.R;
import com.example.bumbee.activities.Reading.NewsAdapter;
import com.example.bumbee.activities.Reading.NewsDetail;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterVocab extends RecyclerView.Adapter<AdapterVocab.ViewHolder> {
    private final ArrayList<Topic> topicArrayList;
    private final Context context;

    public AdapterVocab(ArrayList<Topic> topicArrayList, Context context) {
        this.topicArrayList = topicArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterVocab.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_topic,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Topic topic = topicArrayList.get(position);
        holder.topicName.setText(topic.getTopicName());
        int imageId = this.getMipmapResIdByName(topic.getTopicImage());
        holder.topicImage.setImageResource(imageId);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity2.class);
            intent.putExtra("name" , topic.getTopicName());
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView topicName;
        private final ImageView topicImage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            topicName = itemView.findViewById(R.id.name_topic);
            topicImage = itemView.findViewById(R.id.image_topic);
        }
    }
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
}
