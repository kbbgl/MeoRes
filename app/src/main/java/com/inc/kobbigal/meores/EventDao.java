package com.inc.kobbigal.meores;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Kobbi.Gal on 10/11/2017.
 */

@Dao
public interface EventDao {
    @Query("SELECT * FROM events")
    List<Event> getAll();

    @Query("SELECT * FROM events WHERE id IN (:eventId)")
    List<Event> loadAllByIds(int[] eventId);

    @Query("SELECT * FROM events WHERE name LIKE :name LIMIT 1")
    Event findByName(String name);

    @Insert
    void insertEvents(Event... events);

    @Insert
    void insertEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Delete
    void deleteEvents(Event... events);

    @Update
    void updateEvent(Event event);

    @Update
    void updateEvents(Event... events);
}
