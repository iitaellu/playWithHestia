package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_LONG).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);
            }
        });
    }
}