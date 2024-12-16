package com.ipcb.milleuro;

import android.content.Intent;
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
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnSwitchQuestion, btn5050, btnGiveUp;
    List<Answer> answers;
    List<Difficulty> difficulties;
    int currentIndex, gainedMoney = 0;
    List<Question> selectedQuestions = new ArrayList<>();
    Question currentQuestion;
    private boolean switchQuestionIsUsed = false;
    private boolean fiftyFiftyIsUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        try (DBHelper db = new DBHelper(this)){
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
        txtGrant = findViewById(R.id.Game_txtGrant_Money);
        txtQuestion = findViewById(R.id.Game_txtQuestion);

        btnAnswer1 = findViewById(R.id.Game_btnAnswer1);
        btnAnswer2 = findViewById(R.id.Game_btnAnswer2);
        btnAnswer3 = findViewById(R.id.Game_btnAnswer3);
        btnAnswer4 = findViewById(R.id.Game_btnAnswer4);
        btnSwitchQuestion = findViewById(R.id.Game_btnHelp1);
        btn5050 = findViewById(R.id.Game_btnHelp2);
        btnGiveUp = findViewById(R.id.Game_btnExit);

        buttonList.add(btnAnswer1);
        buttonList.add(btnAnswer2);
        buttonList.add(btnAnswer3);
        buttonList.add(btnAnswer4);

        btnSwitchQuestion.setOnClickListener(view -> {
            if (switchQuestionIsUsed) {
                Toast.makeText(this, "Só pode usar uma ajuda por jogo.",
                        Toast.LENGTH_SHORT).show();
            } else {
                switchQuestion();
                switchQuestionIsUsed = true;
                disableHelp(btnSwitchQuestion);
            }
        });

        btn5050.setOnClickListener(view -> {
            if (fiftyFiftyIsUsed) {
                Toast.makeText(this, "Só pode usar uma ajuda por jogo.",
                        Toast.LENGTH_SHORT).show();
            } else {
                applyFiftyFifty();
                fiftyFiftyIsUsed = true;
                disableHelp(btn5050);
            }
        });

        btnGiveUp.setOnClickListener(view -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Desistir do Jogo")
                    .setMessage("Quer realmente desistir do jogo e voltar para o ecrã inicial?")
                    .setCancelable(false) // Impede a cena de fechar ao tocar fora do dialog
                    .setPositiveButton("Sim", (dialog, id) -> {
                        finish(); // Finalizar o jogo
                        startActivity(new Intent(this, MainActivity.class));
                    })
                    .setNegativeButton("Não", (dialog, id) -> {
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });


        txtName.setText(String.format("%s", getIntent().getStringExtra("PlayerName")));

        Log.d("GameActivity", String.valueOf(currentIndex));
        loadQuestion();
    }

    private void disableHelp(Button btn) {
        btn.setEnabled(false);

    }

    private void resetButtons() {
        for (Button button : buttonList) {
            button.setEnabled(true);
        }
    }


    private List<Question> getRandomQuestionsByDifficulty(int difficultyLevel, int count, DBHelper db) {
        List<Question> questionsByDifficulty = db.getQuestionsByDifficulty(difficultyLevel);
        Collections.shuffle(questionsByDifficulty);
        return questionsByDifficulty.subList(0, Math.min(count, questionsByDifficulty.size()));
    }

    private int correctAnswerId;

    // Função para carregar as perguntas (O jogo em si) - Lucas
    private void loadQuestion() {
        if (currentIndex >= selectedQuestions.size()) {
            finishGame();
            return;
        }

        resetButtons(); // Restart buttons after using 50/50

        txtGrant.setText(String.format("%s€", gainedMoney));

        Question question = selectedQuestions.get(currentIndex);
        currentQuestion = question;
        Log.d("Question", currentQuestion.toString());
        txtQuestion.setText(currentQuestion.getQuestionText());
        txtDifficulty.setText(String.format("Dificuldade: %s",
                currentQuestion.getDifficulty().getName()));
        correctAnswerId = currentQuestion.getCorrectAnswer().getId();

        // Prepara as respostas
        List<Answer> possibleAnswers = new ArrayList<>(currentQuestion.getPossibleAnswers());
        Collections.shuffle(possibleAnswers);
        Log.d("Possible Answers", possibleAnswers.toString());

        for (int i = 0; i < 4; i++) {
            final Button button = buttonList.get(i);
            final Answer answer = possibleAnswers.get(i);
            button.setText(answer.getAnswerText());
            button.setOnClickListener(view -> checkAnswer(answer));
        }
    }

    // Função para a ajuda "50/50"
    private void applyFiftyFifty() {
        List<Button> incorrectButtons = new ArrayList<>();

        // Identificar as respostas incorretas nos botões
        for (Button button : buttonList) {
            if (!button.getText().toString().equals(currentQuestion.getCorrectAnswer()
                    .getAnswerText())) {
                incorrectButtons.add(button);
            }
        }

        // Verificar se há pelo menos duas respostas incorretas
        if (incorrectButtons.size() < 2) {
            Toast.makeText(this, "Não há respostas suficientes para aplicar 50/50.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Remover duas respostas incorretas aleatoriamente
        Collections.shuffle(incorrectButtons);
        incorrectButtons.get(0).setEnabled(false);
        incorrectButtons.get(0).setText("");
        incorrectButtons.get(1).setEnabled(false);
        incorrectButtons.get(1).setText("");

        Toast.makeText(this, "50/50 aplicado! Duas respostas foram eliminadas.",
                Toast.LENGTH_SHORT).show();
    }

    // Função para a ajuda "Trocar Pergunta"
    private void switchQuestion() {
        try (DBHelper db = new DBHelper(this)) {
            List<Question> questionsByDifficulty = db.getQuestionsByDifficulty(currentQuestion.getDifficulty().getDifficultyLevel());
            questionsByDifficulty.remove(currentQuestion);

            if (questionsByDifficulty.isEmpty()) {
                Toast.makeText(this, "Não há mais perguntas disponíveis para troca.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Collections.shuffle(questionsByDifficulty);
            currentQuestion = questionsByDifficulty.get(0);

            txtQuestion.setText(currentQuestion.getQuestionText());
            txtDifficulty.setText(String.format("Dificuldade: %s",
                    currentQuestion.getDifficulty().getName()));
            correctAnswerId = currentQuestion.getCorrectAnswer().getId();

            List<Answer> possibleAnswers = new ArrayList<>(currentQuestion.getPossibleAnswers());
            Collections.shuffle(possibleAnswers);

            for (int i = 0; i < 4; i++) {
                final Button button = buttonList.get(i);
                final Answer answer = possibleAnswers.get(i);
                button.setText(answer.getAnswerText());
                button.setOnClickListener(view -> checkAnswer(answer));
            }

            Toast.makeText(this, "Pergunta trocada!", Toast.LENGTH_SHORT).show();
        }
    }

    // Função para verificar a resposta escolhida - Lucas
    private void checkAnswer(Answer selectedAnswer) {
        if (currentQuestion.isAnswerCorrect(selectedAnswer)) {
            Log.d("CheckAnswer", String.valueOf(correctAnswerId));
            gainedMoney += currentQuestion.getValue();
            currentIndex++;
            loadQuestion();
        } else {
            Log.e("CheckAnswer", "Resposta incorreta!");
            Toast.makeText(this, "Resposta errada! Você ganhou " + gainedMoney + "€.", Toast.LENGTH_LONG).show();
            finishGame();
        }
    }

    private void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("PlayerName", getIntent().getStringExtra("PlayerName"));
        intent.putExtra("Score", gainedMoney);
        startActivity(intent);
        finish();
    }

}
