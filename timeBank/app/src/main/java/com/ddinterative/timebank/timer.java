package com.ddinterative.timebank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;

public class timer extends AppCompatActivity {
    private Boolean pause = false;
    private long milliseconds;
    private long currentMilliseconds = 0;
    private long hrsT;
    private long minsT;
    private long secsT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        setTimer();

        SharedPreferences mSettings = timer.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        final String secsOn = mSettings.getString("sec", "on");

        final ImageButton start_timer=(ImageButton)this.findViewById(R.id.start);
        final ImageButton stop_timer=(ImageButton)this.findViewById(R.id.stop);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mSettings = timer.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

                v.setVisibility(View.GONE);
                stop_timer.setVisibility(View.VISIBLE);

                Integer hrs = mSettings.getInt("timeHr",1);
                Integer mins = mSettings.getInt("timeMin",1);
                Integer secs = mSettings.getInt("timeSec",1);

                currentMilliseconds = hrs * 60 * 60 * 1000 + mins * 60 * 1000+ secs * 1000;

                pause = false;

                CountDownTimer myCountDownTimer = new CountDownTimer(currentMilliseconds, 1000) {

                    TextView timer = (TextView)findViewById(R.id.timer);

                    ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar);

                    long mil;

                    int count = 0;

                    public void onTick(long currentMil) {

                        hrsT = TimeUnit.MILLISECONDS.toHours(currentMil);
                        mil = currentMil - hrsT*60*60*1000;
                        minsT = TimeUnit.MILLISECONDS.toMinutes(mil);
                        mil = mil- minsT*60*1000;
                        secsT = TimeUnit.MILLISECONDS.toSeconds(mil);



                        if(secsOn.equals("off")) {
                            if (minsT < 10) {
                                timer.setText(hrsT + ":0" + minsT);
                            } else {
                                timer.setText(hrsT + ":" + minsT);
                            }
                        }
                        else {
                            if (minsT < 10 && secsT < 10) {
                                timer.setText(hrsT + ":0" + minsT + ":0" + secsT);
                            } else if (minsT < 10) {
                                timer.setText(hrsT + ":0" + minsT + ":" + secsT);
                            } else if (secsT < 10) {
                                timer.setText(hrsT + ":" + minsT + ":0" + secsT);
                            } else {
                                timer.setText(hrsT + ":" + minsT + ":" + secsT);
                            }
                        }

                        long remaining = 100 - currentMil*100/milliseconds;
                        progress.setProgress((int)remaining);

                        if (pause) {
                            this.pause(currentMil);
                        }

                        if (count == 10) {
                            save();
                            count = 0;
                        }
                        count = count + 1;

                    }

                    public void pause(long currentMil) {
                        currentMilliseconds = currentMil;
                        save();
                        this.cancel();
                    }

                    public void onFinish() {

                        progress.setProgress(100);

                    }
                }.start();
            }
        });

        stop_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause = true;
                v.setVisibility(View.GONE);
                start_timer.setVisibility(View.VISIBLE);
            }
        });

    }

    public void toSettings(View v){
        startActivity(new Intent(timer.this, settings.class));
    }

    public void setTimer() {
        SharedPreferences mSettings = timer.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

        TextView timer = (TextView)findViewById(R.id.timer);
        ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        int date = localCalendar.get(Calendar.DATE);
        int initalDate = mSettings.getInt("Day", date);
        int daysPassed = date - initalDate;

        Integer TimeHrs = mSettings.getInt("timeHr", 1);
        Integer TimeMins = mSettings.getInt("timeMin", 0);
        Integer TimeSecs = mSettings.getInt("timeSec", 0);

        String option = mSettings.getString("option","add");

        if (daysPassed == 0){

            String secsOn = mSettings.getString("sec", "on");

            if(secsOn.equals("off")) {
                if (TimeMins < 10) {
                    timer.setText(TimeHrs + ":0" + TimeMins);
                } else {
                    timer.setText(TimeHrs + ":" + TimeMins);
                }
            }
            else {
                if (TimeMins < 10 && TimeSecs < 10) {
                    timer.setText(TimeHrs + ":0" + TimeMins + ":0" + TimeSecs);
                } else if (TimeMins < 10) {
                    timer.setText(TimeHrs + ":0" + TimeMins + ":" + TimeSecs);
                } else if (TimeSecs < 10) {
                    timer.setText(TimeHrs + ":" + TimeMins + ":0" + TimeSecs);
                } else {
                    timer.setText(TimeHrs + ":" + TimeMins + ":" + TimeSecs);
                }
            }

            milliseconds = mSettings.getLong("totalMilliseconds",TimeHrs*60*60*1000+TimeMins*60*1000);
            long current = TimeHrs*60*60*1000 + TimeMins * 60 *1000 + TimeSecs * 1000;
            long remaining = 100 - current*100/milliseconds;
            progress.setProgress((int)remaining);
        }
        else {
            if (option.equals("add")) {
                String addedTimeHrs = mSettings.getString("addedTimeHrs", "1");
                String addedTimeMins = mSettings.getString("addedTimeMins", "1");
                String maxTimeHrs = mSettings.getString("maxTimeHrs", "1");
                String maxTimeMins = mSettings.getString("maxTimeMins", "1");

                int hrs = Integer.parseInt(addedTimeHrs) * daysPassed + TimeHrs;
                int mins = Integer.parseInt(addedTimeMins) * daysPassed + TimeMins;
                int secs = TimeSecs;

                if (hrs > Integer.parseInt(maxTimeHrs)) {
                    hrs = Integer.parseInt(maxTimeHrs);
                }

                if (hrs >= Integer.parseInt(maxTimeHrs) && mins > Integer.parseInt(maxTimeMins)) {
                    mins = Integer.parseInt(maxTimeMins);
                }

                milliseconds = hrs*60*60*1000+mins*60*1000;

                String secsOn = mSettings.getString("sec", "on");

                if(secsOn.equals("off")) {
                    if (mins < 10) {
                        timer.setText(hrs + ":0" + mins);
                    } else {
                        timer.setText(hrs + ":" + mins);
                    }
                }
                else {

                    if (TimeMins < 10 && TimeSecs < 10) {
                        timer.setText(hrs + ":0" + mins + ":0" + secs);
                    } else if (TimeMins < 10) {
                        timer.setText(hrs + ":0" + mins + ":" + secs);
                    } else if (TimeSecs < 10) {
                        timer.setText(hrs + ":" + mins + ":0" + secs);
                    } else {
                        timer.setText(hrs + ":" + mins + ":" + secs);
                    }
                }

                hrsT = hrs;
                minsT = mins;
                secsT = secs;

                save();

                mSettings.edit().putInt("Day",date);

                mSettings.edit().apply();


            } else {

                String decayTimeHrs = mSettings.getString("decayTimeHrs", "1");
                String decayTimeMins = mSettings.getString("decayTimeMins", "1");
                String goalTimeHrs = mSettings.getString("goalTimeHrs", "1");
                String goalTimeMins = mSettings.getString("goalTimeMins", "1");

                int hrs = TimeHrs - Integer.parseInt(decayTimeHrs) * daysPassed;
                int mins = TimeMins - Integer.parseInt(decayTimeMins) * daysPassed;
                int secs = TimeSecs;

                if(Integer.parseInt(goalTimeHrs) < hrs) {
                    hrs = Integer.parseInt(goalTimeHrs);
                }

                if(Integer.parseInt(goalTimeHrs) == hrs && Integer.parseInt(goalTimeMins) < mins ) {
                    mins = Integer.parseInt(goalTimeMins);
                }


                while(mins < 0){
                    mins = 60 - mins;
                    hrs = hrs - 1;
                }

                String secsOn = mSettings.getString("sec", "on");

                if(secsOn.equals("off")) {
                    if (mins < 9) {
                        timer.setText(hrs + ":0" + mins);
                    } else {
                        timer.setText(hrs + ":" + mins);
                    }
                }
                else {

                    if (TimeMins < 9 && TimeSecs < 9) {
                        timer.setText(hrs + ":0" + mins + ":0" + secs);
                    } else if (TimeMins < 9) {
                        timer.setText(hrs + ":0" + mins + ":" + secs);
                    } else if (TimeSecs < 9) {
                        timer.setText(hrs + ":" + mins + ":0" + secs);
                    } else {
                        timer.setText(hrs + ":" + mins + ":" + secs);
                    }
                }

                hrsT = hrs;
                minsT = mins;
                secsT = secs;

                save();

                mSettings.edit().putInt("Day",date);

                mSettings.edit().apply();
            }
        }
    }

    public void save() {
        SharedPreferences mSettings = timer.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putInt("timeHr",(int)hrsT);
        editor.putInt("timeMin",(int)minsT);
        editor.putInt("timeSec",(int)secsT);

        editor.apply();

    }

}




