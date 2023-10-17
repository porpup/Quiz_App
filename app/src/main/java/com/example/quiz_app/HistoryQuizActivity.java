package com.example.quiz_app;


import android.content.Intent;
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

public class HistoryQuizActivity extends AppCompatActivity {

    private TextView questionText;
    private Button optionA, optionB, optionC, optionD, btnNext, btnFinish;
    private String correctAnswer;
    private int currentQuestionIndex = 0;

    private DatabaseReference mDatabaseReference;

    private int userScore = 0;
    private int totalQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_quiz);

        questionText = findViewById(R.id.questionText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        btnNext = findViewById(R.id.btnNext);
        btnFinish = findViewById(R.id.btnFinish);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("HistoryQuestions");

        loadNextQuestion();

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionA");
                disableAnswerButtons();
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionB");
                disableAnswerButtons();
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionC");
                disableAnswerButtons();
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("optionD");
                disableAnswerButtons();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryQuizActivity.this, ResultActivity.class);
                intent.putExtra("SCORE", userScore);
                startActivity(intent);
                finish();
            }
        });

        btnNext.setEnabled(false);

    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        String questionKey = "Q" + currentQuestionIndex;

        mDatabaseReference.child(questionKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot questionSnapshot) {
                if (!questionSnapshot.exists()) {
                    btnNext.setVisibility(View.GONE);
                    btnFinish.setVisibility(View.VISIBLE);
                    Toast.makeText(HistoryQuizActivity.this, "No more questions!", Toast.LENGTH_SHORT).show();
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
                btnNext.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryQuizActivity.this, "Failed to load the next question. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalQuestions = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryQuizActivity.this, "Failed to retrieve total question count.", Toast.LENGTH_SHORT).show();
            }
        });


        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }

    private void checkAnswer(String selectedOption) {
        if (selectedOption.equals(correctAnswer)) {
            userScore++;
            Toast.makeText(HistoryQuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HistoryQuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        if (currentQuestionIndex == totalQuestions) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnNext.setEnabled(true);
        }
    }

    private void disableAnswerButtons() {
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

}