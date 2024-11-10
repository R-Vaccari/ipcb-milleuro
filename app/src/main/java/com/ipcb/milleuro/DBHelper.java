package com.ipcb.milleuro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ipcb.milleuro.model.Answer;
import com.ipcb.milleuro.model.Difficulty;
import com.ipcb.milleuro.model.Question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    private static final String DIFF_TABLE = "Difficulty",
                                ANSWER_TABLE = "Answer",
                                QUESTION_TABLE = "Question",
                                QUESTION_ANSWER_TABLE = "QuestionAnswer";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DIFF_TABLE + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "difficultyLevel INTEGER NOT NULL, "
                + "name TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + ANSWER_TABLE + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "answerText TEXT)");

        db.execSQL("CREATE TABLE " + QUESTION_TABLE + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "questionText TEXT NOT NULL, "
                + "value INTEGER NOT NULL, "
                + "correctAnswerId INTEGER NOT NULL, "
                + "difficultyId INTEGER NOT NULL, "
                + "FOREIGN KEY (correctAnswerId) REFERENCES Answer(id),"
                + "FOREIGN KEY (difficultyId) REFERENCES Difficulty(id))");

        db.execSQL("CREATE TABLE " + QUESTION_ANSWER_TABLE + "("
                + "questionId INTEGER, "
                + "answerId INTEGER,"
                + "FOREIGN KEY (questionId) REFERENCES Question(id),"
                + "FOREIGN KEY (answerId) REFERENCES Answer(id),"
                + "PRIMARY KEY (questionId, answerId))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DIFF_TABLE);
        db.execSQL("DROP TABLE " + ANSWER_TABLE);
        db.execSQL("DROP TABLE " + QUESTION_TABLE);
        db.execSQL("DROP TABLE " + QUESTION_ANSWER_TABLE);
        onCreate(db);
    }

    public List<Difficulty> getDifficulties() {
        List<Difficulty> results = new ArrayList<>();
        try (Cursor cursor = getAllFromTable(DIFF_TABLE)) {
            results.add(new Difficulty(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2)));
        }

        return results;
    }

    public List<Answer> getAnswers() {
        List<Answer> results = new ArrayList<>();
        try (Cursor cursor = getAllFromTable(ANSWER_TABLE)) {
            results.add(new Answer(cursor.getInt(0),
                    cursor.getString(1)));
        }

        return results;
    }

    public List<Question> getQuestions() {
        List<Question> results = new ArrayList<>();
        List<Answer> possibleAnswer = new ArrayList<>();



        try (Cursor cursor = getAllFromTable(QUESTION_TABLE)) {
            final int difficultyId = cursor.getInt(4);
            final int correctAnswerId = cursor.getInt(3);
            final int questionId = cursor.getInt(0);
            final String questionText = cursor.getString(1);
            final int questionValue = cursor.getInt(2);

            //Getters
            final Difficulty difficulty = getDifficultyById(difficultyId);
            final Answer correctAnswer = getCorrectAnswerById(correctAnswerId);
            Question question = new Question(questionId, questionText, (Set<Answer>) possibleAnswer, correctAnswer, difficulty, questionValue);
            final Set<Answer> answers = getAvailableAnswers(question);

            results.add(new Question(cursor.getInt(0),
                    cursor.getString(1),
                    answers,
                    correctAnswer,
                    difficulty,
                    cursor.getInt(2)));
        }

        return results;
    }

    private Cursor getAllFromTable(String table) {
        final SQLiteDatabase db = this.getReadableDatabase();
        return db.query(table,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private Set<Answer> getAvailableAnswers(Question question) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Set<Answer> set = new HashSet<>(4);
        try (Cursor cursor = db.query(QUESTION_ANSWER_TABLE,
                null,
                "questionId = " + question.getId(),
                null,
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                do {
                    try (Cursor answerCursor = db.query(ANSWER_TABLE,
                            null,
                            "id = " + cursor.getInt(1),
                            null,
                            null,
                            null,
                            null)) {
                        if (answerCursor.moveToFirst()) {
                            do {
                                set.add(new Answer(answerCursor.getInt(0),
                                        answerCursor.getString(1)));
                            } while (answerCursor.moveToNext());
                        }
                    }
                } while (cursor.moveToNext());
            }
        }

        return set;
    }


    private Difficulty getDifficultyById(int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(DIFF_TABLE,
                null,
                "id = " + id,
                null,
                null,
                null,
                null)) {
            return new Difficulty(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2));
        }
    }

    private Answer getCorrectAnswerById(int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(ANSWER_TABLE,
                null,
                "id = " + id,
                null,
                null,
                null,
                null)) {
            if (cursor.moveToFirst())
                return new Answer(cursor.getInt(0),
                        cursor.getString(2));
            else return null;
        }
    }

    private Set<Answer> getAvailableAnswers(Question question) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Set<Answer> set = new HashSet<>(4);
        try (Cursor cursor = db.query(QUESTION_ANSWER_TABLE,
                null,
                "questionId = " + question.getId(),
                null,
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                do {
                    try (Cursor answerCursor = db.query(ANSWER_TABLE,
                            null,
                            "id = " + cursor.getInt(1), //trocar por retornar um TEXT e nao UM INT
                            null,
                            null,
                            null,
                            null)) {
                        if (answerCursor.moveToFirst()) {
                            do {
                                set.add(new Answer(answerCursor.getInt(0),
                                        answerCursor.getString(1)));
                            } while (answerCursor.moveToNext());
                        }
                    }
                } while (cursor.moveToNext());
            }
        }
        return set;
    }
}
