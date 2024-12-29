package com.patriciocds.audiopreferences.dao;

import android.content.ContentValues;

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
    int update(UserProfile userProfile);

    @Delete
    void delete(UserProfile userProfile);

    @Query("DELETE FROM user_profile WHERE id = :id")
    int deleteById(int id);

    @Query("SELECT * FROM user_profile ORDER BY name")
    LiveData<List<UserProfile>> getFullUserProfile();

    @Query("SELECT * FROM user_profile ORDER BY name")
    List<UserProfile> getUserProfiles();

    @Query("SELECT * FROM user_profile WHERE id = :profileId")
    UserProfile getUserProfileById(long profileId);

    default long insertFromContentValues(ContentValues values) {
        UserProfile profile = new UserProfile();

        if (values.containsKey("name")) {
            profile.setName(values.getAsString("name"));
        }

        if (values.containsKey("bassLevel")) {
            profile.setBassLevel(values.getAsInteger("bassLevel"));
        }

        if (values.containsKey("midLevel")) {
            profile.setMidLevel(values.getAsInteger("midLevel"));
        }

        if (values.containsKey("trebleLevel")) {
            profile.setTrebleLevel(values.getAsInteger("trebleLevel"));
        }

        return insert(profile);
    }

    // Exemplo de update
    default int updateFromContentValues(long profileId, ContentValues values) {
        UserProfile profile = getUserProfileById((int) profileId);

        if (profile == null) return 0;

        if (values.containsKey("name")) {
            profile.setName(values.getAsString("name"));
        }

        if (values.containsKey("bassLevel")) {
            profile.setBassLevel(values.getAsInteger("bassLevel"));
        }

        if (values.containsKey("midLevel")) {
            profile.setMidLevel(values.getAsInteger("midLevel"));
        }

        if (values.containsKey("trebleLevel")) {
            profile.setTrebleLevel(values.getAsInteger("trebleLevel"));
        }

        return update(profile);
    }
}