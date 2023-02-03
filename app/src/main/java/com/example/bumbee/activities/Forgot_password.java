package com.example.bumbee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bumbee.activities.utilities.Contants;
import com.example.bumbee.activities.utilities.PreferenceManager;
import com.example.bumbee.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Forgot_password extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    final FirebaseAuth auth = FirebaseAuth.getInstance();
    PreferenceManager preferenceManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        ResetPasswordbysendingEmail();
        Updatepassword();
    }
    private void showToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void update()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Contants.KEY_COLLECTION_USERS).whereEqualTo(Contants.KEY_EMAIL , binding.editEmail.getText().toString()).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot  documentSnapshot = task.getResult().getDocuments().get(0);
                        DocumentReference documentReference = db.collection(Contants.KEY_COLLECTION_USERS).document(documentSnapshot.getId());
                        Map<String , Object> updates = new HashMap<>();
                        updates.put(Contants.KEY_PASSWORD,binding.editTextTextPassword.getText().toString());
                        documentReference.update(updates).addOnSuccessListener(unused ->{
                            showToast("Update password successfully");
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finish();
                        })
                                .addOnFailureListener(e->showToast("Unable to update password"));
                    }
                });
    }
    private void Updatepassword() {
        binding.btnUpdatePass.setOnClickListener(v->update());
    }

    private void ResetPasswordbysendingEmail()
    {
        binding.btnVerify.setOnClickListener(v->SendingPasswordresetEmail());
    }
    private void SendingPasswordresetEmail()
    {
        auth.sendPasswordResetEmail(binding.editEmail.getText().toString()).addOnCompleteListener(task ->  {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"A mail was sent to your email.", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Cannot find entered email", Toast.LENGTH_SHORT).show();
                }
        });
    }
}