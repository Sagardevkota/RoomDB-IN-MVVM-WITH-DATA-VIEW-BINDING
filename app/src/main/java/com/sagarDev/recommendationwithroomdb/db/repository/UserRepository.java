package com.sagarDev.recommendationwithroomdb.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sagarDev.recommendationwithroomdb.db.dao.UserDao;
import com.sagarDev.recommendationwithroomdb.db.dbHelper.AppDatabase;
import com.sagarDev.recommendationwithroomdb.db.entity.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;

    private LiveData<List<User>> users;

    // Note that in order to unit test the UserRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
   public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        users = userDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
   public LiveData<List<User>> getAllUsers() {
        return users;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
  public void insert(final User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });


    }

    public void delete(final User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });


    }

    public LiveData<User> getUser(User user) {
       return userDao.findByName(user.firstName,user.lastName);
    }
}
