package com.example.quiz_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScienceQuizActivity extends AppCompatActivity {

    private TextView questionText;
    private Button optionA, optionB, optionC, optionD, btnNext;
    private String correctAnswer;
    private int currentQuestionIndex = 0;  // Used to keep track of current question

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_quiz);

        questionText = findViewById(R.id.questionText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        btnNext = findViewById(R.id.btnNext);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ScienceQuestions");

        loadNextQuestion();

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionA");
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionB");
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionC");
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionD");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });

        // Initially, the Next button should be disabled until an answer is selected
        btnNext.setEnabled(false);
    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        String questionKey = "Q" + currentQuestionIndex;

        mDatabaseReference.child(questionKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot questionSnapshot) {
                if (!questionSnapshot.exists()) {
                    Toast.makeText(ScienceQuizActivity.this, "No more questions!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String question = questionSnapshot.child("questionText").getValue(String.class);
                String optA = questionSnapshot.child("optionA").getValue(String.class);
                String optB = questionSnapshot.child("optionB").getValue(String.class);
                String optC = questionSnapshot.child("optionC").getValue(String.class);
                String optD = questionSnapshot.child("optionD").getValue(String.class);
                correctAnswer = questionSnapshot.child("correctAnswer").getValue(String.class);

                questionText.setText(question);
                optionA.setText(optA);
                optionB.setText(optB);
                optionC.setText(optC);
                optionD.setText(optD);
                btnNext.setEnabled(false); // Disable until a new answer is selected
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScienceQuizActivity.this, "Failed to load the next question. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkAnswer(String selectedOption) {
        if (selectedOption.equals(correctAnswer)) {
            Toast.makeText(ScienceQuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ScienceQuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        btnNext.setEnabled(true); // Enable the Next button
    }
}
