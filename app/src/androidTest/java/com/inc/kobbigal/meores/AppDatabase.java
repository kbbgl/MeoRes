package com.inc.kobbigal.meores;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Kobbi.Gal on 10/11/2017.
 */

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EventDao eventDao();

}
