package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LivingRoomActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";
    int count=0;
    ImageView pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);
        pet = (ImageView) findViewById(R.id.PETIMAGE);

        setView();


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

        ImageButton socialise = (ImageButton) findViewById(R.id.sosialisebuttonLivingRoom);
        socialise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Socialise", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton play = (ImageButton) findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_LONG).show();

                pet.setImageResource(R.drawable.hestia);
                //nex counDownTimer part is partly from https://www.codegrepper.com/code-examples/java/countdown+timer+android+studio
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.hestia_neutral);
                    }
                }.start();
            }
        });
    }
    //From the old project
    public String[] readFile(String filename,String person) {
        BufferedReader br = null;
        try {
            String line;
            String[] lines;
            br = new BufferedReader(new FileReader(getApplication().getFilesDir().getPath() + "/" + person+filename));
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                line = line+",";
                buffer.append(line);
            }
            String result = buffer.toString();
            lines = result.split(",");

            String wanted = lines[lines.length-1];
            String[] info = wanted.split(";");
            return info;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        String[] info = null;
        return info;
    }


    public void setView(){
        TextView room = (TextView) findViewById(R.id.room);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameLiving);
        TextView hungry = (TextView) findViewById(R.id.textViewHungry);
        TextView thirsty = (TextView) findViewById(R.id.textViewThirsty);
        TextView boring = (TextView) findViewById(R.id.textViewBoring);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly);
        TextView messy = (TextView) findViewById(R.id.textViewMessy);


        room.setText("Living room");

        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[0] + "'s needs");
        hungry.setText(petInfo[1]);
        thirsty.setText(petInfo[2]);
        boring.setText(petInfo[3]);
        lonely.setText(petInfo[4]);
        smelly.setText(petInfo[5]);
        messy.setText(petInfo[6]);

        pet = (ImageView) findViewById(R.id.PETIMAGE);
        pet.setImageResource(R.drawable.hestia_neutral);

        return;
    }
}