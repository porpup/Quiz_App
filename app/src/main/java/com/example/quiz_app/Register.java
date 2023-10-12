package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
  private EditText username, email, password, confirmPassword;
  Button registerBtn;
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

    registerBtn.setOnClickListener(view -> registerUser());

  }

  public void registerUser() {
    database = FirebaseDatabase.getInstance();
    reference = database.getReference("users"); // Name of my table

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
      Model model = new Model(username, email, password);
      reference.child(username).setValue(model); // Important!
      Toast.makeText(Register.this, "Successful Registry", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(Register.this, Login.class);
      intent.putExtra("username", username);
      intent.putExtra("password", password);
      startActivity(intent);
      finish();
    }

  }
}