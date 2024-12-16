package com.ipcb.milleuro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Button btnRetry, btnMainMenu;
    TextView txtResultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtResultMessage = findViewById(R.id.Result_txtMessage);
        btnRetry = findViewById(R.id.Result_btnRetry);
        btnMainMenu = findViewById(R.id.Result_btnMainMenu);

        String playerName = getIntent().getStringExtra("PlayerName");
        int finalScore = getIntent().getIntExtra("Score", 0);

        txtResultMessage.setText(String.format("Fim de jogo! Parabéns %s!\nVocê ganhou %d€.", playerName, finalScore));

        // Botão para reiniciar o jogo
        btnRetry.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PlayerName", playerName);
            startActivity(intent);
            finish();
        });

        // Botão para voltar ao inicio
        btnMainMenu.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}