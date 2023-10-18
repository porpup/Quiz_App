package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    Toolbar adminToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminToolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(adminToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back to Login");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("admin");

        usernameEditText = findViewById(R.id.txtUsernameLogin);
        passwordEditText = findViewById(R.id.txtPasswordLogin);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAdminCredentials();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void validateAdminCredentials() {
        final String inputUsername = usernameEditText.getText().toString().trim();
        final String inputPassword = passwordEditText.getText().toString().trim();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String adminUsername = dataSnapshot.child("username").getValue(String.class);
                String storedHashedPassword = dataSnapshot.child("password").getValue(String.class);

                if (inputUsername.equals(adminUsername) && inputPassword.equals(storedHashedPassword)) {
                    Toast.makeText(AdminLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                    clearFields();
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminLoginActivity.this, "Error checking credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

}
