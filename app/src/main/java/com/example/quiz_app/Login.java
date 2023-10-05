package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button loginBtn;
    private TextView register;
    private FirebaseAuth firebaseAuthAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        loginBtn = findViewById(R.id.btnLogin);
        register = findViewById(R.id.txtRegister);

        register.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        loginBtn.setOnClickListener(view -> {
            loginUser();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuthAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(Login.this, ChooseQuiz.class);
            startActivity(intent);
            finish();
        }
    }

    private void loginUser() {
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter Email and Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuthAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(Login.this, ChooseQuiz.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Login Failed! Enter correct Email and Password", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}