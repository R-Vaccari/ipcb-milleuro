package com.ipcb.milleuro.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return getId() == answer.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
