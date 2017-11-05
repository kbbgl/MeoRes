package com.inc.kobbigal.meores;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class EventDetailActivity extends AppCompatActivity {

    ImageButton datetimeBtn;
    Date selectedDatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        datetimeBtn = findViewById(R.id.choose_time_btn);

        datetimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, final int yearSelected, final int monthSelected, final int daySeleected) {

                                int hour = calendar.get(Calendar.HOUR);
                                int minutes = calendar.get(Calendar.MINUTE);

                                TimePickerDialog tpd = new TimePickerDialog(EventDetailActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int hourSelected, int minuteSelected) {

                                                Calendar selectedTimestamp = Calendar.getInstance();
                                                //set causes null pointer
                                                selectedTimestamp.set(yearSelected, monthSelected, daySeleected, hourSelected, minuteSelected);
//                                                long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
                                                selectedDatetime.setTime(selectedTimestamp.getTimeInMillis());

                                                Toast.makeText(EventDetailActivity.this, "unixtime:" + selectedTimestamp.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }, hour, minutes, true);
                                tpd.setTitle("Select event time");
                                tpd.show();
                            }
                        }, year, month, day);
                datePickerDialog.setTitle("Select event date");
                datePickerDialog.show();
            }
        });
    }


}
