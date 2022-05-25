package com.example.bumbee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.activities.utilities.PreferenceManager;
import com.example.bumbee.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String email ="";
    private Contants contants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN))
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        setListeners();
    }

    private void setListeners() {
        binding.alreadyHav.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(v->{
            if (isValidSignupDetails())
            {
                SignUp();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void SignUp()
    {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Contants.KEY_COLLECTION_USERS).whereEqualTo(Contants.KEY_EMAIL , binding.email.getText().toString()).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null &&task.getResult().getDocuments().size()>0) {
                        loading(false);
                        Toast.makeText(getApplicationContext(), "Unable to sign up because your email have already existed", Toast.LENGTH_LONG).show();
                    }else
                    {
                        Map<String , Object> user = new HashMap<>();
                        user.put(Contants.KEY_USERNAME , binding.userName.getText().toString());
                        user.put(Contants.KEY_EMAIL ,binding.email.getText().toString());
                        user.put(Contants.KEY_PASSWORD ,binding.password.getText().toString());
                        email = binding.email.getText().toString();
                        contants = new Contants(email);
                        db.collection(Contants.KEY_COLLECTION_USERS).add(user).addOnSuccessListener(documentReference ->  {
                            preferenceManager.putBoolean(Contants.KEY_IS_SIGNED_IN , true);
                            preferenceManager.PutString(Contants.KEY_USER_ID , documentReference.getId());
                            preferenceManager.PutString(Contants.KEY_USERNAME ,binding.userName.getText().toString());
                            preferenceManager.PutString(Contants.KEY_EMAIL ,binding.email.getText().toString());
                            preferenceManager.PutString(Contants.KEY_PASSWORD ,binding.password.getText().toString());
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            auth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString());
                            Intent i = new Intent(getApplicationContext() ,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        });
                    }

                });
    }
    private Boolean checkPassword(String password)
    {
        if(password.length() >= 6)
        {
            return true;
        }else
        {
            return false;
        }
    }
    private Boolean isValidSignupDetails()
    {
        if (binding.userName.getText().toString().trim().isEmpty()) {
            showToast("Please enter your username");
            return false;
        } else if (binding.email.getText().toString().trim().isEmpty()) {
            showToast("Please enter your email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches())
        {
            showToast("Please enter your valid email");
            return false;
        }
        else if (binding.password.getText().toString().trim().isEmpty())
        {
            showToast("Please enter your password that has 6 least characters");
            return false;

        }else if (checkPassword(binding.password.getText().toString().trim()) ==false)
        {
            showToast("Please enter your password that has 6 least characters");
            return false;
        } else if (binding.rePassword.getText().toString().trim().isEmpty()) {
            showToast("Please confirm your password");
            return false;
        } else if (!binding.password.getText().toString().equals(binding.rePassword.getText().toString())) {
            showToast("Re-password and password must be same");
            return false;
        }
        return true;
    }
    private void loading(Boolean isLoading)
    {
        if(isLoading)
        {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.INVISIBLE);
        }else
        {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
        }
    }
}