package com.example.playwithhestia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ToyDetails extends AppCompatActivity {

    ListView toyList;
    String[] toys;
    String[] details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://www.youtube.com/watch?v=rdGpT1pIJlw
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tou_details);

        Resources res = getResources();
        toyList = (ListView) findViewById(R.id.toyListView);
        toys = res.getStringArray(R.array.toyList);
        details = res.getStringArray(R.array.detail);

        ToyAdapter adapter = new ToyAdapter(getApplication(), toys, details);
        toyList.setAdapter(adapter);

        toyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showDetail = new Intent(getApplicationContext(), ToyInfo.class);
                showDetail.putExtra("com.example.TOY_INDEX", i);
                startActivity(showDetail);
            }
        });

    }
}