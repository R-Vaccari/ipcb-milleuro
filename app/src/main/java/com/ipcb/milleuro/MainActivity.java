package com.ipcb.milleuro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.EditText;

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
            //String mensagem = "Este texto é para abrir com um bloco notas!";

            Intent openGame = new Intent(this, GameActivity.class);
            openGame.setAction(Intent.ACTION_SEND);
            openGame.putExtra("PlayerName", txtName.getText());

            startActivity(openGame);
        });
    }
}