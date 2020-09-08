package com.benX.trackerapp.core.data.manager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.benX.trackerapp.core.constant.AppConstant;
import com.benX.trackerapp.core.data.dao.CoordinatesDao;
import com.benX.trackerapp.core.data.entities.Coordinates;


@Database(entities = {Coordinates.class}, version = 2, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    private static volatile DatabaseManager INSTANCE;

    public abstract CoordinatesDao coordinatesDao();

    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseManager.class, AppConstant.DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public void emptyDatabase() {
        Thread thread = new Thread(() -> coordinatesDao().deleteAll());
        thread.start();
    }
}
