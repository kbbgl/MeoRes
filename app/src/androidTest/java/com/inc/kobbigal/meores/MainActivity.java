package com.inc.kobbigal.meores;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_NEW_EVENT_REQUEST = 1;
    Button addEventBtn;
    ArrayList<Event> events;
    List<String> eventNames;
    EventAdapter eventAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE_NEW_EVENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                String eventName = data.getStringExtra("event_name");
                String location = data.getStringExtra("event_location");
                String datetime = data.getStringExtra("event_time");

                String[] datetimeParts = datetime.split("\n");
                String date = datetimeParts[0];
                String time = datetimeParts[1];

                Event event = new Event(0, eventName, location, date, time, 0, 0);

                eventNames.add(eventName);
                events.add(event);

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();

        addEventBtn = findViewById(R.id.add_event);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        ArrayAdapter<String> eventNamesArrayAdapter;
        eventNames = new ArrayList<>();
        events = new ArrayList<>();

        /*
        for (int i = 0; i < 10; i++) {
            events.add(new Event(i, "Event " + i,
                    "Location " + i, "Date " + i,
                    "Time + " + i, i, i));

            eventNames.add(events.get(i).getName());
        }*/

        eventNamesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, eventNames);
        autoCompleteTextView.setAdapter(eventNamesArrayAdapter);

        RecyclerView recyclerView = findViewById(R.id.events_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        eventAdapter = new EventAdapter(events);
        eventAdapter.setCallback(new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(int position, View view) {

                Event event = events.get(position);
                Toast.makeText(MainActivity.this, "Event chosen: " + event.getTime(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void oneEventLongClick(int position, View view) {

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                switch (direction) {
                    case ItemTouchHelper.RIGHT:

                        Toast.makeText(MainActivity.this, "Event archived", Toast.LENGTH_SHORT).show();
                        //TODO add archive logic
                        break;
                    case ItemTouchHelper.LEFT:
                        break;
                }

                events.remove(viewHolder.getAdapterPosition());
                eventAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(eventAdapter);

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchEventDetailActivity = new Intent(MainActivity.this, EventDetailActivity.class);
                startActivityForResult(launchEventDetailActivity, CREATE_NEW_EVENT_REQUEST);

            }
        });


//        intent.putExtra("event_time", epoch);
//        intent.putExtra("event_name", event_title.getText().toString());
    }
}