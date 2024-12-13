package com.ipcb.milleuro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnStart = findViewById(R.id.Main_btnStart);
        txtName= findViewById(R.id.Main_txtName);

        btnStart.setOnClickListener(view -> {
            String playerName = txtName.getText().toString().trim();

            // Verificar se o campo está vazio
            if (playerName.isEmpty()) {
                Toast.makeText(this, "Ups... Necessita de introduzir um " +
                        "nome de jogador.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar se nome tem menos de 3 caracteres
            if (playerName.length() < 3) {
                Toast.makeText(this, "O nome deve ter pelo menos" +
                        " 3 caracteres.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar se o nome é numérico
            if (playerName.matches("\\d+")) {
                Toast.makeText(this, "O nome não pode ser um número.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
                Intent openGame = new Intent(this, GameActivity.class);
                openGame.putExtra("PlayerName", txtName.getText().toString());
                startActivity(openGame);
        });
    }
}