package com.inc.kobbigal.meores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.events_list);
        addEventBtn = findViewById(R.id.add_event);

        ArrayList<Event> list = new ArrayList<>();
        //public Event(int id, String name, String location, String date, String time, int numberOfAttendees, int statusId) {
        for (int i = 0; i < 5; i++)
            list.add(new Event(i, "Name " + i, "Location " + i, "Date " + i, "Time " + i, i, i));

        EventAdapter eventAdapter = new EventAdapter(this, list);
        listView.setAdapter(eventAdapter);

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
