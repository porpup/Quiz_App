package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdminDashboardActivity extends AppCompatActivity {
  private EditText questionEditText, optionAEditText, optionBEditText, optionCEditText, optionDEditText;
  private Spinner correctAnswerSpinner, categorySpinner;
  private DatabaseReference mDatabaseReference;
  Button addNewQuestionButton, btnLogout, btnUsers;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_dashboard);

    questionEditText = findViewById(R.id.questionEditText);
    optionAEditText = findViewById(R.id.optionAEditText);
    optionBEditText = findViewById(R.id.optionBEditText);
    optionCEditText = findViewById(R.id.optionCEditText);
    optionDEditText = findViewById(R.id.optionDEditText);
    correctAnswerSpinner = findViewById(R.id.correctAnswerSpinner);
    categorySpinner = findViewById(R.id.categorySpinner);
    addNewQuestionButton = findViewById(R.id.addNewQuestionButton);
    btnLogout = findViewById(R.id.btnLogoutAdminDashboard);
    btnUsers = findViewById(R.id.btnUsers);

    mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    // Modify the correctAnswerSpinner
    String[] correctAnswers = {"Option A", "Option B", "Option C", "Option D"};
    ArrayAdapter<String> correctAnswerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, correctAnswers);
    correctAnswerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    correctAnswerSpinner.setAdapter(correctAnswerAdapter);

    addNewQuestionButton.setOnClickListener(v -> {
      String question = questionEditText.getText().toString();
      String optionA = optionAEditText.getText().toString();
      String optionB = optionBEditText.getText().toString();
      String optionC = optionCEditText.getText().toString();
      String optionD = optionDEditText.getText().toString();
      String correctAnswer = correctAnswerSpinner.getSelectedItem().toString();
      String category = categorySpinner.getSelectedItem().toString();

      // Generate the question number
      final int[] questionNumber = {6};  // Start with 6
      DatabaseReference categoryRef = mDatabaseReference.child(category + "Questions");

      // Find the last question number in the category
      categoryRef.get().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          DataSnapshot snapshot = task.getResult();
          if (snapshot.exists()) {
            for (DataSnapshot child : snapshot.getChildren()) {
              // Parse the question key to extract the number
              String questionKey = child.getKey();
              if (questionKey != null) {
                try {
                  int parsedQuestionNumber = Integer.parseInt(questionKey.substring(1));
                  if (parsedQuestionNumber >= questionNumber[0]) {
                    questionNumber[0] = parsedQuestionNumber + 1;
                  }
                } catch (NumberFormatException e) {
                  e.printStackTrace();
                }
              }
            }
          }

          // Create the new question with the updated question number
          String questionKey = "Q" + questionNumber[0];
          Question newQuestion = new Question(question, optionA, optionB, optionC, optionD, correctAnswer);

          // Save the new question in the category
          categoryRef.child(questionKey).setValue(newQuestion);

          Toast.makeText(AdminDashboardActivity.this, "Question added successfully!", Toast.LENGTH_SHORT).show();
          clearFields();
        }
      });
    });

    btnLogout.setOnClickListener(view -> {
      Intent logoutIntent = new Intent(AdminDashboardActivity.this, Login.class);
      startActivity(logoutIntent);
    });

    btnUsers.setOnClickListener(view -> {
      Intent usersIntent = new Intent(AdminDashboardActivity.this, AdminUsersActivity.class);
      startActivity(usersIntent);
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    Intent intent = new Intent(this, Login.class);
    startActivity(intent);
    finish();
    return true;
  }

  private void clearFields() {
    questionEditText.setText("");
    optionAEditText.setText("");
    optionBEditText.setText("");
    optionCEditText.setText("");
    optionDEditText.setText("");
  }
}
