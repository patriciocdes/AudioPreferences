package com.patriciocds.audiopreferences.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.patriciocds.audiopreferences.R;
import com.patriciocds.audiopreferences.adapter.UserProfileAdapter;
import com.patriciocds.audiopreferences.controller.UserProfileViewModel;
import com.patriciocds.audiopreferences.model.UserProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UserProfileViewModel userProfileViewModel;
    private UserProfileAdapter userProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userProfileViewModel = new UserProfileViewModel(getApplication());

        userProfileAdapter = new UserProfileAdapter(this, new ArrayList<>());

        ((ListView) findViewById(R.id.profileListView)).setAdapter(userProfileAdapter);

        ((ListView) findViewById(R.id.profileListView)).setOnItemClickListener((parent, view, position, id) -> {
            UserProfile item = (UserProfile) parent.getItemAtPosition(position);

            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("USER_PROFILE", item);

            startActivity(intent);
        });

        findViewById(R.id.registerButton).setOnClickListener(v ->
                startActivity(new Intent(this, UserProfileActivity.class)));

        userProfileViewModel.getFullUserProfile().observe(this, userProfiles -> {
            userProfileAdapter.clear();

            if (!userProfiles.isEmpty()) {
                userProfileAdapter.addAll(userProfiles);
            }

            userProfileAdapter.notifyDataSetChanged();
        });
    }
}