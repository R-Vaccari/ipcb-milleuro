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
            if(txtName.getText().toString().isEmpty()){
                Toast messageToast = Toast.makeText(MainActivity.this, "AADA", Toast.LENGTH_SHORT);
                messageToast.show();
            }else {
                Intent openGame = new Intent(this, GameActivity.class);
                openGame.putExtra("PlayerName", txtName.getText().toString());
                startActivity(openGame);
            }
        });
    }
}