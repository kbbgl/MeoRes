package com.inc.kobbigal.meores;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Kobbi.Gal on 10/11/2017.
 */

public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE id IN (:eventId)")
    List<Event> loadAllByIds(int[] eventId);

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    Event findByName(String name);

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event event);

    @Update
    void updateEvent(Event... events);
}
