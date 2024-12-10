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
    List<Button> buttonList = new ArrayList<>(4);
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    List<Question> questions;
    List<Answer> answers;
    List<Difficulty> difficulties;
    int currentIndex;
    int totalQuestions = 15;
    List<Question> selectedQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        try (DBHelper db = new DBHelper(this)){
            questions = db.getQuestions();
            answers = db.getAnswers();
            difficulties = db.getDifficulties();
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(1, 5, db));
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(2, 5, db));
            selectedQuestions.addAll(getRandomQuestionsByDifficulty(3, 5, db));
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

        buttonList.add(btnAnswer1);
        buttonList.add(btnAnswer2);
        buttonList.add(btnAnswer3);
        buttonList.add(btnAnswer4);

        txtName.setText(String.format("Bem vindo %s", getIntent().getStringExtra("PlayerName")));

        Log.d("GameActivity", String.valueOf(currentIndex));
        loadQuestion();
    }


    private List<Question> getRandomQuestionsByDifficulty(int difficultyLevel, int count, DBHelper db) {
        List<Question> questionsByDifficulty = db.getQuestionsByDifficulty(difficultyLevel);
        Collections.shuffle(questionsByDifficulty);
        return questionsByDifficulty.subList(0, Math.min(count, questionsByDifficulty.size()));
    }

    private void loadQuestion() {
        if (currentIndex >= selectedQuestions.size()) {
            finishGame();
            return;
        }

        Question question = questions.get(currentIndex);
        Log.d("GameActivity", question.toString());
        txtQuestion.setText(question.getQuestionText());
        txtDifficulty.setText(String.format("Dificuldade: %s", question.getDifficulty().getName()));

        // Prepara as respostas
        List<Answer> possibleAnswers = new ArrayList<>(question.getPossibleAnswers());
        Collections.shuffle(possibleAnswers);
        Log.d("GameActivity", possibleAnswers.toString());

        for (int i = 0; i < 4; i++) {
            final Button button = buttonList.get(i);
            final Answer answer = possibleAnswers.get(i);
            button.setText(answer.getAnswerText());
            button.setOnClickListener(view -> checkAnswer(answer));
        }
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
