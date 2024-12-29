package com.patriciocds.audiopreferences.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.patriciocds.audiopreferences.db.AppDatabase;
import com.patriciocds.audiopreferences.model.UserProfile;
import com.patriciocds.audiopreferences.util.ConverterUtils;

import java.util.List;

/**
 * ContentProvider que expõe informações de UserProfile e configurações de áudio.
 */
public class AudioConfigContentProvider extends ContentProvider {

    // URIs para identificar requisições
    private static final int USER_PROFILE = 100;
    private static final int USER_PROFILE_ID = 101;

    private static UriMatcher uriMatcher = buildUriMatcher();

    // Instância do banco de dados Room
    private AppDatabase appDatabase;

    // Cria e configura o UriMatcher
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // content://com.patriciocds.audiopreferences.audioprovider/user_profile
        matcher.addURI(
                AudioConfigContract.AUTHORITY,
                AudioConfigContract.PATH_USER_PROFILE,
                USER_PROFILE
        );

        // content://com.patriciocds.audiopreferences.audioprovider/user_profile/#
        matcher.addURI(
                AudioConfigContract.AUTHORITY,
                AudioConfigContract.PATH_USER_PROFILE + "/#",
                USER_PROFILE_ID
        );

        return matcher;
    }

    @Override
    public boolean onCreate() {
        // Inicializa o banco de dados Room
        appDatabase = AppDatabase.getDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder
    ) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case USER_PROFILE:
                // Retorna todos os perfis
                // Precisamos converter a lista de UserProfile em Cursor
                List<UserProfile> userProfiles = appDatabase.userProfileDao().getUserProfiles();
                return ConverterUtils.listToCursorUserProfile(userProfiles);

            case USER_PROFILE_ID:
                // Retorna apenas o perfil específico pelo ID
                long profileId = ContentUris.parseId(uri);
                UserProfile profile = appDatabase.userProfileDao().getUserProfileById((int) profileId);
                return ConverterUtils.singleToCursorUserProfile(profile);

            default:
                throw new UnsupportedOperationException("URI não suportada: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case USER_PROFILE:
                return AudioConfigContract.UserProfileEntry.CONTENT_LIST_TYPE;
            case USER_PROFILE_ID:
                return AudioConfigContract.UserProfileEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI não suportada: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(
            @NonNull Uri uri,
            @Nullable ContentValues values
    ) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case USER_PROFILE:
                // Insere um novo perfil usando DAO
                long profileId = appDatabase.userProfileDao().insertFromContentValues(values);

                if (profileId > 0) {
                    // Retorna URI do item recém-inserido
                    Uri returnUri = ContentUris.withAppendedId(
                            AudioConfigContract.UserProfileEntry.CONTENT_URI,
                            profileId
                    );

                    getContext().getContentResolver().notifyChange(returnUri, null);

                    return returnUri;
                }
                break;

            default:
                throw new UnsupportedOperationException("URI não suportada para insert: " + uri);
        }

        return null;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs
    ) {
        int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case USER_PROFILE_ID:
                long profileId = ContentUris.parseId(uri);
                rowsUpdated = appDatabase.userProfileDao().updateFromContentValues(profileId, values);
                break;

            default:
                throw new UnsupportedOperationException("URI não suportada para update: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs
    ) {
        int match = uriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case USER_PROFILE_ID:
                long profileId = ContentUris.parseId(uri);
                rowsDeleted = appDatabase.userProfileDao().deleteById((int) profileId);
                break;

            default:
                throw new UnsupportedOperationException("URI não suportada para delete: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }
}
