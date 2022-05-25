package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class BathroomActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";
    ImageView pet;
    TextView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);
        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);
        setView();

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
                //Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                //Following popUpMenu part from https://www.javatpoint.com/android-popup-menu-example
                PopupMenu menu = new PopupMenu(getApplicationContext(), settings);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch(menuItem.getItemId()){
                            case R.id.set:
                                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                                return true;

                            case R.id.logout:
                                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(startIntent);
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
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

        ImageView socialise = (ImageView) findViewById(R.id.speak3);
        socialise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Socialise", Toast.LENGTH_LONG).show();
            }
        });
        ImageView bath = (ImageView) findViewById(R.id.bath);
        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pet.setImageResource(R.drawable.hestia_bath);
                ImageView ba = (ImageView) findViewById(R.id.bath);
                ba.setImageResource(R.drawable.empty);
                chat.setText("Purr...");
                //nex counDownTimer part is partly from https://www.codegrepper.com/code-examples/java/countdown+timer+android+studio
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.hestia_neutral);
                        chat.setText(":)");
                        ba.setImageResource(R.drawable.bath);
                    }
                }.start();
            }
        });
        ImageView box = (ImageView) findViewById(R.id.box);
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

    public void setView(){
        TextView room = (TextView) findViewById(R.id.room);

        chat = (TextView) findViewById(R.id.petChatTextView3);
        pet = (ImageView) findViewById(R.id.PETIMAGE3);
        setNeeds();


        room.setText("Bathroom");

        Calendar now = Calendar.getInstance();
        int hours = now.get(Calendar.HOUR_OF_DAY);

        if (hours >= 21 || hours <=6){
            pet.setImageResource(R.drawable.hestia_sleeping);
            chat.setText("Zzz...");

        }
        else {
            pet.setImageResource(R.drawable.hestia_neutral);
            chat.setText("Hello!");
        }

        return;
    }

    public void setNeeds(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameBath);
        TextView hungry = (TextView) findViewById(R.id.textViewHungry3);
        TextView thirsty = (TextView) findViewById(R.id.textViewThirsty3);
        TextView boring = (TextView) findViewById(R.id.textViewBoring3);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely3);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly3);
        TextView messy = (TextView) findViewById(R.id.textViewMessy3);
        Integer hunb, thirb, borb, lonb, smelb, mesb;

        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[1] + "'s needs");
        hunb = Integer.parseInt(petInfo[2]);
        hunb = (hunb*100/20);
        hungry.setText(hunb+"%");

        thirb = Integer.parseInt(petInfo[3]);
        thirb = (thirb*100/20);
        thirsty.setText(thirb+"%");

        borb = Integer.parseInt(petInfo[4]);
        borb = (borb*100/20);
        boring.setText(borb+"%");

        lonb = Integer.parseInt(petInfo[5]);
        lonb = (lonb*100/20);
        lonely.setText(lonb+"%");

        smelb = Integer.parseInt(petInfo[6]);
        smelb = (smelb*100/10);
        smelly.setText(smelb+"%");

        mesb = Integer.parseInt(petInfo[7]);
        mesb = (mesb*100/10);
        messy.setText(mesb+"%");
    }
}