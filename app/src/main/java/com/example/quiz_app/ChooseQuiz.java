package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChooseQuiz extends AppCompatActivity {
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz);

        username = findViewById(R.id.txtUsernameChooseQuiz);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");

        // Display the username in the TextView
        username.setText(user);
    }
}
