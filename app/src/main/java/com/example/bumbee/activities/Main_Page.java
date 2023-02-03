package com.example.bumbee.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.bumbee.activities.Reading.news;
import com.example.bumbee.activities.TestVocab.MainActivity;
import com.example.bumbee.activities.TestVocab.TestResult;
import com.example.bumbee.activities.note.LanguageTranslator;
import com.example.bumbee.activities.note.note;
import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.databinding.ActivityMainPageBinding;
import com.example.bumbee.activities.utilities.PreferenceManager;
public class Main_Page extends AppCompatActivity {
    private ActivityMainPageBinding binding;
    private PreferenceManager preferenceManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        setOnClickSignUp();
        setOnClickLogIn();
        setOnClickLanguageTranslator();
        setOnClickReadingPractice();
        setOnClickNote();
        setOnClickTestVocab();

    }
    protected void setOnClickSignUp()
    {
        binding.btnSignUp.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
    }
    protected void setOnClickLogIn()
    {
        binding.btnLogIn.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),SignInActivity.class)));
    }
    protected void setOnClickLanguageTranslator()
    {
       binding.imagebtnLanguageTranslator.setOnClickListener(v -> startActivity(new Intent(getApplicationContext() , LanguageTranslator.class)));
    }
    protected void setOnClickReadingPractice()
    {
        binding.imagebtnReading.setOnClickListener(v->Check());
    }
    protected void setOnClickNote()
    {
        binding.imageNote.setOnClickListener(v-> startActivity(new Intent (getApplicationContext() , note.class)));
    }
    protected void Check()
    {
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN))
        {
            Intent i = new Intent(getApplicationContext() , news.class);
            startActivity(i);
        }else
        {
            Toast.makeText(this , "Please log in or sign up to use this function" , Toast.LENGTH_SHORT).show();
        }

    }
    protected  void setOnClickTestVocab()
    {
        binding.imagebtnTestVocab.setOnClickListener(v->Checkk());
    }
    protected void Checkk()
    {
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN))
        {
            startActivity(new Intent(getApplicationContext() , MainActivity.class));
        }else
        {
            Toast.makeText(this , "Please log in or sign up to use this function" ,Toast.LENGTH_SHORT).show();
        }
    }

}