package com.ipcb.milleuro;

import android.content.ContentValues;
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
import java.util.List;

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

    void addQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", question.getId());
        cv.put("questionText", question.getQuestionText());


        db.insert(QUESTION_TABLE, null, cv);
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


        try (Cursor cursor = getAllFromTable(QUESTION_TABLE)) {
            final int difficultyId = cursor.getInt(4);
            final Difficulty difficulty = getDifficultyById(difficultyId);

            results.add(new Question(cursor.getInt(0),
                    cursor.getString(1),
                    null,
                    null,
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

    private Difficulty getDifficultyById(int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(DIFF_TABLE,
                null,
                "id = " + id,
                null,
                null,
                null,
                null)) {
            if (cursor.moveToFirst())
                return new Difficulty(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2));
            else return null;
        }
    }
}
