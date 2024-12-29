package com.patriciocds.audiopreferences.util;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.patriciocds.audiopreferences.model.UserProfile;

import java.util.List;

public class ConverterUtils {
    /**
     * Converte uma lista de UserProfile em um Cursor (MatrixCursor).
     */
    public static Cursor listToCursorUserProfile(List<UserProfile> userProfiles) {
        // Definindo nomes de colunas no Cursor
        String[] columns = new String[]{"id", "name", "bassLevel", "midLevel", "trebleLevel"};

        // Usamos MatrixCursor para criar um cursor na memória
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        // Iteramos sobre cada UserProfile para inserir como linha no MatrixCursor
        for (UserProfile userProfile : userProfiles) {
            Object[] rowData = new Object[] {
                    userProfile.getId(),
                    userProfile.getName(),
                    userProfile.getBassLevel(),
                    userProfile.getMidLevel(),
                    userProfile.getTrebleLevel()
            };

            matrixCursor.addRow(rowData);
        }

        return matrixCursor;
    }

    /**
     * Converte um único UserProfile em um Cursor (MatrixCursor).
     */
    public static Cursor singleToCursorUserProfile(UserProfile userProfile) {
        // Se o objeto for nulo, retornamos um cursor vazio
        if (userProfile == null) {
            return new MatrixCursor(new String[] {"id", "name", "bassLevel", "midLevel", "trebleLevel"});
        }

        // Mesma ideia do método anterior, mas para um único objeto
        String[] columns = new String[] {"id", "name", "bassLevel", "midLevel", "trebleLevel"};
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        Object[] rowData = new Object[] {
                userProfile.getId(),
                userProfile.getName(),
                userProfile.getBassLevel(),
                userProfile.getMidLevel(),
                userProfile.getTrebleLevel()
        };

        matrixCursor.addRow(rowData);

        return matrixCursor;
    }

    /**
     * Exemplo (opcional): Converte um Cursor de UserProfile de volta para uma lista de UserProfile.
     */
    public static List<UserProfile> cursorToListUserProfile(Cursor cursor) {
        // Se for necessário converter de Cursor -> List<UserProfile>
        // Você pode implementar algo como abaixo:

        // Exemplo de implementação
        // List<UserProfile> profiles = new ArrayList<>();
        // while (cursor.moveToNext()) {
        //     int id = cursor.getInt(cursor.getColumnIndex("id"));
        //     String nome = cursor.getString(cursor.getColumnIndex("nome"));
        //     int graves = cursor.getInt(cursor.getColumnIndex("graves"));
        //     int medios = cursor.getInt(cursor.getColumnIndex("medios"));
        //     int agudos = cursor.getInt(cursor.getColumnIndex("agudos"));
        //     profiles.add(new UserProfile(id, nome, graves, medios, agudos));
        // }
        // return profiles;

        throw new UnsupportedOperationException("Ainda não implementado.");
    }
}
