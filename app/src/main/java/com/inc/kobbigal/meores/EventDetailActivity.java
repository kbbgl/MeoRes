package com.inc.kobbigal.meores;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import java.util.Calendar;

public class EventDetailActivity extends AppCompatActivity {

    ImageButton datetimeBtn;
    Button submitEvent;
    DatePickerDialog datePickerDialog;
    TimePickerDialog tpd;
    EditText event_title;
    ViewSwitcher viewSwitcher;
    TextView datetimeTv;
    long epoch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        datetimeBtn = findViewById(R.id.choose_time_btn);
        event_title = findViewById(R.id.event_title_et);
        submitEvent = findViewById(R.id.submit_event_details_btn);
        viewSwitcher = findViewById(R.id.datetime_vs);

        final Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        final Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewSwitcher.setOutAnimation(out);
        viewSwitcher.setInAnimation(in);

        submitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EventDetailActivity.this, MainActivity.class);
                intent.putExtra("event_time", epoch);
                intent.putExtra("event_name", event_title.getText().toString());
                startActivity(intent);

            }
        });

        datetimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EventDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker datePicker, final int yearSelected, final int monthSelected, final int daySeleected) {

                                int hour = calendar.get(Calendar.HOUR);
                                int minutes = calendar.get(Calendar.MINUTE);

                                tpd = new TimePickerDialog(EventDetailActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int hourSelected, int minuteSelected) {

                                                Calendar selectedTimestamp = Calendar.getInstance();

                                                selectedTimestamp.set(Calendar.YEAR, yearSelected);
                                                selectedTimestamp.set(Calendar.MONTH, monthSelected);
                                                selectedTimestamp.set(Calendar.DAY_OF_MONTH, daySeleected);
                                                selectedTimestamp.set(Calendar.HOUR, hourSelected);
                                                selectedTimestamp.set(Calendar.MINUTE, minuteSelected);

                                                epoch = selectedTimestamp.getTimeInMillis() / 1000;
                                                final String datetime = daySeleected + "/" + (monthSelected + 1) + "/" + yearSelected + "\n" + hourSelected + ":" + minuteSelected;
                                                System.out.println(epoch);
                                                datetimeTv = (TextView) viewSwitcher.getNextView();
                                                viewSwitcher.showNext();
                                                datetimeTv.setText(datetime);

                                                //TODO perhaps add prompt to add event to Google Calendar
                                            }
                                        }, hour, minutes, true);
                                tpd.setTitle("Select event time");
                                tpd.show();
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.setTitle("Select event date");
                datePickerDialog.show();
            }
        });

    }


    public class CustomAnimationListener implements Animation.AnimationListener {
        private ImageButton imageButton;

        public CustomAnimationListener(ImageButton imageButton) {
            this.imageButton = imageButton;
        }

        @Override
        public void onAnimationStart(Animation animation) {

            imageButton.setVisibility(View.GONE);

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
