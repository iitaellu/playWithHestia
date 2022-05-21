package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BathroomActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);
        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);
        TextView room = (TextView) findViewById(R.id.room);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameBath);
        TextView hungry = (TextView) findViewById(R.id.textViewThirsty3);
        TextView thirsty = (TextView) findViewById(R.id.textViewHungry3);
        TextView boring = (TextView) findViewById(R.id.textViewBoring3);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely3);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly3);
        TextView messy = (TextView) findViewById(R.id.textViewMessy3);

        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[0] + "'s needs");
        hungry.setText(petInfo[1]);
        thirsty.setText(petInfo[2]);
        boring.setText(petInfo[3]);
        lonely.setText(petInfo[4]);
        smelly.setText(petInfo[5]);
        messy.setText(petInfo[6]);

        room.setText("Living room");

        ImageView pet = (ImageView) findViewById(R.id.PETIMAGE3);
        pet.setImageResource(R.drawable.hestia);

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

        ImageButton socialise = (ImageButton) findViewById(R.id.sosialicebutton3);
        socialise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Socialise", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton bath = (ImageButton) findViewById(R.id.bathButton);
        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "give bath", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton box = (ImageButton) findViewById(R.id.sandBoxButton);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "clean sandbox", Toast.LENGTH_LONG).show();
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
}