package com.ipcb.milleuro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
                + "difficultyId INTEGER NOT NULL, "
                + "correctAnswerId INTEGER NOT NULL, "
                + "name TEXT NOT NULL, "
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

}
