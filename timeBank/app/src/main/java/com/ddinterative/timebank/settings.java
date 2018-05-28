package com.ddinterative.timebank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void back(View v){
        startActivity(new Intent(settings.this, timer.class));
    }

    public void openAdd(View v){
        View add = findViewById(R.id.add_settings);
        View sub = findViewById(R.id.sub_settings);

        sub.setVisibility(View.GONE);
        if(add.getVisibility() == View.GONE)
            add.setVisibility(View.VISIBLE);
        else
            add.setVisibility(View.GONE);
    }

    public void openSub(View v){
        View add = findViewById(R.id.add_settings);
        View sub = findViewById(R.id.sub_settings);

        add.setVisibility(View.GONE);
        if(sub.getVisibility() == View.GONE)
            sub.setVisibility(View.VISIBLE);
        else
            sub.setVisibility(View.GONE);
    }
}
