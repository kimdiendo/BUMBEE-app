package com.example.bumbee.activities.TestVocab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bumbee.R;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuickTest extends AppCompatActivity {
    private String name;
    private ArrayList<String> ques;
    private ArrayList<String> ans;
    private int Score = 0;
    private int num = 1;
    private int i = 0;
    private boolean isSuccess;

    private TextView question;
    private EditText answer;
    private Button confirm;
    private Button cancel;
    private TextView score;
    private TextView word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_test);

        question = (TextView) findViewById(R.id.txtQues);
        answer = (EditText) findViewById(R.id.txtAns);
        confirm = (Button) findViewById(R.id.btnConfirm);
        score = (TextView) findViewById(R.id.txtScore);
        word = (TextView) findViewById(R.id.txtWord);
        cancel = (Button) findViewById(R.id.btnCancel);

        ActionBar ac = getSupportActionBar();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ques = intent.getStringArrayListExtra("ques");
        ans = intent.getStringArrayListExtra("ans");

        ac.setTitle("Test: " + name);
        score.setText("Score: " + Score + "/" + ques.size());

        question.setText("Question: " + num);
        word.setText(ques.get(i));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuickTest.this, com.google.android.material.R.style.Theme_AppCompat_DayNight_Dialog);
                builder.setTitle("Do you want to cancel this test?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent b = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(b);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == ques.size() - 2){
                    confirm.setText("Finish");
                }
                isSuccess = true;
                if (answer.getText().toString().compareTo("") == 0){
                    Toast.makeText(QuickTest.this, "You have not typed your answer!", Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }
                if (isSuccess == false){
                    return;
                }
                else{
                    if (answer.getText().toString().compareTo(ans.get(i)) == 0){
                        Score++;
                        score.setText("Score: " + Score + "/" + ques.size());
                        Toast.makeText(QuickTest.this, "CORRECT!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(QuickTest.this, "INCORRECT!\nCorrect Answer: " + ans.get(i), Toast.LENGTH_SHORT).show();
                    }
                    num++;
                    question.setText("Question: " + num);
                    answer.setText("");
                }
                if (i == ques.size() - 1){
                    Intent intent2 = new Intent(getApplicationContext(), TestResult.class);
                    intent2.putExtra("score", Score);
                    intent2.putStringArrayListExtra("ques" ,ques);
                    intent2.putStringArrayListExtra("ans" ,ans);
                    intent2.putExtra("topic", name);
                    intent2.putExtra("num", ques.size());
                    startActivity(intent2);
                }
                else{
                    i++;
                    word.setText(ques.get(i));
                }
            }
        });
    }
}