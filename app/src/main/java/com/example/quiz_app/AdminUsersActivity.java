package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminUsersActivity extends AppCompatActivity {
  Toolbar toolbar;
  ListView userListView;
  FirebaseDatabase database;
  DatabaseReference reference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_admin_users);
    userListView = findViewById(R.id.userListView);

    toolbar = findViewById(R.id.toolbarAdminUsers);
    setSupportActionBar(toolbar);

    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
    getSupportActionBar().setTitle("Back to Quiz Administration");

    // Initialize Firebase Realtime Database
    database = FirebaseDatabase.getInstance();
    reference = database.getReference("users");

    // Call the method to list users
    listAllUsers();
  }





  @Override
  public boolean onSupportNavigateUp() {
    Intent intent = new Intent(this, AdminDashboardActivity.class);
    startActivity(intent);
    finish();
    return true;
  }

  private void listAllUsers() {
    // Create an ArrayList to store User objects
    final ArrayList<Model> userList = new ArrayList<>();

    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
          String username = userSnapshot.child("username").getValue(String.class);
          String email = userSnapshot.child("email").getValue(String.class);
          String password = userSnapshot.child("password").getValue(String.class);
          int highScore = userSnapshot.child("highScore").getValue(Integer.class);
          if (username != null && email != null && password != null) {
            Model userModel = new Model(username, email, password);
            userModel.setHighScore(highScore);
            userList.add(userModel);
          }
        }

        // Create a custom adapter to display the user names, emails, passwords, and high scores in the ListView
        CustomArrayAdapter adapter = new CustomArrayAdapter(AdminUsersActivity.this, R.layout.custom_list_item, userList);
        userListView.setAdapter(adapter);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        // Handle any errors here
      }
    });
  }



}