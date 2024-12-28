package com.patriciocds.audiopreferences.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.patriciocds.audiopreferences.model.UserProfile;

import java.util.List;

@Dao
public interface UserProfileDao {

    @Insert
    long insert(UserProfile userProfile);

    @Update
    void update(UserProfile userProfile);

    @Delete
    void delete(UserProfile userProfile);

    @Query("SELECT * FROM user_profile ORDER BY name")
    LiveData<List<UserProfile>> getFullUserProfile();

    @Query("SELECT * FROM user_profile WHERE id = :profileId")
    UserProfile getUserProfileById(long profileId);
}