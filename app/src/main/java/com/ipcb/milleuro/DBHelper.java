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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String
                                DATABASE_NAME = "Milleuro",
                                DIFF_TABLE = "Difficulty",
                                ANSWER_TABLE = "Answer",
                                QUESTION_TABLE = "Question",
                                QUESTION_ANSWER_TABLE = "QuestionAnswer";

    String easy = ", Fácil)";
    String medium = ", Médio)";
    String hard = ", Dificil)";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

        db.execSQL("INSERT INTO " + DIFF_TABLE + " VALUES ("
                + 1
                + easy );

        db.execSQL("INSERT INTO " + DIFF_TABLE + " VALUES ("
                + 2
                + medium );

        db.execSQL("INSERT INTO " + DIFF_TABLE + " VALUES ("
                + 3
                + hard );

        db.execSQL("INSERT INTO " + ANSWER_TABLE + " VALUES "
                + "( 'Paris' ),"
                + "( 'Leonardo da Vinci' ),"
                + "( 'Português' ),"
                + "( '60' ),"
                + "( 'George Washington' ),"
                + "( 'Mercúrio' ),"
                + "( 'Oceano Pacífico' ),"
                + "( '60' ),"
                + "( 'Yen' ),"
                + "( 'África' ),"
                + "( 'Vaticano' ),"
                + "( 'H₂O' ),"
                + "( 'Pedro Álvares Cabral' ),"
                + "( 'William Shakespeare' ),"
                + "( 'Plutão' ),"
                + "( 'Pele' ),"
                + "( 'Célula' ),"
                + "( '1969' ),"
                + "( 'Siddhartha Gautama' ),"
                + "( 'Canadá' ),"
                + "( 'Miguel de Cervantes' ),"
                + "( 'Au' ),"
                + "( 'Baleia Azul' ),"
                + "( 'J.K. Rowling' ),"
                + "( 'União Soviética' ),"
                + "( 'Via Láctea' ),"
                + "( '1789' ),"
                + "( 'Suécia' ),"
                + "( 'Nitrogênio' ),"
                + "( 'Chita' );");


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

    public List<Question> getQuestions() throws NullPointerException {
        List<Question> results = new ArrayList<>();
        try (Cursor cursor = getAllFromTable(QUESTION_TABLE)) {
            if (cursor.moveToFirst()) {
                final int difficultyId = cursor.getInt(4);
                final int correctAnswerId = cursor.getInt(3);
                final int questionId = cursor.getInt(0);

                final List<Answer> answers = getAvailableAnswers(questionId);
                final Answer correctAnswer = getCorrectAnswerById(correctAnswerId);
                final Difficulty difficulty = getDifficultyById(difficultyId);

                if (answers.size() != 4)
                    throw new NullPointerException(String.format(
                            "Question with id [%s] does not have 4 answers!",
                            questionId
                    ));

                if (correctAnswer == null)
                    throw new NullPointerException(String.format(
                            "Correct answer not found for id [%s] for question with id [%s]",
                            correctAnswerId,
                            questionId));

                if (difficulty == null)
                    throw new NullPointerException(String.format(
                            "Difficulty not found for id [%s] for question with id [%s]",
                            difficultyId,
                            questionId));

                results.add(new Question(questionId,
                        cursor.getString(1),
                        answers,
                        correctAnswer,
                        difficulty,
                        cursor.getInt(2)));
            }
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

    private List<Answer> getAvailableAnswers(int questionId) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final List<Answer> set = new ArrayList<>(4);
        try (Cursor cursor = db.query(QUESTION_ANSWER_TABLE,
                null,
                "questionId = " + questionId,
                null,
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                do {
                    try (Cursor answerCursor = db.query(ANSWER_TABLE,
                            null,
                            "id = " + cursor.getInt(1), //trocar por retornar um TEXT e nao UM INT -> O casting aqui é feito implicitamente (Rodrigo)
                            null,
                            null,
                            null,
                            null)) {
                        if (answerCursor.moveToFirst())
                                set.add(new Answer(answerCursor.getInt(0),
                                        answerCursor.getString(1)));
                    }
                } while (cursor.moveToNext());
            }
        }
        return set;
    }

}
