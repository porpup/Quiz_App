package com.example.quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseQuiz extends AppCompatActivity {
    TextView username;
    Button btnScienceQuiz, btnHistoryQuiz, btnMoviesQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz);

        username = findViewById(R.id.txtUsernameChooseQuiz);
        btnScienceQuiz = findViewById(R.id.btnScienceQuiz);
        btnHistoryQuiz = findViewById(R.id.btnHistoryQuiz);
        btnMoviesQuiz = findViewById(R.id.btnMoviesQuiz);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");

        if(user == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);
            user = sharedPreferences.getString("username", "User"); // Default to "User" if username not found
        } else {
            // Save the username in shared preferences since it's not null
            SharedPreferences sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user);
            editor.apply();
        }

        // Set the greeting message with the username
        username.setText("Hi " + user + "!");

        btnScienceQuiz.setOnClickListener(view -> {
            Intent scienceIntent = new Intent(ChooseQuiz.this, ScienceQuizActivity.class);
            startActivity(scienceIntent);
        });

        btnHistoryQuiz.setOnClickListener(view -> {
            Intent historyIntent = new Intent(ChooseQuiz.this, HistoryQuizActivity.class);
            startActivity(historyIntent);
        });

        btnMoviesQuiz.setOnClickListener(view -> {
            Intent moviesIntent = new Intent(ChooseQuiz.this, MoviesQuizActivity.class);
            startActivity(moviesIntent);
        });
    }
}
