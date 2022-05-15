package com.example.playwithhestia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignInActivity extends AppCompatActivity {
    String fileName = ".Profil.csv";
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Old code from old project
        EditText username = (EditText) findViewById(R.id.editUserName);
        EditText email = (EditText) findViewById(R.id.editEmail);
        EditText password = (EditText) findViewById(R.id.editPassword);

        FirebaseAuth fAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        //ProgressBar progressBar =(ProgressBar) findViewById(R.id.rogressBar);
        FirebaseFirestore fStore = (FirebaseFirestore) FirebaseFirestore.getInstance();

        Button backButton = (Button) findViewById(R.id.backButtonSignIn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String name = username.getText().toString().trim();
                String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{12,}";

                if (TextUtils.isEmpty(name)) {
                    username.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                    return;
                }
                //Should check the password is good
                if (!pass.matches(pattern)) {
                    //This check if the password is valid or not
                    password.setError("Password must have at least: 12 character, one uppercase, one number and one symbol");
                    System.out.println(pass.matches(pattern));
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                //Sign in user in farebase
                fAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                            //add user firestore
                            String userID = fAuth.getCurrentUser().getUid();
                            createFile(userID);
                            writeFile(name, userID);
                            documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Username", name);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for" + documentReference.getId());
                                }
                            });
                            Intent startIntent = new Intent(getApplicationContext(), ChoosePetActivity.class);
                            startActivity(startIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }

    public void createFile(String person){
        try {
            String content = "Name;Pet;\n";
            File file = new File(this.getFilesDir().getPath()+"/"+person+fileName);

            if(!file.exists()){
                file.createNewFile();
                System.out.println(file);
                OutputStreamWriter writer = new OutputStreamWriter(this.openFileOutput(person+fileName, Context.MODE_PRIVATE));
                writer.write(content);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void writeFile(String newname, String person) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() +"/"+ person+fileName, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            writer.append(newname+";");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
//End of the old code