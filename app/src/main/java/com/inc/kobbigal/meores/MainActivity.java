package com.inc.kobbigal.meores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEventBtn = findViewById(R.id.add_event);

        final ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            events.add(new Event(i, "Event " + i,
                    "Location " + i, "Date " + i,
                    "Time + " + i, i, i));
        }

        RecyclerView recyclerView = findViewById(R.id.events_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        final EventAdapter adapter = new EventAdapter(events);
        adapter.setCallback(new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(int position, View view) {

                Event event = events.get(position);
                Toast.makeText(MainActivity.this, "Event chosen: " + event.getTime(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void oneEventLongClick(int position, View view) {

                events.remove(position);
                adapter.notifyItemRemoved(position);

            }
        });

        recyclerView.setAdapter(adapter);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchEventDetailActivity = new Intent(MainActivity.this, EventDetailActivity.class);
                startActivity(launchEventDetailActivity);

            }
        });


//        intent.putExtra("event_time", epoch);
//        intent.putExtra("event_name", event_title.getText().toString());
    }
}
