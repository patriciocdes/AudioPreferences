package com.patriciocds.audiopreferences.controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.patriciocds.audiopreferences.dao.UserProfileDao;
import com.patriciocds.audiopreferences.db.AppDatabase;
import com.patriciocds.audiopreferences.model.UserProfile;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserProfileViewModel extends AndroidViewModel {

    private final UserProfileDao userProfileDao;
    private LiveData<List<UserProfile>> fullUserProfiles;

    private final ExecutorService executorService;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getDatabase(application);

        userProfileDao = database.userProfileDao();
        fullUserProfiles = userProfileDao.getFullUserProfile();

        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertUserProfile(final UserProfile userProfile) {
        executorService.execute(() -> {
            userProfileDao.insert(userProfile);
            fullUserProfiles = userProfileDao.getFullUserProfile();
        });
    }

    public void updateUserProfile(final UserProfile userProfile) {
        executorService.execute(() -> {
            userProfileDao.update(userProfile);
            fullUserProfiles = userProfileDao.getFullUserProfile();
        });
    }

    public void deleteUserProfile(final UserProfile userProfile) {
        executorService.execute(() -> {
            userProfileDao.delete(userProfile);
            fullUserProfiles = userProfileDao.getFullUserProfile();
        });
    }

    public LiveData<List<UserProfile>> getFullUserProfile() {
        return fullUserProfiles;
    }
}
