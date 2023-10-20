package com.example.quiz_app;

public class Model {
  private String username;
  private String email;
  private String password;
  private int highScore;

  public Model(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.highScore = 0;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getHighScore() {
    return highScore;
  }

  public void setHighScore(int highScore) {
    this.highScore = highScore;
  }
}
