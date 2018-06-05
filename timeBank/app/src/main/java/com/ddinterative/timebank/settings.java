package com.ddinterative.timebank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getOption();
        getTimes();
        getToggle();
    }

    public void back(View v){

        startActivity(new Intent(settings.this, timer.class));
    }

    public void secondsToggle(View v) {
        ToggleButton toggle = (ToggleButton) v;
        boolean state = toggle.isChecked();

        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        if (state) {
            editor.putString("sec", "on");
        }
        else {
            editor.putString("sec", "off");
        }

        editor.apply();
    }

    public void getToggle() {
        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

        String option = mSettings.getString("sec", "none");

        if (option.equals("on")) {
            ToggleButton box = (ToggleButton) findViewById(R.id.secToggle);
            box.setChecked(true);
        }
    }

    public void turnAddOn(View v) {
        CheckBox add = (CheckBox)v;
        add.setChecked(true);

        CheckBox sub = (CheckBox)findViewById(R.id.subCheck);
        sub.setChecked(false);

        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString("option","add");

        EditText startViewHr = (EditText)findViewById(R.id.added_time_hrs_edit);
        EditText startViewMin = (EditText)findViewById(R.id.added_time_min_edit);

        String startHr = startViewHr.getText().toString();
        String startMin = startViewMin.getText().toString();

        editor.putInt("timeHr",Integer.parseInt(startHr));
        editor.putInt("timeMin",Integer.parseInt(startMin));

        editor.putLong("totalMilliseconds",Integer.parseInt(startHr)*60*60*1000 + Integer.parseInt(startMin)*60*1000);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        int date = localCalendar.get(Calendar.DATE);

        editor.putInt("Day",date);

        editor.apply();
    }

    public void turnSubOn(View v) {
        CheckBox sub = (CheckBox)v;
        sub.setChecked(true);

        CheckBox add = (CheckBox)findViewById(R.id.addCheck);
        add.setChecked(false);

        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString("option","sub");

        EditText startViewHr = (EditText)findViewById(R.id.initial_time_hrs_edit);
        EditText startViewMin = (EditText)findViewById(R.id.initial_time_mins_edit);

        String startHr = startViewHr.getText().toString();
        String startMin = startViewMin.getText().toString();

        editor.putInt("timeHr",Integer.parseInt(startHr));
        editor.putInt("timeMin",Integer.parseInt(startMin));

        editor.putLong("totalMilliseconds",Integer.parseInt(startHr)*60*60*1000 + Integer.parseInt(startMin)*60*1000);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        int date = localCalendar.get(Calendar.DATE);

        editor.putInt("Day",date);

        editor.apply();
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

    public void setAddTimes(View v) {
        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        EditText addedTimeHrs = (EditText)findViewById(R.id.added_time_hrs_edit);
        EditText addedTimeMins = (EditText)findViewById(R.id.added_time_min_edit);
        EditText maxTimeHrs = (EditText)findViewById(R.id.max_time_hrs_edit);
        EditText maxTimeMins = (EditText)findViewById(R.id.max_time_mins_edit);

        String addedTimeHrsVal = addedTimeHrs.getText().toString();
        String addedTimeMinsVal = addedTimeMins.getText().toString();
        String maxTimeHrsVal = maxTimeHrs.getText().toString();
        String maxTimeMinsVal = maxTimeMins.getText().toString();

        editor.putString("addedTimeHrs",addedTimeHrsVal);
        editor.putString("addedTimeMins",addedTimeMinsVal);
        editor.putString("maxTimeHrs",maxTimeHrsVal);
        editor.putString("maxTimeMins",maxTimeMinsVal);

        editor.putString("startTimeHrs",addedTimeHrsVal);
        editor.putString("startTimeMins",addedTimeMinsVal);

        editor.apply();

        View addView = findViewById(R.id.addCheck);
        turnAddOn(addView);
    }

    public void setSubTimes(View v) {
        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        EditText initialTimeHrs = (EditText)findViewById(R.id.initial_time_hrs_edit);
        EditText initialTimeMins = (EditText)findViewById(R.id.initial_time_mins_edit);
        EditText decayTimeHrs = (EditText)findViewById(R.id.decay_time_hrs_edit);
        EditText decayTimeMins = (EditText)findViewById(R.id.decay_time_mins_edit);
        EditText goalTimeHrs = (EditText)findViewById(R.id.goal_time_hrs_edit);
        EditText goalTimeMins = (EditText)findViewById(R.id.goal_time_mins_edit);

        String initialTimeHrsVal = initialTimeHrs.getText().toString();
        String initialTimeMinsVal = initialTimeMins.getText().toString();
        String decayTimeHrsVal = decayTimeHrs.getText().toString();
        String decayTimeMinsVal = decayTimeMins.getText().toString();
        String goalTimeHrsVal = goalTimeHrs.getText().toString();
        String goalTimeMinsVal = goalTimeMins.getText().toString();


        editor.putString("initialTimeHrs",initialTimeHrsVal);
        editor.putString("initialTimeMins",initialTimeMinsVal);
        editor.putString("decayTimeHrs",decayTimeHrsVal);
        editor.putString("decayTimeMins",decayTimeMinsVal);
        editor.putString("goalTimeHrs",goalTimeHrsVal);
        editor.putString("goalTimeMins",goalTimeMinsVal);

        editor.putString("startTimeHrs",initialTimeHrsVal);
        editor.putString("startTimeMins",initialTimeMinsVal);

        editor.apply();

        View subView = findViewById(R.id.subCheck);
        turnSubOn(subView);
    }

    public void getTimes() {
        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

        // get add times
        String addedTimeHrs = mSettings.getString("addedTimeHrs", "1");
        String addedTimeMins = mSettings.getString("addedTimeMins", "1");
        String maxTimeHrs = mSettings.getString("maxTimeHrs", "1");
        String maxTimeMins = mSettings.getString("maxTimeMins", "1");

        // get sub times
        String initialTimeHrs = mSettings.getString("initialTimeHrs", "1");
        String initialTimeMins = mSettings.getString("initialTimeMins", "1");
        String decayTimeHrs = mSettings.getString("decayTimeHrs", "1");
        String decayTimeMins = mSettings.getString("decayTimeMins", "1");
        String goalTimeHrs = mSettings.getString("goalTimeHrs", "1");
        String goalTimeMins = mSettings.getString("goalTimeMins", "1");

        // get add views
        EditText addedTimeHrsView = (EditText)findViewById(R.id.added_time_hrs_edit);
        EditText addedTimeMinsView = (EditText)findViewById(R.id.added_time_min_edit);
        EditText maxTimeHrsView = (EditText)findViewById(R.id.max_time_hrs_edit);
        EditText maxTimeMinsView = (EditText)findViewById(R.id.max_time_mins_edit);

        // get sub views
        EditText initialTimeHrsView = (EditText)findViewById(R.id.initial_time_hrs_edit);
        EditText initialTimeMinsView = (EditText)findViewById(R.id.initial_time_mins_edit);
        EditText decayTimeHrsView = (EditText)findViewById(R.id.decay_time_hrs_edit);
        EditText decayTimeMinsView = (EditText)findViewById(R.id.decay_time_mins_edit);
        EditText goalTimeHrsView = (EditText)findViewById(R.id.goal_time_hrs_edit);
        EditText goalTimeMinsView = (EditText)findViewById(R.id.goal_time_mins_edit);

        //set add times
        addedTimeHrsView.setText(addedTimeHrs);
        addedTimeMinsView.setText(addedTimeMins);
        maxTimeHrsView.setText(maxTimeHrs);
        maxTimeMinsView.setText(maxTimeMins);

        //set sub times
        initialTimeHrsView.setText(initialTimeHrs);
        initialTimeMinsView.setText(initialTimeMins);
        decayTimeHrsView.setText(decayTimeHrs);
        decayTimeMinsView.setText(decayTimeMins);
        goalTimeHrsView.setText(goalTimeHrs);
        goalTimeMinsView.setText(goalTimeMins);


    }

    public void getOption() {
        SharedPreferences mSettings = settings.this.getSharedPreferences("TimeSettings", Context.MODE_PRIVATE);

        String option = mSettings.getString("option", "none");

        if (option.equals("add")) {
            CheckBox box = (CheckBox)findViewById(R.id.addCheck);
            box.setChecked(true);
        }
        else if (option.equals("sub")) {
            CheckBox box = (CheckBox)findViewById(R.id.subCheck);
            box.setChecked(true);
        }

    }
}
