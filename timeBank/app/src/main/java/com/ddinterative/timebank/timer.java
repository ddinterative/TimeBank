package com.ddinterative.timebank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class timer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        setTimer();

    }

    public void toSettings(View v){
        startActivity(new Intent(timer.this, settings.class));
    }

    public void setTimer() {
        SharedPreferences mSettings = timer.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

        TextView timer = (TextView)findViewById(R.id.timer);

        int date = Calendar.DATE;
        int initalDate = mSettings.getInt("Day", date);
        int daysPassed = date - initalDate;

        String TimeHrs = mSettings.getString("timeHr", "1");
        String TimeMins = mSettings.getString("timeMin", "00");

        String option = mSettings.getString("option","add");

        if (daysPassed == 0){
            if (Integer.parseInt(TimeMins) < 9) {
                TimeMins = "0" + TimeMins;
                timer.setText(TimeHrs + ":" + TimeMins);
            }
            else {
                timer.setText(TimeHrs + ":" + TimeMins);
            }
        }
        else {
            if (option.equals("add")) {
                String addedTimeHrs = mSettings.getString("addedTimeHrs", "1");
                String addedTimeMins = mSettings.getString("addedTimeMins", "1");
                String maxTimeHrs = mSettings.getString("maxTimeHrs", "1");
                String maxTimeMins = mSettings.getString("maxTimeMins", "1");

                int hrs = Integer.parseInt(addedTimeHrs) * daysPassed + Integer.parseInt(TimeHrs);
                int mins = Integer.parseInt(addedTimeMins) * daysPassed + Integer.parseInt(TimeMins);

                if (hrs > Integer.parseInt(maxTimeHrs)) {
                    hrs = Integer.parseInt(maxTimeHrs);
                }

                if (hrs >= Integer.parseInt(maxTimeHrs) && mins > Integer.parseInt(maxTimeMins)) {
                    mins = Integer.parseInt(maxTimeMins);
                }

                String formattedHrs = String.valueOf(hrs);

                if (mins < 9) {
                    String formattedMins = "0" + String.valueOf(mins);
                    timer.setText(formattedHrs + ":" + formattedMins);
                } else {
                    String formattedMins = String.valueOf(mins);
                    timer.setText(formattedHrs + ":" + formattedMins);
                }


            } else {

            }
        }
    }

    public void startTime(View v) {

    }
}




