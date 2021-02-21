package com.example.organizerrolnika;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PowiadomieniaActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_godzina;
    private Button btn_data;
    private Button btn_ustaw;
    private EditText edittext_wiadomosc;
    String timeTonotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powiadomienia);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Tworzenie powiadomienia");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        btn_godzina = findViewById(R.id.btn_godzina);
        btn_data = findViewById(R.id.btn_data);
        btn_ustaw = findViewById(R.id.btn_ustaw);
        edittext_wiadomosc = findViewById(R.id.edittext_wiadomosc);
        btn_godzina.setOnClickListener(this);
        btn_data.setOnClickListener(this);
        btn_ustaw.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_godzina){
            selectTime();
        }
        if (v == btn_data){
            selectDate();
        }
        if (v == btn_ustaw){
            submit();
        }

    }

    //dodanie powiadomienia
    private void submit(){
        String text = edittext_wiadomosc.getText().toString().trim();
        if (text.isEmpty()){
            Toast.makeText(this, "Proszę wpisać wiadomość", Toast.LENGTH_SHORT).show();
        }else{
            if (btn_godzina.getText().toString().equals("Wybierz godzine") || btn_data.getText().toString().equals("Wybierz date")){
                Toast.makeText(this, "Proszę wybrać datę i godzinę", Toast.LENGTH_SHORT).show();
            }else{
                String value = (edittext_wiadomosc.getText().toString().trim());
                String date = (btn_data.getText().toString().trim());
                String time = (btn_godzina.getText().toString().trim());
                setAlarm(value, date, time);
                Toast.makeText(this, "Wydarzenie zostało zapisane", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //ustawienie godziny powiadomienia
    private void selectTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeTonotify = hourOfDay + ":" + minute;
                btn_godzina.setText(FormatTime(hourOfDay,minute));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    //ustawienie daty powiadomienia
    private void selectDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btn_data.setText(dayOfMonth + "-" + (month+1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    //wyswietlanie formatu czasu
    public String FormatTime(int hour, int minute){

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0){
            formattedMinute = "0" + minute;
        }else {
            formattedMinute = "" + minute;
        }

        if (hour == 0){
            time = "24" + ":" + formattedMinute + " ";
        }else if (hour < 24){
            time = hour + ":" + formattedMinute + " ";
        }else if (hour == 24){
            time = "24" + ":" + formattedMinute + " ";
        }else{
            int temp = hour - 24;
            time = temp + ":" + formattedMinute + " ";
        }

        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edittext_wiadomosc.setText(text.get(0));
            }
        }
    }

    private void setAlarm(String text, String date, String time){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try{
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
        }catch(ParseException e){
            e.printStackTrace();
        }
        finish();
    }
}
