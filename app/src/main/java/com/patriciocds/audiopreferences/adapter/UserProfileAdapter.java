package com.patriciocds.audiopreferences.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.patriciocds.audiopreferences.model.UserProfile;

import java.util.List;

public class UserProfileAdapter extends ArrayAdapter<UserProfile> {

    private final Context context;

    public UserProfileAdapter(Context context, List<UserProfile> userProfiles) {
        super(context, 0, userProfiles);

        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserProfile userProfile = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (userProfile != null) {
            text1.setText(userProfile.getName());
            text2.setText("Graves: " + userProfile.getBassLevel() +
                    " | Médios: " + userProfile.getMidLevel() +
                    " | Agudos: " + userProfile.getTrebleLevel());
        }

        return convertView;
    }
}
