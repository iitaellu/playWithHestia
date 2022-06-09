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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class KitchenActivity extends AppCompatActivity {
    String petFile = ".petProfil.csv";
    ImageView pet;
    TextView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView settings = (ImageView) findViewById(R.id.settings);
        pet = (ImageView) findViewById(R.id.PETIMAGE2);

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

        ImageButton livingroomIB = (ImageButton) findViewById(R.id.livingroomButton);
        livingroomIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);
            }
        });

        ImageView socialise = (ImageView) findViewById(R.id.speak2);
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
                writeFile(3,person,"2");
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.hestia_neutral);
                        setView();
                    }
                }.start();

            }
        });

        ImageView feed = (ImageView) findViewById(R.id.foodBowl);
        //ImageButton feed = (ImageButton) findViewById(R.id.feedIB);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pet.setImageResource(R.drawable.hestia_eat);
                ImageView fe = (ImageView) findViewById(R.id.foodBowl);
                fe.setImageResource(R.drawable.empty);
                chat.setText("Yammy! Purr...");
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(1,person,"20");
                //nex counDownTimer part is partly from https://www.codegrepper.com/code-examples/java/countdown+timer+android+studio
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        //pet.setImageResource(R.drawable.hestia);
                        fe.setImageResource(R.drawable.food_bowl);
                        setView();
                    }
                }.start();
            }
        });

        ImageView drink = (ImageView) findViewById(R.id.waterCup);
        //ImageButton drink = (ImageButton) findViewById(R.id.drinkIB);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pet.setImageResource(R.drawable.hestia_drink);
                ImageView dr = (ImageView) findViewById(R.id.waterCup);
                dr.setImageResource(R.drawable.empty);
                chat.setText("Slurp!");
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String person = fAuth.getCurrentUser().getUid();
                String[] petInfo = readFile(petFile,person);
                writeFile(2,person,"20");
                //nex counDownTimer part is partly from https://www.codegrepper.com/code-examples/java/countdown+timer+android+studio
                new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        pet.setImageResource(R.drawable.hestia);
                        dr.setImageResource(R.drawable.water_cup);
                        setView();
                    }
                }.start();            }
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

        int wellk = setNeeds();
        room.setText("Kitchen");

        Calendar nown = Calendar.getInstance();
        int hoursn = nown.get(Calendar.HOUR_OF_DAY);

        if (hoursn >= 21 || hoursn <=6){
            pet.setImageResource(R.drawable.hestia_sleeping);
            chat.setText("Zzz...");

        }
        else {
            setMood(wellk);
        }

    }

    public int setNeeds(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String person = fAuth.getCurrentUser().getUid();
        TextView header = (TextView) findViewById(R.id.petNameKitchen);
        TextView hungry = (TextView) findViewById(R.id.textViewThirsty2);
        TextView thirsty = (TextView) findViewById(R.id.textViewHungry2);
        TextView boring = (TextView) findViewById(R.id.textViewboring2);
        TextView lonely = (TextView) findViewById(R.id.textViewLonely2);
        TextView smelly = (TextView) findViewById(R.id.textViewSmelly2);
        TextView messy = (TextView) findViewById(R.id.textViewMessy2);
        TextView wellBeing = (TextView) findViewById(R.id.wellBeingmeter2);
        chat = (TextView) findViewById(R.id.petChatTextView2);
        pet = (ImageView) findViewById(R.id.PETIMAGE2);

        //upDate(person);

        Integer hunk, thirk, bork, lonk, smelk, mesk;
        Integer wellk = 0;
        String[] petInfo = readFile(petFile,person);
        header.setText(petInfo[2] + "'s needs");

        hunk = Integer.parseInt(petInfo[3]);
        wellk=hunk;
        hunk= (hunk*100/20);
        hungry.setText(hunk+"%");

        thirk = Integer.parseInt(petInfo[4]);
        wellk= wellk+thirk;
        thirk = (thirk*100/20);
        thirsty.setText(thirk+"%");

        bork = Integer.parseInt(petInfo[5]);
        wellk= wellk+bork;
        bork = (bork*100/20);
        boring.setText(bork+"%");

        lonk = Integer.parseInt(petInfo[6]);
        wellk= wellk+lonk;
        lonk = (lonk*100/20);
        lonely.setText(lonk+"%");

        smelk = Integer.parseInt(petInfo[7]);
        wellk= wellk+smelk;
        smelk = (smelk*100/10);
        smelly.setText(smelk+"%");

        mesk = Integer.parseInt(petInfo[8]);
        wellk= wellk+mesk;
        mesk = (mesk*100/10);
        messy.setText(mesk+"%");

        wellBeing.setText(wellk+"%");
        return wellk;
    }

    //This method write data in file. partly from old project
    public void writeFile(int id, String person, String edit) {
        try (FileWriter fw = new FileWriter(this.getFilesDir().getPath()+"/"+person+petFile, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            String[] petInfo = readFile(petFile,person);
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

            //Pet has got food
            if (id == 1){
                writer.append(date+";"+petInfo[2]+";"+edit+";"+petInfo[4]+";"+petInfo[5]+";"+petInfo[6]+";"+petInfo[7]+";"+petInfo[8]+";\n");
                writer.flush();
                writer.close();
            }
            //Pet has got water
            if (id == 2){
                writer.append(date+";"+petInfo[2]+";"+petInfo[3]+";"+edit+";"+petInfo[5]+";"+petInfo[6]+";"+petInfo[7]+";"+petInfo[8]+";\n");
                writer.flush();
                writer.close();
            }

            //When speack with pegt
            if (id == 3) {

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void setMood (int well) {
            pet = (ImageView) findViewById(R.id.PETIMAGE2);
            chat = (TextView) findViewById(R.id.petChatTextView2);
            if (well == 100) {
                pet.setImageResource(R.drawable.hestia);
                chat.setText("Purr meow!\n\n(Kitty looks happy)");
            }
            if (well >= 75 && well < 100) {
                pet.setImageResource(R.drawable.hestia_neutral);
                chat.setText("Meow!\n\n(Kitty looks to be fine)");
            }
            if (well >= 50 && well < 75) {
                pet.setImageResource(R.drawable.hestia_dissaponted);
                chat.setText("...\n\n(Kitty looks to be ok)");
            }
            if (well >= 25 && well < 50) {
                pet.setImageResource(R.drawable.hestia_sad);
                chat.setText("Yowl\n\n(Kitty looks to be really sad)");
            }
            if (well >= 0 && well < 25) {
                pet.setImageResource(R.drawable.hestia_mad);
                chat.setText("hiss\n\n(Kitty looks to be disappointed!)");
            }
        }





    /*//Follow code from https://www.youtube.com/watch?v=TpyRKom0X_s
    public void upDate(String person, String inputFile, String newfeed, String newDrink){
        String tempFile = "temp.csv";
        File oldFile = new File(getApplication().getFilesDir().getPath() + "/" + person + inputFile);
        File newFile = new File(tempFile);
        String ID = "1"; String date = ""; String time = ""; String name = "";
        String hun = ""; String thir = ""; String bor = "";
        String sos = ""; String sme = ""; String mes = "";

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            Scanner x = new Scanner(new File(getApplication().getFilesDir().getPath() + "/" + person + inputFile));
            x.useDelimiter(";\n");

            while(x.hasNext()){
                ID = x.next();
                date = x.next();
                time = x.next();
                name = x.next();
                hun = x.next();
                thir = x.next();
                bor = x.next();
                sos = x.next();
                sme = x.next();
                mes = x.next();

                if (ID.equals("1")){
                    pw.println(ID+";"+date+";"+time+";"+name+";"+newfeed+";"+newDrink+";"+bor+";"+sos+";"+sme+";"+mes+";");
                }
                else{
                    pw.println(ID+";"+date+";"+time+";"+name+";"+hun+";"+thir+";"+bor+";"+sos+";"+sme+";"+mes+";");
                }
            }

            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (person+inputFile);
            newFile.renameTo(dump);

        } catch (IOException e) {
            System.out.println("Error!");
        }

    }*/
}