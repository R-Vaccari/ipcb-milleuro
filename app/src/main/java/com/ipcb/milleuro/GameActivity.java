package com.ipcb.milleuro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ipcb.milleuro.model.Answer;
import com.ipcb.milleuro.model.Difficulty;
import com.ipcb.milleuro.model.Question;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    TextView txtName, txtDifficulty, txtGrant, txtQuestion;
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    List<Question> questions;
    List<Answer> answers;
    List<Difficulty> difficulties;
    int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        try (DBHelper db = new DBHelper(this)){
            questions = db.getQuestions();
            answers = db.getAnswers();
            difficulties = db.getDifficulties();

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Question quests = questions.get(currentIndex);
        Answer ans = answers.get(0);
        Difficulty diff = difficulties.get(currentIndex);

        txtName = findViewById(R.id.Game_txtPlayerName);
        txtDifficulty = findViewById(R.id.Game_txtDifficulty);
        txtName.setText(String.format("Bem vindo %s", getIntent().getStringExtra("PlayerName")));
        txtGrant = findViewById(R.id.Game_txtGrant);
        txtQuestion = findViewById(R.id.Game_txtQuestion);
        txtQuestion.setText(quests.getQuestionText());
        txtDifficulty.setText(String.format("%s %s", diff.getDifficultyLevel(), diff.getName()));

        btnAnswer1 = findViewById(R.id.Game_btnAnswer1);
        btnAnswer2 = findViewById(R.id.Game_btnAnswer2);
        btnAnswer3 = findViewById(R.id.Game_btnAnswer3);
        btnAnswer4 = findViewById(R.id.Game_btnAnswer4);

        btnAnswer1.setText(ans.getAnswerText());
        btnAnswer2.setText(ans.getAnswerText());
        btnAnswer3.setText(quests.getCorrectAnswer().getAnswerText());
        btnAnswer4.setText(ans.getAnswerText());

        btnAnswer1.setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(1)));
        btnAnswer2.setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(2)));
        btnAnswer3.setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(3)));
        btnAnswer4.setOnClickListener(view -> {});


    }

    public void onClickAnswer(Answer ans){

    }
}