package com.example.playwithhestia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class BathroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);
        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(startIntent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton livingroomIB = (ImageButton) findViewById(R.id.livingroomIB);
        livingroomIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);
            }
        });
    }
}