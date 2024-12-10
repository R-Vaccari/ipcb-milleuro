package com.ipcb.milleuro;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ipcb.milleuro.model.Answer;
import com.ipcb.milleuro.model.Difficulty;
import com.ipcb.milleuro.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class GameActivity extends AppCompatActivity {

    TextView txtName, txtDifficulty, txtGrant, txtQuestion;
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    List<Question> questions;
    List<Answer> answers;
    List<Difficulty> difficulties;
    int currentIndex;
    int totalQuestions = 15;
    List<Question> selectedQuestions = new ArrayList<>();
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        try {
            db = new DBHelper(this);
            questions = db.getQuestions();
            answers = db.getAnswers();
            difficulties = db.getDifficulties();
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(1, 5));
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(2, 5));
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(3, 5));
        } finally {
            if (db != null) {
                db.close();
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtName = findViewById(R.id.Game_txtPlayerName);
        txtDifficulty = findViewById(R.id.Game_txtDifficulty);
        txtGrant = findViewById(R.id.Game_txtGrant);
        txtQuestion = findViewById(R.id.Game_txtQuestion);
        btnAnswer1 = findViewById(R.id.Game_btnAnswer1);
        btnAnswer2 = findViewById(R.id.Game_btnAnswer2);
        btnAnswer3 = findViewById(R.id.Game_btnAnswer3);
        btnAnswer4 = findViewById(R.id.Game_btnAnswer4);

        txtName.setText(String.format("Bem vindo %s", getIntent().getStringExtra("PlayerName")));


        loadQuestion();
        Log.e("GameActivity", "currentIndex");
    }


    private List<Question> getRandomQuestionsByDifficulty(int difficultyLevel, int count) {
        List<Question> questionsByDifficulty = db.getQuestionsByDifficulty(difficultyLevel);
        Collections.shuffle(questionsByDifficulty);
        return questionsByDifficulty.subList(0, Math.min(count, questionsByDifficulty.size()));
    }

    private void loadQuestion() {
        if (currentIndex >= selectedQuestions.size()) {
            finishGame();
            return;
        }

        Question question = selectedQuestions.get(currentIndex);
        txtQuestion.setText(question.getQuestionText());
        txtDifficulty.setText(String.format("Dificuldade: %s", question.getDifficulty().toString()));

        // Prepara as respostas
        List<Answer> possibleAnswers = new ArrayList<>(question.getPossibleAnswers());
        Collections.shuffle(possibleAnswers);

        // Exibe as respostas nos botões
        if (possibleAnswers.size() == 4) {
            btnAnswer1.setText(possibleAnswers.get(0).getAnswerText());
            btnAnswer2.setText(possibleAnswers.get(1).getAnswerText());
            btnAnswer3.setText(possibleAnswers.get(2).getAnswerText());
            btnAnswer4.setText(possibleAnswers.get(3).getAnswerText());
        } else {
            Log.e("GameActivity", "Número insuficiente de respostas na lista!");

        }

        // Configuração do clique nos botões
        btnAnswer1.setOnClickListener(view -> checkAnswer(possibleAnswers.get(0)));
        btnAnswer2.setOnClickListener(view -> checkAnswer(possibleAnswers.get(1)));
        btnAnswer3.setOnClickListener(view -> checkAnswer(possibleAnswers.get(2)));
        btnAnswer4.setOnClickListener(view -> checkAnswer(possibleAnswers.get(3)));
    }

    private void checkAnswer(Answer selectedAnswer) {
        if (selectedAnswer.isCorrect(selectedAnswer.getId())) {
            currentIndex++;
            loadQuestion();
        } else {
            Log.e("GameActivity", "Resposta incorreta!");
        }
    }

    private void finishGame() {
        txtQuestion.setText("Fim do jogo! Parabéns VAGABUNDO!");
    }
}
