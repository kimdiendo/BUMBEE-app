package com.example.bumbee.activities.TestVocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bumbee.R;
import com.example.bumbee.activities.Reading.NewsAdapter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Topic> topicArrayList;
    private AdapterVocab adapterVocab;
    public String k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView topic = findViewById(R.id.idTopic);
        topicArrayList = new ArrayList<>();

        adapterVocab = new AdapterVocab(topicArrayList, this);
        topic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        topic.setAdapter(adapterVocab);

        getListData();

        adapterVocab.notifyDataSetChanged();
    }

    private  void getListData() {
        topicArrayList.add(new Topic("animal", "Animal"));
        topicArrayList.add(new Topic("color", "Color"));
        topicArrayList.add(new Topic("family", "Family"));
        topicArrayList.add(new Topic("jobs", "Jobs"));
        topicArrayList.add(new Topic("sport", "Sport"));
        topicArrayList.add(new Topic("subjects", "Subjects"));
        topicArrayList.add(new Topic("transport", "Transport"));
    }

}