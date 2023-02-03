package com.example.bumbee.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.activities.utilities.PreferenceManager;
import com.example.bumbee.databinding.ActivityIntroductionBinding;

public class introduction extends AppCompatActivity {
    private ActivityIntroductionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN))
        {
            Intent i = new Intent(getApplicationContext() ,MainActivity.class);
            startActivity(i);
            finish();
        }else
        {
            binding = ActivityIntroductionBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setOnClickSignUp();
            setOnClickLogIn();
            setOnMainPage();
        }
    }
    protected void setOnClickSignUp()
    {
        binding.button.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
    }
    protected void setOnClickLogIn()
    {
        binding.button2.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),SignInActivity.class)));
    }
    protected void setOnMainPage()
    {
        binding.skipForNo.setOnClickListener(v->startActivity(new Intent(getApplicationContext() ,Main_Page.class)));
    }
}
