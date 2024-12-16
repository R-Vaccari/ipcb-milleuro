package com.ipcb.milleuro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ipcb.milleuro.model.Answer;
import com.ipcb.milleuro.model.Difficulty;
import com.ipcb.milleuro.model.Question;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String
                                DATABASE_NAME = "Milleuro",
                                DIFF_TABLE = "Difficulty",
                                ANSWER_TABLE = "Answer",
                                QUESTION_TABLE = "Question",
                                QUESTION_ANSWER_TABLE = "QuestionAnswer";

    String easy = ", 'Facil')";
    String medium = ", 'Médio')";
    String hard = ", 'Dificil')";

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
                + "id INTEGER PRIMARY KEY, "
                + "answerText TEXT)");

        db.execSQL("CREATE TABLE " + QUESTION_TABLE + "("
                + "id INTEGER PRIMARY KEY, "
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
                + 1 + ","
                + 1
                + easy );

        db.execSQL("INSERT INTO " + DIFF_TABLE + " VALUES ("
                + 2 + ","
                + 2
                + medium );

        db.execSQL("INSERT INTO " + DIFF_TABLE + " VALUES ("
                + 3 + ","
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
                + "( 31, 'Londres' ),"
                + "( 32, 'Madri' ),"
                + "( 33, 'Roma' ),"
                + "( 34, 'Vincent van Gogh' ),"
                + "( 35, 'Pablo Picasso' ),"
                + "( 36, 'Michelangelo' ),"
                + "( 37, 'Espanhol' ),"
                + "( 38, 'Inglês' ),"
                + "( 39, 'Francês' ),"
                + "( 40, '30' ),"
                + "( 41, '90' ),"
                + "( 42, '120' ),"
                + "( 43, 'Thomas Jefferson' ),"
                + "( 44, 'Abraham Lincoln' ),"
                + "( 45, 'John Adams' ),"
                + "( 46, 'Vênus' ),"
                + "( 47, 'Marte' ),"
                + "( 48, 'Júpiter' ),"
                + "( 49, 'Oceano Índico' ),"
                + "( 50, 'Oceano Atlântico' ),"
                + "( 51, 'Oceano Ártico' ),"
                + "( 52, '100' ),"
                + "( 53, '50' ),"
                + "( 54, '120' ),"
                + "( 55, 'Yuan' ),"
                + "( 56, 'Won' ),"
                + "( 57, 'Dólar' ),"
                + "( 58, 'Ásia' ),"
                + "( 59, 'América' ),"
                + "( 60, 'Europa' ),"
                + "( 61, 'Malta' ),"
                + "( 62, 'Mônaco' ),"
                + "( 63, 'San Marino' ),"
                + "( 64, 'CO₂' ),"
                + "( 65, 'O₂' ),"
                + "( 66, 'NaCl' ),"
                + "( 67, 'Cristóvão Colombo' ),"
                + "( 68, 'Vasco da Gama' ),"
                + "( 69, 'Fernão de Magalhães' ),"
                + "( 70, 'Charles Dickens' ),"
                + "( 71, 'Victor Hugo' ),"
                + "( 72, 'Goethe' ),"
                + "( 73, 'Marte' ),"
                + "( 74, 'Vênus' ),"
                + "( 75, 'Júpiter' ),"
                + "( 76, 'Coração' ),"
                + "( 77, 'Pulmão' ),"
                + "( 78, 'Fígado' ),"
                + "( 79, 'Molécula' ),"
                + "( 80, 'Átomo' ),"
                + "( 81, 'Tecido' ),"
                + "( 82, '1960' ),"
                + "( 83, '1965' ),"
                + "( 84, '1972' ),"
                + "( 85, 'Ashoka' ),"
                + "( 86, 'Mahatma Gandhi' ),"
                + "( 87, 'Vishnu' ),"
                + "( 88, 'China' ),"
                + "( 89, 'Rússia' ),"
                + "( 90, 'Estados Unidos' ),"
                + "( 91, 'Gabriel García Márquez' ),"
                + "( 92, 'William Shakespeare' ),"
                + "( 93, 'Fernando Pessoa' ),"
                + "( 94, 'Ag' ),"
                + "( 95, 'Hg' ),"
                + "( 96, 'Fe' ),"
                + "( 97, 'Elefante' ),"
                + "( 98, 'Hipopótamo' ),"
                + "( 99, 'Rinoceronte' ),"
                + "( 100, 'J.R.R. Tolkien' ),"
                + "( 101, 'Stephen King' ),"
                + "( 102, 'George R.R. Martin' ),"
                + "( 103, 'China' ),"
                + "( 104, 'Alemanha' ),"
                + "( 105, 'Cuba' ),"
                + "( 106, 'Andrômeda' ),"
                + "( 107, 'Centaurus' ),"
                + "( 108, 'M51' ),"
                + "( 109, '1689' ),"
                + "( 110, '1776' ),"
                + "( 111, '1799' ),"
                + "( 112, 'Canadá' ),"
                + "( 113, 'Indonésia' ),"
                + "( 114, 'Filipinas' ),"
                + "( 115, 'Oxigênio' ),"
                + "( 116, 'Hidrogênio' ),"
                + "( 117, 'Carbono' ),"
                + "( 118, 'Tigre' ),"
                + "( 119, 'Cavalo' ),"
                + "( 120, 'Leopardo' );");


        db.execSQL("INSERT INTO " + QUESTION_TABLE + " VALUES "
                + "( 1, 'Qual é a capital da França?', 100, 1, 1 ),"
                + "( 2, 'Quem pintou a Mona Lisa?', 100, 2, 1 ),"
                + "( 3, 'Qual é o idioma oficial do Brasil?', 100, 3, 1 ),"
                + "( 4, 'Quantos minutos tem uma hora?', 100, 4, 1 ),"
                + "( 5, 'Quem foi o primeiro presidente dos Estados Unidos?', 100, 5, 1 ),"
                + "( 6, 'Qual é o planeta mais próximo do Sol?', 100, 6, 1 ),"
                + "( 7, 'Qual é o maior oceano do mundo?', 100, 7, 1 ),"
                + "( 8, 'Quantos segundos tem um minuto?', 100, 8, 1 ),"
                + "( 9, 'Qual é a moeda oficial do Japão?', 100, 9, 1 ),"
                + "( 10, 'Em qual continente fica o Egito?', 100, 10, 1 ),"
                + "( 11, 'Qual é o menor país do mundo?', 200, 11, 2 ),"
                + "( 12, 'Qual é a fórmula química da água?', 200, 12, 2 ),"
                + "( 13, 'Quem descobriu o Brasil?', 200, 13, 2 ),"
                + "( 14, 'Quem escreveu a obra \"Hamlet\"?', 200, 14, 2 ),"
                + "( 15, 'Qual desses é um planeta anão?', 200, 15, 2 ),"
                + "( 16, 'Qual é o maior órgão do corpo humano?', 200, 16, 2 ),"
                + "( 17, 'Qual é a unidade básica da vida?', 200, 17, 2 ),"
                + "( 18, 'Em que ano o homem pisou na Lua pela primeira vez?', 200, 18, 2 ),"
                + "( 19, 'Qual foi o primeiro nome de Buda?', 200, 19, 2 ),"
                + "( 20, 'Qual é o segundo maior país do mundo em área?', 200, 20, 2 ),"
                + "( 21, 'Quem escreveu \"Dom Quixote\"?', 300, 21, 3 ),"
                + "( 22, 'Qual é o símbolo químico do Ouro?', 300, 22, 3 ),"
                + "( 23, 'Qual é o maior mamífero do mundo?', 300, 23, 3 ),"
                + "( 24, 'Quem é o autor de “Harry Potter”?', 300, 24, 3 ),"
                + "( 25, 'Qual foi o primeiro país a adotar o socialismo?', 300, 25, 3 ),"
                + "( 26, 'Qual o nome da galáxia onde está o Sistema Solar?', 300, 26, 3 ),"
                + "( 27, 'Em que ano ocorreu a Revolução Francesa?', 300, 27, 3 ),"
                + "( 28, 'Qual o país com o maior número de ilhas no mundo?', 300, 28, 3 ),"
                + "( 29, 'Qual é o elemento químico mais abundante na atmosfera da Terra?', 300, 29, 3 ),"
                + "( 30, 'Qual é o animal terrestre mais rápido do mundo?', 300, 30, 3 );");

        db.execSQL("INSERT INTO " + QUESTION_ANSWER_TABLE + " VALUES "
                + "( 1, 1 ),"
                + "( 1, 31 ),"
                + "( 1, 42 ),"
                + "( 1, 36 ),"
                + "( 2, 2 ),"
                + "( 2, 55 ),"
                + "( 2, 67 ),"
                + "( 2, 45 ),"
                + "( 3, 3 ),"
                + "( 3, 39 ),"
                + "( 3, 46 ),"
                + "( 3, 72 ),"
                + "( 4, 4 ),"
                + "( 4, 41 ),"
                + "( 4, 53 ),"
                + "( 4, 78 ),"
                + "( 5, 5 ),"
                + "( 5, 43 ),"
                + "( 5, 110 ),"
                + "( 5, 95 ),"
                + "( 6, 6 ),"
                + "( 6, 46 ),"
                + "( 6, 74 ),"
                + "( 6, 48 ),"
                + "( 7, 7 ),"
                + "( 7, 49 ),"
                + "( 7, 50 ),"
                + "( 7, 51 ),"
                + "( 8, 8 ),"
                + "( 8, 52 ),"
                + "( 8, 53 ),"
                + "( 8, 54 ),"
                + "( 9, 9 ),"
                + "( 9, 55 ),"
                + "( 9, 56 ),"
                + "( 9, 57 ),"
                + "( 10, 10 ),"
                + "( 10, 58 ),"
                + "( 10, 59 ),"
                + "( 10, 60 ),"
                + "( 11, 11 ),"
                + "( 11, 61 ),"
                + "( 11, 62 ),"
                + "( 11, 63 ),"
                + "( 12, 12 ),"
                + "( 12, 64 ),"
                + "( 12, 65 ),"
                + "( 12, 66 ),"
                + "( 13, 13 ),"
                + "( 13, 67 ),"
                + "( 13, 68 ),"
                + "( 13, 69 ),"
                + "( 14, 14 ),"
                + "( 14, 70 ),"
                + "( 14, 71 ),"
                + "( 14, 72 ),"
                + "( 15, 15 ),"
                + "( 15, 73 ),"
                + "( 15, 74 ),"
                + "( 15, 75 ),"
                + "( 16, 16 ),"
                + "( 16, 76 ),"
                + "( 16, 77 ),"
                + "( 16, 78 ),"
                + "( 17, 17 ),"
                + "( 17, 79 ),"
                + "( 17, 80 ),"
                + "( 17, 81 ),"
                + "( 18, 18 ),"
                + "( 18, 82 ),"
                + "( 18, 83 ),"
                + "( 18, 84 ),"
                + "( 19, 19 ),"
                + "( 19, 85 ),"
                + "( 19, 86 ),"
                + "( 19, 87 ),"
                + "( 20, 20 ),"
                + "( 20, 88 ),"
                + "( 20, 89 ),"
                + "( 20, 90 ),"
                + "( 21, 21 ),"
                + "( 21, 91 ),"
                + "( 21, 92 ),"
                + "( 21, 93 ),"
                + "( 22, 22 ),"
                + "( 22, 94 ),"
                + "( 22, 95 ),"
                + "( 22, 96 ),"
                + "( 23, 23 ),"
                + "( 23, 97 ),"
                + "( 23, 98 ),"
                + "( 23, 99 ),"
                + "( 24, 24 ),"
                + "( 24, 100 ),"
                + "( 24, 101 ),"
                + "( 24, 102 ),"
                + "( 25, 25 ),"
                + "( 25, 103 ),"
                + "( 25, 104 ),"
                + "( 25, 105 ),"
                + "( 26, 26 ),"
                + "( 26, 106 ),"
                + "( 26, 107 ),"
                + "( 26, 108 ),"
                + "( 27, 27 ),"
                + "( 27, 109 ),"
                + "( 27, 110 ),"
                + "( 27, 111 ),"
                + "( 28, 28 ),"
                + "( 28, 112 ),"
                + "( 28, 113 ),"
                + "( 28, 114 ),"
                + "( 29, 29 ),"
                + "( 29, 115 ),"
                + "( 29, 116 ),"
                + "( 29, 117 ),"
                + "( 30, 30 ),"
                + "( 30, 118 ),"
                + "( 30, 97 ),"
                + "( 30, 80 );");

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
            if (cursor.moveToFirst())
                results.add(new Difficulty(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2)));
        }

        return results;
    }

    public List<Answer> getAnswers() {
        List<Answer> results = new ArrayList<>();
        try (Cursor cursor = getAllFromTable(ANSWER_TABLE)) {
            if (cursor.moveToFirst())
                results.add(new Answer(cursor.getInt(0),
                    cursor.getString(1)));
        }

        return results;
    }

    public List<Question> getQuestionsByDifficulty(int difficultyLevel) {
        List<Question> questions = new ArrayList<>();
        try (Cursor cursor = getQuestionByDifficultyId(QUESTION_TABLE,difficultyLevel)) {
            if (cursor.moveToFirst()) {
                do {
                    int questionId = cursor.getInt(0);
                    String questionText = cursor.getString(1);
                    int value = cursor.getInt(2);
                    int correctAnswerId = cursor.getInt(3);


                    List<Answer> answers = getAvailableAnswers(questionId);
                    Answer correctAnswer = getCorrectAnswerById(correctAnswerId);
                    Difficulty difficulty = getDifficultyById(difficultyLevel);


                    questions.add(new Question(questionId, questionText, answers, correctAnswer, difficulty, value));
                } while (cursor.moveToNext());
            }
        }
        return questions;
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
            if (cursor.moveToFirst()) {
                return new Difficulty(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2));
            } else
                return null;
        }
    }

    private Cursor getQuestionByDifficultyId(String table, int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        return db.query(table,
                null,
                "difficultyId = " + id,
                null,
                null,
                null,
                null);
    }


    private Answer getCorrectAnswerById(int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + ANSWER_TABLE + " WHERE id = " + id,
                null)) {
            if (cursor.moveToFirst())
                return new Answer(cursor.getInt(0),
                        cursor.getString(1));
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
