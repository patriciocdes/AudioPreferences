package com.patriciocds.audiopreferences.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.patriciocds.audiopreferences.R;
import com.patriciocds.audiopreferences.controller.UserProfileViewModel;
import com.patriciocds.audiopreferences.model.UserProfile;

public class UserProfileActivity extends AppCompatActivity {

    private UserProfileViewModel userProfileViewModel;

    private EditText profileNameEditText;
    private SeekBar barBassLevel;
    private SeekBar barMidLevel;
    private SeekBar barTrebleLevel;

    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileNameEditText = findViewById(R.id.profileNameEditText);
        barBassLevel = findViewById(R.id.barBassLevel);
        barMidLevel = findViewById(R.id.barMidLevel);
        barTrebleLevel = findViewById(R.id.barTrebleLevel);

        userProfileViewModel = new UserProfileViewModel(getApplication());
        userProfile = (UserProfile) getIntent().getSerializableExtra("USER_PROFILE");

        if (userProfile != null) {
            profileNameEditText.setText(userProfile.getName());
            barBassLevel.setProgress(userProfile.getBassLevel());
            barMidLevel.setProgress(userProfile.getMidLevel());
            barTrebleLevel.setProgress(userProfile.getTrebleLevel());
        }

        showDeleteButton(userProfile != null);

        findViewById(R.id.saveProfileButton).setOnClickListener(v -> saveProfileButtonClick());
        findViewById(R.id.deleteProfileButton).setOnClickListener(v -> deleteProfileButtonClick());
    }

    private void saveProfileButtonClick() {
        UserProfile newUserProfile = getNewUserProfile();

        if (newUserProfile != null) {
            if (userProfile == null) {
                userProfileViewModel.insertUserProfile(newUserProfile);
                Toast.makeText(UserProfileActivity.this, "Perfil salvo!", Toast.LENGTH_SHORT).show();
            } else {
                newUserProfile.setId(userProfile.getId());
                userProfileViewModel.updateUserProfile(newUserProfile);
                Toast.makeText(UserProfileActivity.this, "Perfil atualizado!", Toast.LENGTH_SHORT).show();
            }

            clearValues();
        } else {
            Toast.makeText(UserProfileActivity.this, "Preencha o campo nome do perfil!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProfileButtonClick() {
        userProfileViewModel.deleteUserProfile(userProfile);
        clearValues();
        Toast.makeText(UserProfileActivity.this, "Perfil excluído!", Toast.LENGTH_SHORT).show();
    }

    private UserProfile getNewUserProfile() {
        UserProfile userProfile = null;
        String name = profileNameEditText.getText().toString();

        if (!name.isEmpty()) {
            userProfile = new UserProfile();
            userProfile.setName(name);
            userProfile.setBassLevel(barBassLevel.getProgress());
            userProfile.setMidLevel(barMidLevel.getProgress());
            userProfile.setTrebleLevel(barTrebleLevel.getProgress());
        }

        return userProfile;
    }

    private void clearValues() {
        profileNameEditText.setText("");
        barBassLevel.setProgress(0);
        barMidLevel.setProgress(0);
        barTrebleLevel.setProgress(0);
    }

    private void showDeleteButton(boolean value) {
        findViewById(R.id.deleteProfileButton).setVisibility(value ? View.VISIBLE : View.GONE);
    }
}