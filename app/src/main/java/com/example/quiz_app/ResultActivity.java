package com.example.quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView userNameTextView, scoreTextView;
    private Button retryButton, btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        userNameTextView = findViewById(R.id.userNameTextView); // Assuming you have a TextView in your layout with this ID
        scoreTextView = findViewById(R.id.scoreTextView);
        retryButton = findViewById(R.id.retryButton);
        btnLogout = findViewById(R.id.btnLogoutResult);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "User"); // Default to "User" if username not found

        // Set the greeting message with the username
        userNameTextView.setText(user);
        userNameTextView.setTextColor(Color.GREEN);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreTextView.setText("Your Score: " + score + "pts");

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
