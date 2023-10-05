package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText username, email, password, confirmPassword;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.txtUsername);
        email = findViewById(R.id.txtEmail2);
        password = findViewById(R.id.txtPassword2);
        confirmPassword = findViewById(R.id.txtRepeatPassword);
        registerBtn = findViewById(R.id.btnRegister);
        firebaseAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(view -> {
            registerUser();
        });

    }

    private void registerUser() {
        String username = this.username.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String confirmPassword = this.confirmPassword.getText().toString();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All the fields should be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 8) {
            Toast.makeText(this, "Password should be at least 8 characters long!", Toast.LENGTH_SHORT).show();
            this.password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please provide a valid email!", Toast.LENGTH_SHORT).show();
            this.email.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}