package com.example.quiz_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
  private EditText usernameLogin, passwordLogin;
  Button loginBtn;
  TextView register;
  private DatabaseReference mDatabaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    usernameLogin = findViewById(R.id.txtUsernameLogin);
    passwordLogin = findViewById(R.id.txtPasswordLogin);
    loginBtn = findViewById(R.id.btnLogin);
    register = findViewById(R.id.txtRegister);

    mDatabaseReference = FirebaseDatabase.getInstance().getReference("admin");

    register.setOnClickListener(view -> {
      Intent intent = new Intent(Login.this, Register.class);
      startActivity(intent);
      finish();
    });

    loginBtn.setOnClickListener((View.OnClickListener) v -> {
      String username = usernameLogin.getText().toString().trim();
      if ("pargol".equals(username)) {
        // If the username is "pargol", call validateAdminCredentials()
        validateAdminCredentials();
      } else {
        // If it's any other username, call loginUser()
        loginUser();
      }
    });
  }





  public void loginUser() {
    String username = usernameLogin.getText().toString().trim();
    String userPassword = passwordLogin.getText().toString().trim();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    Query checkUserData = reference.orderByChild("username").equalTo(username);

    checkUserData.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
          String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
          assert passwordFromDB != null;
          if (passwordFromDB.equals(userPassword)) {
            String usernameDB = snapshot.child(username).child("username").getValue(String.class);
            String emailDB = snapshot.child(username).child("email").getValue(String.class);
            String passwordDB = snapshot.child(username).child("password").getValue(String.class);

            Intent intent = new Intent(Login.this, ChooseQuiz.class);
            intent.putExtra("username", usernameDB);
            intent.putExtra("email", emailDB);
            intent.putExtra("name", passwordDB);

            startActivity(intent);
            finish();
          } else {
            Toast.makeText(Login.this, "Wrong password!", Toast.LENGTH_SHORT).show();
          }
        } else {
          Toast.makeText(Login.this, "User does not exist!", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });

  }

  private void validateAdminCredentials() {
    final String inputUsername = usernameLogin.getText().toString().trim();
    final String inputPassword = passwordLogin.getText().toString().trim();

    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String adminUsername = dataSnapshot.child("username").getValue(String.class);
        String storedHashedPassword = dataSnapshot.child("password").getValue(String.class);

        if (inputUsername.equals(adminUsername) && inputPassword.equals(storedHashedPassword)) {
          Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();

          Intent intent = new Intent(Login.this, AdminDashboardActivity.class);
          startActivity(intent);
          finish();
          clearFields();
        } else {
          Toast.makeText(Login.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(Login.this, "Error checking credentials!", Toast.LENGTH_SHORT).show();
      }
    });
  }

    private void clearFields() {
      usernameLogin.setText("");
      passwordLogin.setText("");
    }

}
