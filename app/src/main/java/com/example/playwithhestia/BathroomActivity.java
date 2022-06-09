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

public class BathroomActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";
    ImageView pet;
    TextView chat;
    ImageView box;

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
                                Intent startIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                                startActivity(startIntent);
                                return true;

                            case R.id.logout:
                                Intent startIntents = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(startIntents);
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
                //Random code part from https://www.youtube.com/watch?v=7zlVvtcMceU
                Random random = new Random();
                int val = random.nextInt(3);
                if (val == 0){
                    Toast.makeText(getApplicationContext(), "How are You doing?", Toast.LENGTH_LONG).show();
                    chat.setText("Meow meow!");
                    pet.setImageResource(R.drawable.meow_speack);
                }

                if (val == 1){
                    Toast.makeText(getApplicationContext(), "Who is pretty?", Toast.LENGTH_LONG).show();
                    chat.setText("Purr me!");
                    pet.setImageResource(R.drawable.meow_speack);
                }

                if (val == 2){
                    Toast.makeText(getApplicationContext(), "You are adorable", Toast.LENGTH_LONG).show();
                    chat.setText("Purr...Meow!");
                    pet.setImageResource(R.drawable.meow_speack);
                }
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(3,person,"2");
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.meow_neutral);
                        setView();
                    }
                }.start();

            }
        });
        ImageView bath = (ImageView) findViewById(R.id.bath);
        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pet.setImageResource(R.drawable.meow_bath);
                ImageView ba = (ImageView) findViewById(R.id.bath);
                ba.setImageResource(R.drawable.empty);
                chat.setText("Purr...");
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(1,person,"10");
                //nex counDownTimer part is partly from https://www.codegrepper.com/code-examples/java/countdown+timer+android+studio
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        ba.setImageResource(R.drawable.bath);
                        setView();
                    }
                }.start();
            }
        });
        box = (ImageView) findViewById(R.id.box);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setText("Purr...meow");
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(2,person,"10");
                setNeeds();
                setSandBox();
            }
        });
    }

    //From the old project https://github.com/iitaellu/Harkkatyo
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

    //This method build view
    public void setView(){
        TextView room = (TextView) findViewById(R.id.room);

        chat = (TextView) findViewById(R.id.petChatTextView3);
        pet = (ImageView) findViewById(R.id.PETIMAGE3);
        int wellb = setNeeds();
        setSandBox();

        room.setText("Bathroom");

        Calendar now = Calendar.getInstance();
        int hours = now.get(Calendar.HOUR_OF_DAY);

        if (hours >= 21 || hours <=6){
            pet.setImageResource(R.drawable.meow_sleeping);
            chat.setText("Zzz...");

        }
        else {
            setMood(wellb);
        }

        return;
    }

    //Method sets need point's into view
    public int setNeeds(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameBath);
        TextView hungry = (TextView) findViewById(R.id.textViewHungry3);
        TextView thirsty = (TextView) findViewById(R.id.textViewThirsty3);
        TextView boring = (TextView) findViewById(R.id.textViewBoring3);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely3);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly3);
        TextView messy = (TextView) findViewById(R.id.textViewMessy3);
        TextView wellBeing = (TextView) findViewById(R.id.wellBeingmeter3);
        Integer hunb, thirb, borb, lonb, smelb, mesb;
        Integer wellb = 0;

        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[2] + "'s needs");
        hunb = Integer.parseInt(petInfo[3]);
        wellb = hunb;
        hunb = (hunb*100/20);
        hungry.setText(hunb+"%");

        thirb = Integer.parseInt(petInfo[4]);
        wellb=wellb+thirb;
        thirb = (thirb*100/20);
        thirsty.setText(thirb+"%");

        borb = Integer.parseInt(petInfo[5]);
        wellb=wellb+borb;
        borb = (borb*100/20);
        boring.setText(borb+"%");

        lonb = Integer.parseInt(petInfo[6]);
        wellb=wellb+lonb;
        lonb = (lonb*100/20);
        lonely.setText(lonb+"%");

        smelb = Integer.parseInt(petInfo[7]);
        wellb=wellb+smelb;
        smelb = (smelb*100/10);
        smelly.setText(smelb+"%");

        mesb = Integer.parseInt(petInfo[8]);
        wellb=wellb+mesb;
        mesb = (mesb*100/10);
        messy.setText(mesb+"%");

        wellBeing.setText(wellb+"%");
        return wellb;
    }

    //Method sets right picture of sandbox according to the smelly need
    public void setSandBox(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        String[] petInfo = readFile(petFile,person);
        int litter = Integer.parseInt(petInfo[8]);
        box = (ImageView) findViewById(R.id.box);
        if (litter < 4){
            box.setImageResource(R.drawable.box_threedayold);
        }
        if(litter < 8 && litter >= 4){
            box.setImageResource(R.drawable.box_twodayold);
        }
        if (litter >= 8 && litter < 10){
            box.setImageResource(R.drawable.bax_dayold);
        }
        if (litter == 10){
            box.setImageResource(R.drawable.bax_clean);
        }
    }

    //partly from old project https://github.com/iitaellu/Harkkatyo
    public void writeFile(int id, String person, String edit) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath() + "/" + person + petFile, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            String[] petInfo = readFile(petFile, person);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy;HH:mm");
            String date = format.format(calendar.getTime());

            int sos = Integer.parseInt(petInfo[6]);

            //Pet has got bath
            if (id == 1) {
                writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + petInfo[6] + ";" + edit + ";" + petInfo[8] + ";\n");
                writer.flush();
                writer.close();
            }
            //litter box get cleaning
            if (id == 2) {
                writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + petInfo[6] + ";" + petInfo[7] + ";" + edit + ";\n");
                writer.flush();
                writer.close();
            }

            //When speak with pet
            if (id == 3) {

                int add = Integer.parseInt(edit);
                sos = sos+add;

                if (add <= 20){
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + sos + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                }
                else{
                    writer.append(date + ";" + petInfo[2] + ";" + petInfo[3] + ";" + petInfo[4] + ";" + petInfo[5] + ";" + "20" + ";" + petInfo[7] + ";" + petInfo[8] + ";\n");
                    writer.flush();
                    writer.close();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method set right picture of cat according to the wellbeingmeter
    public void setMood (int wellb) {
        pet = (ImageView) findViewById(R.id.PETIMAGE3);
        chat = (TextView) findViewById(R.id.petChatTextView3);
        if (wellb == 100) {
            pet.setImageResource(R.drawable.meow);
            chat.setText("Purr meow!\n\n(Kitty looks happy)");
        }
        if (wellb >= 75 && wellb < 100) {
            pet.setImageResource(R.drawable.meow_neutral);
            chat.setText("Meow!\n\n(Kitty looks to be fine)");
        }
        if (wellb >= 50 && wellb < 75) {
            pet.setImageResource(R.drawable.meow_dissaponted);
            chat.setText("...\n\n(Kitty looks to be ok)");
        }
        if (wellb >= 25 && wellb < 50) {
            pet.setImageResource(R.drawable.meow_sad);
            chat.setText("Yowl\n\n(Kitty looks to be really sad)");
        }
        if (wellb >= 0 && wellb< 25) {
            pet.setImageResource(R.drawable.meow_mad);
            chat.setText("hiss\n\n(Kitty looks to be disappointed!)");
        }
    }
}