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

    userNameTextView = findViewById(R.id.userNameTextView); // Assuming you have a TextView in your layout with this ID
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

    SpannableStringBuilder builder = new SpannableStringBuilder();
    int score = getIntent().getIntExtra("SCORE", 0);
    builder.append("Your Score: ");
    int start = builder.length();
    builder.append(score + "pts");
    int end = builder.length();
    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow)), start, end, 0);
    scoreTextView.setText(builder);

    // Retrieve and display the best score
    SpannableStringBuilder builder2 = new SpannableStringBuilder();
    int highScore = sharedPreferences.getInt("highScore", 0);
    builder2.append("High Score: ");
    int start2 = builder2.length();
    builder2.append(highScore + "pts");
    int end2 = builder2.length();
    builder2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), start2, end2, 0);
    bestScoreTextView.setText(builder2);

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

    retryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ResultActivity.this, ChooseQuiz.class);
        startActivity(intent);
        finish();
      }
    });

    btnLogout.setOnClickListener(view -> {
      Intent logoutIntent = new Intent(ResultActivity.this, Login.class);
      startActivity(logoutIntent);
    });
  }
}
