package com.example.quiz_app;

public class Question {
    public String questionText;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correctAnswer;

    public Question(String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
}