package com.patriciocds.audiopreferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.patriciocds.audiopreferences.dao.UserProfileDao;
import com.patriciocds.audiopreferences.db.AppDatabase;
import com.patriciocds.audiopreferences.model.UserProfile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserProfileDaoTest {

    private AppDatabase db;
    private UserProfileDao userProfileDao;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                        AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        userProfileDao = db.userProfileDao();
    }

    @Test
    public void testInsertProfile() {
        // Cria um perfil de usuário
        UserProfile profile = new UserProfile();
        profile.setName("Perfil A");
        profile.setBassLevel(5);
        profile.setMidLevel(6);
        profile.setTrebleLevel(7);

        long id = userProfileDao.insert(profile);

        // Recupera o perfil inserido
        UserProfile insertedProfile = userProfileDao.getUserProfileById(id);

        // Verifica se o perfil foi inserido corretamente
        assertNotNull(insertedProfile);
        assertEquals("Perfil A", insertedProfile.getName());
        assertEquals(5, insertedProfile.getBassLevel());
        assertEquals(6, insertedProfile.getMidLevel());
        assertEquals(7, insertedProfile.getTrebleLevel());
    }

    @Test
    public void testUpdateProfile() {
        // Cria e insere um perfil de usuário
        UserProfile profile = new UserProfile();
        profile.setName("Perfil A");
        profile.setBassLevel(5);
        profile.setMidLevel(6);
        profile.setTrebleLevel(7);

        long id = userProfileDao.insert(profile);

        System.out.println("ID inserido: " + id);

        //Atualiza o objeto com o id criado
        profile.setId(id);
        // Atualiza as informações do perfil
        profile.setName("Perfil B");
        profile.setBassLevel(1);
        profile.setMidLevel(2);
        profile.setTrebleLevel(3);

        userProfileDao.update(profile);

        // Recupera o perfil atualizado
        UserProfile updatedProfile = userProfileDao.getUserProfileById(id);

        // Verifica se o perfil foi atualizado corretamente
        assertNotNull(updatedProfile);
        assertEquals("Perfil B", updatedProfile.getName());
        assertEquals(1, updatedProfile.getBassLevel());
        assertEquals(2, updatedProfile.getMidLevel());
        assertEquals(3, updatedProfile.getTrebleLevel());
    }

    @Test
    public void testDeleteProfile() {
        // Cria e insere um perfil de usuário
        UserProfile profile = new UserProfile();
        profile.setName("Perfil A");
        profile.setBassLevel(3);
        profile.setMidLevel(4);
        profile.setTrebleLevel(5);

        long id = userProfileDao.insert(profile);
        //Atualiza o objeto com o id criado
        profile.setId(id);

        // Exclui o perfil
        userProfileDao.delete(profile);

        // Tenta recuperar o perfil pelo ID (deve ser nulo)
        UserProfile deletedProfile = userProfileDao.getUserProfileById(id);

        // Verifica se o perfil foi excluído
        assertNull(deletedProfile);
    }
}