package com.inc.kobbigal.meores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;

    private OnEventClickListener callback;

    EventAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);
        return new EventViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        Event event = events.get(position);
        holder.title.setText(event.getName());
        holder.location.setText(event.getLocation());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setCallback(OnEventClickListener callback) {
        this.callback = callback;
    }

    public interface OnEventClickListener {
        void onEventClick(int position, View view);

        void oneEventLongClick(int position, View view);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        View statusBar;
        TextView title;
        TextView location;
        TextView date;
        TextView time;

        EventViewHolder(View itemView) {
            super(itemView);
            this.statusBar = itemView.findViewById(R.id.status_bar);
            this.title = itemView.findViewById(R.id.event_title);
            this.location = itemView.findViewById(R.id.event_location);
            this.date = itemView.findViewById(R.id.event_date);
            this.time = itemView.findViewById(R.id.event_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onEventClick(getAdapterPosition(), view);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    callback.oneEventLongClick(getAdapterPosition(), view);
                    return true;
                }
            });
        }


    }

}

