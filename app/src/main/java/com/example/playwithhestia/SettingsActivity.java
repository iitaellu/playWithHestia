package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //From the course material https://www.youtube.com/watch?v=6ow3L39Wxmg
        Button git = (Button) findViewById(R.id.gitButton);
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String git = "https://github.com/iitaellu/playWithMeow";
                Uri web = Uri.parse(git);

                Intent gotoGit = new Intent(Intent.ACTION_VIEW, web);
                if (gotoGit.resolveActivity(getPackageManager()) == null){
                    startActivity(gotoGit);
                }
            }
        });

        Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);            }
        });
    }
}