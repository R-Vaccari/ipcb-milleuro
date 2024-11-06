package com.ipcb.milleuro.model;

public class Difficulty {
    private int id;
    private int difficultyLevel;
    private String name;

    public Difficulty(int id, int difficultyLevel, String name) {
        this.id = id;
        this.difficultyLevel = difficultyLevel;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
