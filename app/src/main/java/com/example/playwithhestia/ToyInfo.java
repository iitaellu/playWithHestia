package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ToyInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_info);

        //https://www.youtube.com/watch?v=rdGpT1pIJlw
        Intent in = getIntent();
        int index = in.getIntExtra("com.example.TOY_INDEX", -1);

        if (index > -1){
            int pic = getImg(index);
            String txt = getInfo(index);
            String name = getName(index);
            TextView toyName = (TextView) findViewById(R.id.toyNameInfo);
            TextView info = (TextView) findViewById(R.id.info);
            ImageView img = (ImageView) findViewById(R.id.toyPicture);
            img.setImageResource(pic);
            toyName.setText(name);
            info.setText(txt);
        }

        Button back = (Button) findViewById(R.id.backInfo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), LivingRoomActivity.class);
                startActivity(startIntent);
            }
        });

    }

    //https://www.youtube.com/watch?v=rdGpT1pIJlw
    private int getImg(int index){
        switch (index){
            case 0:
                return R.drawable.mouce;
            case 1:
                return R.drawable.feather_toy;
            default:
                return -1;
        }
    }

    private String getName(int index){
        switch (index){
            case 0:
                return "Mouse";
            case 1:
                return "Feather toy";
            default:
                return null;
        }
    }

    private String getInfo(int index){
        switch (index){
            case 0:
                return "With this toy your pet can play alone and consume energy. With this toy, which look much like real mouse, your adorable kitty can train it's hunter skills with fun way!";
            case 1:
                return "With this toy you can play with your cat together and this way is for kitty even funnier, because it can play with you. Those colorful feathers has very hypnotic effect to the cat!";
            default:
                return null;
        }
    }
}