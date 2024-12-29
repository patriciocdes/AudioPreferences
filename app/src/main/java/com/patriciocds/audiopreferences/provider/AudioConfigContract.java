package com.patriciocds.audiopreferences.provider;

import android.net.Uri;

public final class AudioConfigContract {

    private AudioConfigContract() {}

    // Autoridade do Content Provider (deve ser única no sistema)
    public static final String AUTHORITY = "com.patriciocds.audiopreferences.audioprovider";

    // Caminhos/Endpoints (tabelas ou tipos de dados)
    public static final String PATH_USER_PROFILE = "user_profile";

    // URI base: content://com.patriciocds.audiopreferences.audioprovider/
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Definição para a tabela de perfis de usuário
    public static class UserProfileEntry {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USER_PROFILE);

        // Mime types
        // Vários itens
        public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_USER_PROFILE;
        // Único item
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_USER_PROFILE;
    }
}
