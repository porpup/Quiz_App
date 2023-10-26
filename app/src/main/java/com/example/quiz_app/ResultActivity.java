package com.example.quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultActivity extends AppCompatActivity {

  private TextView userNameTextView, scoreTextView, bestScoreTextView;
  private Button retryButton, btnLogout;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);

    // Initialize views
    userNameTextView = findViewById(R.id.userNameTextView);
    scoreTextView = findViewById(R.id.scoreTextView);
    bestScoreTextView = findViewById(R.id.bestScoreTextView);
    retryButton = findViewById(R.id.retryButton);
    btnLogout = findViewById(R.id.btnLogoutResult);

    // Retrieve username from SharedPreferences
    SharedPreferences sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);
    String user = sharedPreferences.getString("username", "User"); // Default to "User" if username not found

    // Set the greeting message with the username
    userNameTextView.setText(user);
    userNameTextView.setTextColor(Color.GREEN);

    // Get the user's current score from the Intent
    int score = getIntent().getIntExtra("SCORE", 0);

    // Display the user's score
    SpannableStringBuilder scoreBuilder = new SpannableStringBuilder();
    scoreBuilder.append("Your Score: ");
    int start = scoreBuilder.length();
    scoreBuilder.append(score + "pts");
    int end = scoreBuilder.length();
    scoreBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow)), start, end, 0);
    scoreTextView.setText(scoreBuilder);

    // Retrieve and display the best score
    int highScore = sharedPreferences.getInt("highScore", 0);
    SpannableStringBuilder bestScoreBuilder = new SpannableStringBuilder();
    bestScoreBuilder.append("High Score: ");
    int start2 = bestScoreBuilder.length();
    bestScoreBuilder.append(highScore + "pts");
    int end2 = bestScoreBuilder.length();
    bestScoreBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), start2, end2, 0);
    bestScoreTextView.setText(bestScoreBuilder);

    // Check if the current score beats the best score
    if (score > highScore) {
      highScore = score; // Update the best score
      bestScoreTextView.setText("High Score: " + highScore + "pts");

      // Save the new best score in SharedPreferences
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt("highScore", highScore);
      editor.apply();

      // Update the high score in the Realtime Database
      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user);
      databaseReference.child("highScore").setValue(highScore);
    }

    // Set up retry button
    retryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ResultActivity.this, ChooseQuiz.class);
        startActivity(intent);
        finish();
      }
    });

    // Set up logout button
    btnLogout.setOnClickListener(view -> {
      // Clear SharedPreferences data when logging out
      clearSharedPreferences();

      Intent logoutIntent = new Intent(ResultActivity.this, Login.class);
      startActivity(logoutIntent);
    });
  }

  // Function to clear SharedPreferences data
  private void clearSharedPreferences() {
    SharedPreferences sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
  }
}
