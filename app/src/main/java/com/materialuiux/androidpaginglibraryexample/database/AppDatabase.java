package com.materialuiux.androidpaginglibraryexample.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.materialuiux.androidpaginglibraryexample.dao.EmojiDao;
import com.materialuiux.androidpaginglibraryexample.model.Emoji;

@Database(entities = Emoji.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    private static final String DATABASE_NAME = "database.db";

    private static AppDatabase INSTANCE;

    private static final Object sLock = new Object();

    public abstract EmojiDao postDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }

}
