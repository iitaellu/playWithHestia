package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ChoosePetActivity extends AppCompatActivity {
    String fileName = ".Profil.csv";
    String petfilename = ".petProfil.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pet);

        EditText petName = (EditText) findViewById(R.id.editPetName);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        Button adoptButton = (Button) findViewById(R.id.adoptButton);
        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = petName.getText().toString().trim();
                String person = fAuth.getCurrentUser().getUid();

                if(TextUtils.isEmpty(name)){
                    name = "Hestia";
                }
                writeFile(name, person);
                createFile(name, person);
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);
            }
        });
    }

    //With this method will pet's name saved to the file
    public void writeFile(String petname, String person) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() +"/"+ person+fileName, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            writer.append(petname+";\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Create new file for users pet information
    public void createFile(String name, String person){
        try {
            String contentOne = "Name;Hungry;Thirsty;Boring;Socialise;Smelly;Messy;\n";
            String contentTwo= (name+";70%;50%;80%;80%;80%;100%;90%;\n");
            File file = new File(this.getFilesDir().getPath()+"/"+person+petfilename);

            if(!file.exists()){
                file.createNewFile();
                System.out.println(file);
                OutputStreamWriter writer = new OutputStreamWriter(this.openFileOutput(person+petfilename, Context.MODE_PRIVATE));
                writer.write(contentOne+contentTwo);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

