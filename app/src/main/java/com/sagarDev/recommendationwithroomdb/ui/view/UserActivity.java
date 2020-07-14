package com.sagarDev.recommendationwithroomdb.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sagarDev.recommendationwithroomdb.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        binding.buAddUser.setOnClickListener(view1 -> {
            Intent resultIntent=new Intent();

            String firstName=binding.etFirstName.getText().toString();
            String lastName=binding.etLastName.getText().toString();
            if (TextUtils.isEmpty(firstName)||TextUtils.isEmpty(lastName))
                setResult(RESULT_CANCELED,resultIntent);
            else
            {
                resultIntent.putExtra("firstName",firstName);
                resultIntent.putExtra("lastName",lastName);
                setResult(RESULT_OK,resultIntent);
            }

            finish();

        });

    }
}
