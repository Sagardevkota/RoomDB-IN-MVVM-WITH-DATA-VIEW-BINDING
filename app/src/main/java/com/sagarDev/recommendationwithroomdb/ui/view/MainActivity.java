package com.sagarDev.recommendationwithroomdb.ui.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sagarDev.recommendationwithroomdb.databinding.ActivityMainBinding;
import com.sagarDev.recommendationwithroomdb.db.entity.User;
import com.sagarDev.recommendationwithroomdb.ui.adapter.UserAdapter;
import com.sagarDev.recommendationwithroomdb.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE=101;
    private UserViewModel userViewModel;
    private UserAdapter userAdapter;
    private List<User> userList=new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        binding.fab.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,UserActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        });
        initRecyclerView();

        userViewModel.getAllUsers().observe(this, users -> userAdapter.setUsers(users));


    }

    private void initRecyclerView() {
        userAdapter=new UserAdapter(userList,this);

        binding.recyclerview.setAdapter(userAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Do you want to delete the user?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            User user = userAdapter.getUserAt(viewHolder.getAdapterPosition());
                            userViewModel.delete(user);
                            userAdapter.removeUser(viewHolder.getAdapterPosition());
                            Toast.makeText(MainActivity.this,user.firstName +" is removed from users",Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();

                }
        }).attachToRecyclerView(binding.recyclerview);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&resultCode==RESULT_OK)
        {
            User user=new User(data.getStringExtra("firstName"),data.getStringExtra("lastName"));
            userViewModel.insert(user);
            Toast.makeText(MainActivity.this,user.firstName + " is added to users",Toast.LENGTH_SHORT).show();

        }
    }
}