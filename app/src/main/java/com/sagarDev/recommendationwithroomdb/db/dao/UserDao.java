package com.sagarDev.recommendationwithroomdb.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.sagarDev.recommendationwithroomdb.db.entity.User;

import java.util.List;

//live data runs on bg thread so no need for asynctask
//A DAO (data access object) validates your SQL at compile-time and associates it with a method. In your Room DAO, you use handy annotations, like @Insert, to represent the most common database operations! Room uses the DAO to create a clean API for your code.
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
   LiveData<User> findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
