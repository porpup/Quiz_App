package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdminDashboardActivity extends AppCompatActivity {
    private EditText questionEditText;
    private EditText optionAEditText;
    private EditText optionBEditText;
    private EditText optionCEditText;
    private EditText optionDEditText;
    private Spinner correctAnswerSpinner;
    private Spinner categorySpinner;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.adminDashboardToolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setTitle("Back to Login");

        questionEditText = findViewById(R.id.questionEditText);
        optionAEditText = findViewById(R.id.optionAEditText);
        optionBEditText = findViewById(R.id.optionBEditText);
        optionCEditText = findViewById(R.id.optionCEditText);
        optionDEditText = findViewById(R.id.optionDEditText);
        correctAnswerSpinner = findViewById(R.id.correctAnswerSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        Button addNewQuestionButton = findViewById(R.id.addNewQuestionButton);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        addNewQuestionButton.setOnClickListener(v -> {
            String question = questionEditText.getText().toString();
            String optionA = optionAEditText.getText().toString();
            String optionB = optionBEditText.getText().toString();
            String optionC = optionCEditText.getText().toString();
            String optionD = optionDEditText.getText().toString();
            String correctAnswer = correctAnswerSpinner.getSelectedItem().toString();
            String category = categorySpinner.getSelectedItem().toString();

            Question newQuestion = new Question(question, optionA, optionB, optionC, optionD, correctAnswer);

            mDatabaseReference.child(category + "Questions").push().setValue(newQuestion);

            Toast.makeText(AdminDashboardActivity.this, "Question added successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
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
