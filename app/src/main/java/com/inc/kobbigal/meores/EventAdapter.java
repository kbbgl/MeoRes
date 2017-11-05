package com.inc.kobbigal.meores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kobbi.Gal on 04/11/2017.
 */

public class EventAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Event> eventList;
    private Context context;

    public EventAdapter(Context context, List<Event> eventList) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Event event = eventList.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list_row, viewGroup, false);
        }

        View statusBar = view.findViewById(R.id.status_bar);
        TextView eventTitle = view.findViewById(R.id.event_title);
        TextView eventLocation = view.findViewById(R.id.event_location);
        TextView eventTime = view.findViewById(R.id.event_time);
        TextView eventDate = view.findViewById(R.id.event_date);
        Button edit = view.findViewById(R.id.edit_event);

//        statusBar.setBackgroundColor();
        eventTitle.setText(event.getName());
        eventLocation.setText(event.getLocation());
        eventTime.setText(event.getTime());
        eventDate.setText(event.getDate());

        edit.setOnClickListener(this);

        return view;
    }
}
