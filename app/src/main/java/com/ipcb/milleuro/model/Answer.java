package com.ipcb.milleuro.model;

public class Answer {
    private int id;
    private String answerText;

    public Answer(int id, String text) {
        this.id = id;
        this.answerText = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isCorrect(int correctAnswerId) {
        return this.id == correctAnswerId;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
