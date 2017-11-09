package com.inc.kobbigal.meores;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Calendar;

public class EventDetailActivity extends AppCompatActivity {

    ImageButton datetimeBtn;
    ImageButton locationBtn;
    ImageButton tasksBtn;
    ImageButton attendeesBtn;
    Button submitEvent;
    DatePickerDialog datePickerDialog;
    TimePickerDialog tpd;
    EditText event_title;
    ViewSwitcher datetimeVs;
    ViewSwitcher locationVs;
    TextView datetimeTv;
    TextView locationTv;
    long epoch;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("Location", place.getName() + " " + place.getAddress());

                locationTv = (TextView) locationVs.getNextView();
                locationVs.showNext();
                String address = locationTv.getText().toString() + place.getAddress().toString().replaceAll(",", "\n").trim();
                locationTv.setText(address);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                com.google.android.gms.common.api.Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("Location", status.getStatusMessage());
                Toast.makeText(this, "Couldn't retrieve place", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Location retrieval canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        datetimeBtn = findViewById(R.id.choose_time_btn);
        locationBtn = findViewById(R.id.choose_location_btn);
        tasksBtn = findViewById(R.id.choose_tasks_btn);
        attendeesBtn = findViewById(R.id.choose_attendees_btn);
        event_title = findViewById(R.id.event_title_et);
        submitEvent = findViewById(R.id.submit_event_details_btn);

        datetimeVs = findViewById(R.id.datetime_vs);
        locationVs = findViewById(R.id.location_vs);

        final Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        final Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        buttonEffect(datetimeBtn);
        buttonEffect(locationBtn);
        buttonEffect(tasksBtn);
        buttonEffect(attendeesBtn);

        datetimeVs.setOutAnimation(out);
        datetimeVs.setInAnimation(in);
        locationVs.setOutAnimation(out);
        locationVs.setInAnimation(in);

        submitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EventDetailActivity.this, MainActivity.class);
                intent.putExtra("event_time", epoch);
                intent.putExtra("event_name", event_title.getText().toString());
                startActivity(intent);

            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(EventDetailActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

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
                                                Log.i("datetime", datetime);
                                                datetimeTv = (TextView) datetimeVs.getNextView();
                                                datetimeVs.showNext();
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


    public void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

}
