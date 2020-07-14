package com.sagarDev.recommendationwithroomdb.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sagarDev.recommendationwithroomdb.db.UserRepository.UserRepository;
import com.sagarDev.recommendationwithroomdb.db.entity.User;

import java.util.List;

//if we need application context inside our viewmodel we use androidviewmodel as it receives application in its contructor.AndroidViewModel has application context. We all know having static context instance is evil as it can cause memory leaks!! However, having static Application instance is not as bad as you might think because there is only one Application instance in the running application.
public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository=new UserRepository(application);
        users=userRepository.getAllUsers();
    }

   public LiveData<List<User>> getAllUsers() { return users; }

    public void insert(User user) { userRepository.insert(user); }

    public void delete(User user) { userRepository.delete(user); }

    public LiveData<User> getUser(User user)
    {
       return userRepository.getUser(user);
    }
}

//Warning: Don't keep a reference to a context that has a shorter lifecycle than your ViewModel! Examples are:
//
//    Activity
//    Fragment
//    View
