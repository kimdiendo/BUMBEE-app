package com.example.bumbee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.activities.utilities.PreferenceManager;
import com.example.bumbee.databinding.ActivitySignInBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;
    private String email ="";
    private Contants contants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN))
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        setContentView(binding.getRoot());
        setListenners();
    }
    private void setListenners()
    {
        binding.forgotPass.setOnClickListener(v->startActivity((new Intent(getApplicationContext(),Forgot_password.class))));
        binding.donTHave.setOnClickListener(v->startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.btnLogIn.setOnClickListener(v->{
            if(isValidInformation())
            {
                LogIn();
            }
        });
    }
    private void LogIn()
    {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Contants.KEY_COLLECTION_USERS).whereEqualTo(Contants.KEY_EMAIL, binding.email.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null &&task.getResult().getDocuments().size()>0)
                    {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        if (documentSnapshot.getString(Contants.KEY_PASSWORD).equals(binding.password.getText().toString()))
                        {
                            email = binding.email.getText().toString();
                            contants = new Contants(email);
                            preferenceManager.putBoolean(Contants.KEY_IS_SIGNED_IN ,true);
                            preferenceManager.PutString(Contants.KEY_USER_ID  , documentSnapshot.getId());
                            preferenceManager.PutString(Contants.KEY_EMAIL , binding.email.getText().toString());
                            preferenceManager.PutString(Contants.KEY_PASSWORD , binding.password.getText().toString());
                            Intent i = new Intent(getApplicationContext() ,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }else
                        {
                            {
                                loading(false);
                                showToast("Email or password may be incorrect");
                            }
                        }
                    }else
                    {
                        loading(false);
                        showToast("Email has not existed in any account yet");
                    }
                });
    }
    private void showToast(String notification)
    {
        Toast.makeText(getApplicationContext(),notification, Toast.LENGTH_SHORT).show();
    }
    private Boolean isValidInformation()
    {
        if(binding.email.getText().toString().trim().isEmpty())
        {
            showToast("Enter your username");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches())
        {
            showToast("Please enter your valid email");
            return false;
        }
        else if(binding.password.getText().toString().trim().isEmpty())
        {
            showToast("Enter your password");
            return false;
        }
        return true;
    }
    private void loading(Boolean isLoading)
    {
        if(isLoading)
        {
            binding.progressBar2.setVisibility(View.VISIBLE);
            binding.btnLogIn.setVisibility(View.INVISIBLE);
        }else
        {
            binding.progressBar2.setVisibility(View.INVISIBLE);
            binding.btnLogIn.setVisibility(View.VISIBLE);
        }
    }
}