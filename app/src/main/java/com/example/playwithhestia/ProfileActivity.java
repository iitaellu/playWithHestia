package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    String personInfo = ".Profil.csv";
    String petInfo = ".petProfil.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();

        EditText username = (EditText) findViewById(R.id.userNameEditText);
        EditText email = (EditText) findViewById(R.id.emailEditText);
        EditText pet = (EditText) findViewById(R.id.petNameEditText);
        EditText password = (EditText) findViewById(R.id.passwordEditText);

        String[] userInfo = readFile(personInfo, person);
        username.setText(userInfo[2]);
        pet.setText(userInfo[3]);
        email.setText(fAuth.getCurrentUser().getEmail());

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

    //From the old project
    //With this method will updae pet information in the file
    public void writeFile(String petname, String person) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() +"/"+ person+petInfo, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            writer.append(petname+";\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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