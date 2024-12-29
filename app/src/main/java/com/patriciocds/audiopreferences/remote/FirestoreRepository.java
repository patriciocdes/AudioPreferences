package com.patriciocds.audiopreferences.remote;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.patriciocds.audiopreferences.model.UserProfile;

public class FirestoreRepository {

    private final FirebaseFirestore db;

    public FirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    // Exemplo de método para salvar um UserProfile
    public void saveUserProfile(UserProfile userProfile, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) {
        // Coleção onde serão armazenados os perfis
        db.collection("user_profiles")
                .add(userProfile)
                .addOnSuccessListener(documentReference -> {
                    // Callback de sucesso
                    if (onSuccess != null) {
                        onSuccess.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> {
                    if (onFailure != null) {
                        onFailure.onFailure(e);
                    }
                });
    }
}