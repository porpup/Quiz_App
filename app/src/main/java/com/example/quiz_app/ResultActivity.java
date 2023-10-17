package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView userNameTextView, scoreTextView;
    private Button retryButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        userNameTextView = findViewById(R.id.userNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        retryButton = findViewById(R.id.retryButton);

        String username = getIntent().getStringExtra("USERNAME");
        int score = getIntent().getIntExtra("SCORE", 0);

        userNameTextView.setText("Username: " + username);
        scoreTextView.setText("Your Score: " + score);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ChooseQuiz.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
