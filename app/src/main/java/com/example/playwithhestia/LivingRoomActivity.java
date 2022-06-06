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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class LivingRoomActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";
    int count=0;
    ImageView pet;
    TextView chat;

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
            //Following popUpMenu part from https://www.javatpoint.com/android-popup-menu-example
            //And part from https://www.youtube.com/watch?v=MCeWm8qu0sw
            PopupMenu menu = new PopupMenu(getApplicationContext(), settings);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch(menuItem.getItemId()){
                        case R.id.set:
                            Intent startIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(startIntent);
                            return true;

                        case R.id.logout:
                            Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(Intent);
                            return true;
                    }
                    return false;
                }
            });
            menu.show();
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

        ImageView socialise = (ImageView) findViewById(R.id.speak);
        socialise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Random code part from https://www.youtube.com/watch?v=7zlVvtcMceU
                Random random = new Random();
                int val = random.nextInt(3);
                if (val == 0){
                    Toast.makeText(getApplicationContext(), "How are You doing?", Toast.LENGTH_LONG).show();
                    chat.setText("Meow meow!");
                    pet.setImageResource(R.drawable.hestia_speack);
                }

                if (val == 1){
                    Toast.makeText(getApplicationContext(), "Who is pretty?", Toast.LENGTH_LONG).show();
                    chat.setText("Purr me!");
                    pet.setImageResource(R.drawable.hestia_speack);
                }

                if (val == 2){
                    Toast.makeText(getApplicationContext(), "You are adorable", Toast.LENGTH_LONG).show();
                    chat.setText("Purr...Meow!");
                    pet.setImageResource(R.drawable.hestia_speack);
                }
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(2,person,"2");
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.hestia_neutral);
                        setNeeds();
                    }
                }.start();

            }
        });

        ImageView toys = (ImageView) findViewById(R.id.toys);
        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Following popUpMenu part from https://www.javatpoint.com/android-popup-menu-example
                //And part from https://www.youtube.com/watch?v=MCeWm8qu0sw
                PopupMenu menuToy = new PopupMenu(getApplicationContext(), toys);
                menuToy.getMenuInflater().inflate(R.menu.play_menu, menuToy.getMenu());

                menuToy.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        FirebaseAuth fAuth = FirebaseAuth.getInstance();
                        String person = fAuth.getCurrentUser().getUid();
                        String[] petInfo = readFile(petFile,person);
                        ImageView to = (ImageView) findViewById(R.id.toys);
                        to.setImageResource(R.drawable.empty);

                        switch(menuItem.getItemId()){
                            case R.id.mouse:
                                chat.setText("...");
                                pet.setImageResource(R.drawable.hestia_play_mice);
                                writeFile(3,person,"2");
                                new CountDownTimer(5000, 100) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        pet.setImageResource(R.drawable.hestia_neutral);
                                        to.setImageResource(R.drawable.toys);
                                        setNeeds();
                                    }
                                }.start();
                                return true;

                            case R.id.feather:
                                chat.setText("Purr mreow!");
                                pet.setImageResource(R.drawable.hestia_play_featers);

                                writeFile(3,person,"4");
                                new CountDownTimer(5000, 100) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        pet.setImageResource(R.drawable.hestia_neutral);
                                        to.setImageResource(R.drawable.toys);
                                        setNeeds();
                                    }
                                }.start();
                                return true;

                            case R.id.details:
                                Intent startIntent = new Intent(getApplicationContext(), ToyDetails.class);
                                startActivity(startIntent);
                        }
                        return false;
                    }
                });
                menuToy.show();
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

        chat = (TextView) findViewById(R.id.petChatTextView);
        pet = (ImageView) findViewById(R.id.PETIMAGE);
        int well = setNeeds();

        room.setText("Living room");

        Calendar now = Calendar.getInstance();
        int hours = now.get(Calendar.HOUR_OF_DAY);

        if (hours >= 21 || hours <=6){
            pet.setImageResource(R.drawable.hestia_sleeping);
            chat.setText("Zzz...");

        }
        else {
            setMood(well);
        }

        return;
    }
    public int setNeeds(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameLiving);
        TextView hungry = (TextView) findViewById(R.id.textViewHungry);
        TextView thirsty = (TextView) findViewById(R.id.textViewThirsty);
        TextView boring = (TextView) findViewById(R.id.textViewBoring);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly);
        TextView messy = (TextView) findViewById(R.id.textViewMessy);
        TextView wellBeing = (TextView) findViewById(R.id.wellBeingmeter);
        Integer hunl, thirl, borl, lonl, smell, mesl;
        Integer welll = 0;

        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[2] + "'s needs");
        hunl = Integer.parseInt(petInfo[3]);
        welll = hunl;
        hunl = (hunl*100/20);
        hungry.setText(hunl+"%");

        thirl = Integer.parseInt(petInfo[4]);
        welll = welll+thirl;
        thirl = (thirl*100/20);
        thirsty.setText(thirl+"%");

        borl = Integer.parseInt(petInfo[5]);
        welll=welll+borl;
        borl = (borl*100/20);
        boring.setText(borl+"%");

        lonl = Integer.parseInt(petInfo[6]);
        welll = welll+lonl;
        lonl = (lonl*100/20);
        lonely.setText(lonl+"%");

        smell = Integer.parseInt(petInfo[7]);
        welll = welll+smell;
        smell = (smell*100/10);
        smelly.setText(smell+"%");

        mesl = Integer.parseInt(petInfo[8]);
        welll = welll+mesl;
        mesl = (mesl*100/10);
        messy.setText(mesl+"%");

        wellBeing.setText(welll+"%");
        return welll;
    }

    public void writeFile(int id, String person, String edit) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() + "/" + person + petFile, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            String[] petInfo = readFile(petFile, person);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy;HH:mm");
            String date = format.format(calendar.getTime());

            //for need updates
            int hung = Integer.parseInt(petInfo[3]);
            int thir = Integer.parseInt(petInfo[4]);
            //double bor = Double.parseDouble(petInfo[6]);
            int bor = Integer.parseInt(petInfo[6]);
            int sos = Integer.parseInt(petInfo[6]);
            int mess = Integer.parseInt(petInfo[8]);
            int smel = Integer.parseInt(petInfo[7]);
            int minus = Integer.parseInt(edit);

            //Pet has got bath
            if (id == 1) {
                writer.append(date + ";" + petInfo[2] + ";" + edit + ";" + petInfo[4] + ";" + petInfo[5] + ";" + petInfo[6] + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                writer.flush();
                writer.close();
            }
            //When speack with pegt
            if (id == 2) {

                int add = Integer.parseInt(edit);
                sos = sos + add;

                if (sos <= 20) {
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + sos + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                } else {
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + "20" + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                }
            }

            if (id == 3) {

                int add = Integer.parseInt(edit);
                bor = bor + add;

                if (bor <= 20) {
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + bor + ";" + petInfo[6] + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                } else {
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + "20" + ";" + petInfo[6] + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMood (int well){
        pet = (ImageView) findViewById(R.id.PETIMAGE);
        chat = (TextView) findViewById(R.id.petChatTextView);
        if (well == 100){
            pet.setImageResource(R.drawable.hestia);
            chat.setText("Purr meow!\n\n(Kitty looks happy)");
        }
        if (well >= 75 && well < 100){
            pet.setImageResource(R.drawable.hestia_neutral);
            chat.setText("Meow!\n\n(Kitty looks to be fine)");
        }
        if (well >= 50 && well < 75){
            pet.setImageResource(R.drawable.hestia_dissaponted);
            chat.setText("...\n\n(Kitty looks to be ok)");
        }
        if (well >= 25 && well < 50){
            pet.setImageResource(R.drawable.hestia_sad);
            chat.setText("Yowl\n\n(Kitty looks to be really sad)");
        }
        if (well >= 0 && well < 25){
            pet.setImageResource(R.drawable.hestia_mad);
            chat.setText("hiss\n\n(Kitty looks to be disappointed!)");
        }
    }
}