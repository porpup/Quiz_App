package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {
  private EditText username, email, password, confirmPassword;
  Button registerBtn;
  Toolbar toolbar;
  FirebaseDatabase database;
  DatabaseReference reference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    username = findViewById(R.id.txtUsernameRegister);
    email = findViewById(R.id.txtEmailRegister);
    password = findViewById(R.id.txtPasswordRegister);
    confirmPassword = findViewById(R.id.txtRepeatPassword);
    registerBtn = findViewById(R.id.btnRegister);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
    getSupportActionBar().setTitle("Back to Login");

    registerBtn.setOnClickListener(view -> registerUser());
  }

  @Override
  public boolean onSupportNavigateUp() {
    Intent intent = new Intent(this, Login.class);
    startActivity(intent);
    finish();
    return true;
  }

  public void registerUser() {
    database = FirebaseDatabase.getInstance();
    reference = database.getReference("users");

    String username = this.username.getText().toString().trim();
    String email = this.email.getText().toString().trim();
    String password = this.password.getText().toString().trim();
    String confirmPassword = this.confirmPassword.getText().toString().trim();

    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
      Toast.makeText(this, "All the fields should be filled!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!password.equals(confirmPassword)) {
      Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (password.length() < 8) {
      Toast.makeText(this, "Password should be at least 8 characters long!", Toast.LENGTH_SHORT).show();
      this.password.requestFocus();
      return;
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      Toast.makeText(this, "Please provide a valid email!", Toast.LENGTH_SHORT).show();
      this.email.requestFocus();
    } else {
      // Create a user model with an initial high score of 0
      Model model = new Model(username, email, password);
      model.setHighScore(0); // Set the initial high score

      // Save the user to the database
      reference.child(username).setValue(model);

      Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(Register.this, Login.class);
      intent.putExtra("username", username);
      intent.putExtra("password", password);
      startActivity(intent);
      finish();
    }
  }

}
