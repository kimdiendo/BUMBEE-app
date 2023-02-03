package com.example.bumbee.activities.TestVocab;

import com.example.bumbee.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TestResult extends AppCompatActivity {
    private String name;
    private int score;
    private int num;

    private TextView txtTopic;
    private TextView txtScore;
    private Button btnTest;
    private Button btnLearn;
    private ArrayList<String> ques = new ArrayList<>();
    private ArrayList<String> ans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Intent intent = getIntent();
        ques = intent.getStringArrayListExtra("ques");
        ans = intent.getStringArrayListExtra("ans");
        name = intent.getStringExtra("topic");
        score = intent.getIntExtra("score", 0);
        num = intent.getIntExtra("num", 0);

        txtTopic = (TextView) findViewById(R.id.txtTopicResult);
        txtScore = (TextView) findViewById(R.id.txtScoreResult);
        btnTest = (Button) findViewById(R.id.btnBackToTest);
        btnLearn = (Button) findViewById(R.id.btnBackToWord);

        txtTopic.setText("Topic:  " + name);
        txtScore.setText("Score:  " + score + "/" + num);

        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(a);
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), QuickTest.class);
                b.putStringArrayListExtra("ques" , ques);
                b.putStringArrayListExtra("ans", ans);
                b.putExtra("name" , name);
                startActivity(b);
            }
        });
    }
}