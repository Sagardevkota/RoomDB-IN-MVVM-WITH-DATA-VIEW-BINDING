package com.sagarDev.recommendationwithroomdb.db.dbHelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sagarDev.recommendationwithroomdb.db.dao.UserDao;
import com.sagarDev.recommendationwithroomdb.db.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// we set exportSchema to false here to avoid a build warning. In a real app, you should consider setting a directory for Room to use to export the schema so you can check the current schema into your version control system.
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //executor service to run on bg asynchronously
  public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //singleton as we need only one instance of db for whole app
   public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
