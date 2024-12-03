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
                + "( 1, 'Paris' ),"
                + "( 2, 'Leonardo da Vinci' ),"
                + "( 3, 'Português' ),"
                + "( 4, '60' ),"
                + "( 5, 'George Washington' ),"
                + "( 6, 'Mercúrio' ),"
                + "( 7, 'Oceano Pacífico' ),"
                + "( 8, '60' ),"
                + "( 9, 'Yen' ),"
                + "( 10, 'África' ),"
                + "( 11, 'Vaticano' ),"
                + "( 12, 'H₂O' ),"
                + "( 13, 'Pedro Álvares Cabral' ),"
                + "( 14, 'William Shakespeare' ),"
                + "( 15, 'Plutão' ),"
                + "( 16, 'Pele' ),"
                + "( 17, 'Célula' ),"
                + "( 18, '1969' ),"
                + "( 19, 'Siddhartha Gautama' ),"
                + "( 20, 'Canadá' ),"
                + "( 21, 'Miguel de Cervantes' ),"
                + "( 22, 'Au' ),"
                + "( 23, 'Baleia Azul' ),"
                + "( 24, 'J.K. Rowling' ),"
                + "( 25, 'União Soviética' ),"
                + "( 26, 'Via Láctea' ),"
                + "( 27, '1789' ),"
                + "( 28, 'Suécia' ),"
                + "( 29, 'Nitrogênio' ),"
                + "( 30, 'Chita' ),"
                + "( 'Londres' ),"
                + "( 'Madri' ),"
                + "( 'Roma' ),"
                + "( 'Vincent van Gogh' ),"
                + "( 'Pablo Picasso' ),"
                + "( 'Michelangelo' ),"
                + "( 'Espanhol' ),"
                + "( 'Inglês' ),"
                + "( 'Francês' ),"
                + "( '30' ),"
                + "( '90' ),"
                + "( '120' ),"
                + "( 'Thomas Jefferson' ),"
                + "( 'Abraham Lincoln' ),"
                + "( 'John Adams' ),"
                + "( 'Vênus' ),"
                + "( 'Marte' ),"
                + "( 'Júpiter' ),"
                + "( 'Oceano Índico' ),"
                + "( 'Oceano Atlântico' ),"
                + "( 'Oceano Ártico' ),"
                + "( '100' ),"
                + "( '50' ),"
                + "( '120' ),"
                + "( 'Yuan' ),"
                + "( 'Won' ),"
                + "( 'Dólar' ),"
                + "( 'Ásia' ),"
                + "( 'América' ),"
                + "( 'Europa' ),"
                + "( 'Malta' ),"
                + "( 'Mônaco' ),"
                + "( 'San Marino' ),"
                + "( 'CO₂' ),"
                + "( 'O₂' ),"
                + "( 'NaCl' ),"
                + "( 'Cristóvão Colombo' ),"
                + "( 'Vasco da Gama' ),"
                + "( 'Fernão de Magalhães' ),"
                + "( 'Charles Dickens' ),"
                + "( 'Victor Hugo' ),"
                + "( 'Goethe' ),"
                + "( 'Marte' ),"
                + "( 'Vênus' ),"
                + "( 'Júpiter' ),"
                + "( 'Coração' ),"
                + "( 'Pulmão' ),"
                + "( 'Fígado' ),"
                + "( 'Molécula' ),"
                + "( 'Átomo' ),"
                + "( 'Tecido' ),"
                + "( '1960' ),"
                + "( '1965' ),"
                + "( '1972' ),"
                + "( 'Ashoka' ),"
                + "( 'Mahatma Gandhi' ),"
                + "( 'Vishnu' ),"
                + "( 'China' ),"
                + "( 'Rússia' ),"
                + "( 'Estados Unidos' ),"
                + "( 'Gabriel García Márquez' ),"
                + "( 'William Shakespeare' ),"
                + "( 'Fernando Pessoa' ),"
                + "( 'Ag' ),"
                + "( 'Hg' ),"
                + "( 'Fe' ),"
                + "( 'Elefante' ),"
                + "( 'Hipopótamo' ),"
                + "( 'Rinoceronte' ),"
                + "( 'J.R.R. Tolkien' ),"
                + "( 'Stephen King' ),"
                + "( 'George R.R. Martin' ),"
                + "( 'China' ),"
                + "( 'Alemanha' ),"
                + "( 'Cuba' ),"
                + "( 'Andrômeda' ),"
                + "( 'Centaurus' ),"
                + "( 'M51' ),"
                + "( '1689' ),"
                + "( '1776' ),"
                + "( '1799' ),"
                + "( 'Canadá' ),"
                + "( 'Indonésia' ),"
                + "( 'Filipinas' ),"
                + "( 'Oxigênio' ),"
                + "( 'Hidrogênio' ),"
                + "( 'Carbono' ),"
                + "( 'Tigre' ),"
                + "( 'Cavalo' ),"
                + "( 'Leopardo' );");

        db.execSQL("INSERT INTO " + QUESTION_TABLE + " (questionText, value, correctAnswerId, difficultyId) VALUES "
                + "( 'Qual é a capital da França?', 100, 1, 1 ),"
                + "( 'Quem pintou a Mona Lisa?', 100, 2, 1 ),"
                + "( 'Qual é o idioma oficial do Brasil?', 100, 3, 1 ),"
                + "( 'Quantos minutos tem uma hora?', 100, 4, 1 ),"
                + "( 'Quem foi o primeiro presidente dos Estados Unidos?', 100, 5, 1 ),"
                + "( 'Qual é o planeta mais próximo do Sol?', 100, 6, 1 ),"
                + "( 'Qual é o maior oceano do mundo?', 100, 7, 1 ),"
                + "( 'Quantos segundos tem um minuto?', 100, 8, 1 ),"
                + "( 'Qual é a moeda oficial do Japão?', 100, 9, 1 ),"
                + "( 'Em qual continente fica o Egito?', 100, 10, 1 ),"
                + "( 'Qual é o menor país do mundo?', 200, 11, 2 ),"
                + "( 'Qual é a fórmula química da água?', 200, 12, 2 ),"
                + "( 'Quem descobriu o Brasil?', 200, 13, 2 ),"
                + "( 'Quem escreveu a obra \"Hamlet\"?', 200, 14, 2 ),"
                + "( 'Qual desses é um planeta anão?', 200, 15, 2 ),"
                + "( 'Qual é o maior órgão do corpo humano?', 200, 16, 2 ),"
                + "( 'Qual é a unidade básica da vida?', 200, 17, 2 ),"
                + "( 'Em que ano o homem pisou na Lua pela primeira vez?', 200, 18, 2 ),"
                + "( 'Qual foi o primeiro nome de Buda?', 200, 19, 2 ),"
                + "( 'Qual é o segundo maior país do mundo em área?', 200, 20, 2 ),"
                + "( 'Quem escreveu \"Dom Quixote\"?', 300, 21, 3 ),"
                + "( 'Qual é o símbolo químico do Ouro?', 300, 22, 3 ),"
                + "( 'Qual é o maior mamífero do mundo?', 300, 23, 3 ),"
                + "( 'Quem é o autor de “Harry Potter”?', 300, 24, 3 ),"
                + "( 'Qual foi o primeiro país a adotar o socialismo?', 300, 25, 3 ),"
                + "( 'Qual o nome da galáxia onde está o Sistema Solar?', 300, 26, 3 ),"
                + "( 'Em que ano ocorreu a Revolução Francesa?', 300, 27, 3 ),"
                + "( 'Qual o país com o maior número de ilhas no mundo?', 300, 28, 3 ),"
                + "( 'Qual é o elemento químico mais abundante na atmosfera da Terra?', 300, 29, 3 ),"
                + "( 'Qual é o animal terrestre mais rápido do mundo?', 300, 30, 3 );");


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
