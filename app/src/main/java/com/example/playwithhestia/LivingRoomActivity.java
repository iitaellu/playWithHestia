package com.example.playwithhestia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LivingRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(startIntent);            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            }
        });


        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("   Living Room");

        //actionBar.setIcon(R.drawable.ic_settings);

        //actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        ImageButton bathroomIB = (ImageButton) findViewById(R.id.bathroomIB);
        bathroomIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), BathroomActivity.class);
                startActivity(startIntent);
            }
        });

        ImageButton kitchenIB = (ImageButton) findViewById(R.id.kitchenIB);
        kitchenIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), KitchenActivity.class);
                startActivity(startIntent);
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

    }
}