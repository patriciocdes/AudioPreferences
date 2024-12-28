package com.patriciocds.audiopreferences.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user_profile")
public class UserProfile implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int bassLevel;
    private int midLevel;
    private int trebleLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBassLevel() {
        return bassLevel;
    }

    public void setBassLevel(int bassLevel) {
        this.bassLevel = bassLevel;
    }

    public int getMidLevel() {
        return midLevel;
    }

    public void setMidLevel(int midLevel) {
        this.midLevel = midLevel;
    }

    public int getTrebleLevel() {
        return trebleLevel;
    }

    public void setTrebleLevel(int trebleLevel) {
        this.trebleLevel = trebleLevel;
    }
}
