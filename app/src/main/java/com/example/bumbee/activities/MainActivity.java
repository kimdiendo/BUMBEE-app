package com.example.bumbee.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.bumbee.activities.Reading.news;
import com.example.bumbee.activities.note.LanguageTranslator;
import com.example.bumbee.activities.note.note;
import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.activities.utilities.PreferenceManager;
import com.example.bumbee.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        getToken();
        setOnClickLogout();
        setOnClickLanguageTranslator();
        setOnClickReadingPractice();
        setOnClickNote();
        setOnClickTestVocab();
    }
    private void showToast(String name)
    {
        Toast.makeText(getApplicationContext(), name  , Toast.LENGTH_SHORT).show();
    }
    private void setOnClickLogout()
    {
        binding.btnLogOut.setOnClickListener(v-> Log_Out());
    }
    private void getToken()
    {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::UpdateToken);
    }
    private void UpdateToken(String token)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(Contants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Contants.KEY_USER_ID));
        documentReference.update(Contants.KEY_FCM_TOKEN , token).addOnSuccessListener(unused -> showToast("Token updated successfully"))
                .addOnFailureListener(e->showToast("Unable to update token"));
    }
    private void Log_Out()
    {
        showToast("Logging out...");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(Contants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Contants.KEY_USER_ID));
        HashMap<String , Object> updates = new HashMap<>();
        updates.put(Contants.KEY_FCM_TOKEN , FieldValue.delete());
        documentReference.update(updates).addOnSuccessListener(unused ->{
            preferenceManager.clear();
            startActivity(new Intent(getApplicationContext(), Main_Page.class));
            finish();
        })
                .addOnFailureListener(e->showToast("Unable to log out"));
    }
    protected void setOnClickLanguageTranslator()
    {
        binding.imagebtnLanguageTranslator.setOnClickListener(v -> startActivity(new Intent(getApplicationContext() , LanguageTranslator.class)));
    }
    protected void setOnClickReadingPractice()
    {
        binding.imagebtnReading.setOnClickListener(v->startActivity(new Intent(getApplicationContext() , news.class)));
    }
    protected  void setOnClickNote()
    {
        binding.imageNote.setOnClickListener(v->startActivity(new Intent(getApplicationContext() , note.class)));
    }
    protected void setOnClickTestVocab()
    {
        binding.imagebtnTestVocab.setOnClickListener(v->startActivity(new Intent(getApplicationContext()  , com.example.bumbee.activities.TestVocab.MainActivity.class)));
    }
}