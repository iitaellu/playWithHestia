package com.example.playwithhestia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfile extends AppCompatActivity {

    String personInfo = ".Profil.csv";
    String petInfo = ".petProfil.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button back = (Button) findViewById(R.id.backButtonEdit);
        Button save = (Button) findViewById(R.id.saveButtonEdit);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.userNameEditText);
                EditText email = (EditText) findViewById(R.id.emailEditText);
                EditText petName = (EditText) findViewById(R.id.petNameEditText);
                EditText password = (EditText) findViewById(R.id.passWordEditText);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String person = fAuth.getCurrentUser().getUid();

                String profile[] = readFile(personInfo, person);
                //String pet[] = readFile(petInfo, person);

                String newName = name.getText().toString();
                Toast.makeText(getApplicationContext(), newName, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(newName)){
                    newName = profile[1];
                }
                String newPetName = petName.getText().toString();
                if (TextUtils.isEmpty(newPetName)){
                    newPetName = profile[2];
                }

                String newEmail = email.getText().toString();
                if (!newEmail.isEmpty()) {
                    //https://itnext.io/android-firebase-authentication-email-and-password-login-b06980cf864a
                    if (newEmail.contains("@gmail.com") || newEmail.contains("@hotmail.com")) {
                        user.updateEmail(newEmail);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Email has to be gmail or hotmail", Toast.LENGTH_SHORT).show();
                    }
                }

                String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{12,}";
                String newPass = password.getText().toString();

                if (!newPass.isEmpty()){
                    if(!newPass.matches(pattern)){
                        Toast.makeText(getApplicationContext(), "Password must have at least: 12 character, one uppercase, one number and one symbol", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        user.updatePassword(newPass);
                    }

                }

                writeFile(1, newName, newPetName,person,personInfo);
                writeFile(2, newName, newPetName,person,petInfo);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(startIntent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(startIntent);
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

    public void writeFile(int id, String newName, String pet, String person, String fileName) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() +"/"+ person+fileName, true)) {
            BufferedWriter writer = new BufferedWriter(fw);


            if (id == 1){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String date = format.format(calendar.getTime());
                writer.append(date+";"+newName+";"+pet+";\n");
                writer.flush();
                writer.close();
            }
            if (id == 2){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy;HH:mm");
                String date = format.format(calendar.getTime());
                String Info[] = readFile(petInfo, person);
                writer.append(date+";"+pet+";" + Info[3] + ";" + Info[4] + ";" + Info[5] + ";" + Info[6] + ";" + Info[7] + ";" + Info[8] + ";\n");
                writer.flush();
                writer.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}