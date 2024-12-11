package com.ipcb.milleuro.model;

import java.util.List;
import java.util.Set;

public class Question {
    private int id;
    private String questionText;
    private List<Answer> possibleAnswers;
    private Answer correctAnswer;
    private Difficulty difficulty;
    private int value;

    public Question(int id, String questionText, List<Answer> possibleAnswers, Answer correctAnswer, Difficulty difficulty, int value) {
        this.id = id;
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", possibleAnswers=" + possibleAnswers +
                ", correctAnswer=" + correctAnswer +
                ", difficulty=" + difficulty +
                ", value=" + value +
                '}';
    }

    public boolean isAnswerCorrect(Answer answer) {
        return answer.equals(correctAnswer);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
