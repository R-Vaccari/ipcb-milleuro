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
import com.ipcb.milleuro.model.Question;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    TextView txtName, txtGrant, txtQuestion;
    List<Question> questions;
    int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        try (DBHelper db = new DBHelper(this)){
            questions = db.getQuestions();

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Question quests = questions.get(currentIndex);

        txtName = findViewById(R.id.Game_txtPlayerName);
        txtName.setText(String.format("Bem vindo %s", getIntent().getStringExtra("PlayerName")));
        txtGrant = findViewById(R.id.Game_txtGrant);
        txtQuestion = findViewById(R.id.Game_txtQuestion);
        txtQuestion.setText(quests.getQuestionText());
        findViewById(R.id.Game_btnAnswer1).setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(0)));
        findViewById(R.id.Game_btnAnswer2).setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(1)));
        findViewById(R.id.Game_btnAnswer3).setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(2)));
        findViewById(R.id.Game_btnAnswer4).setOnClickListener(view -> onClickAnswer(quests.getPossibleAnswers().get(3)));
        findViewById(R.id.Game_btnExit).setOnClickListener(view -> {});


    }

    public void onClickAnswer(Answer ans){

    }
}