package com.wayster.minhasfinancasapp.RepositoryTest;

import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    public static UserEntity criarUsuario(){
        return UserEntity.builder().name("wayster").email("waystermelo@gmail.com").password("123").build();
    }

    @Test
    public void shouldVerifyIfEmailExists(){
        //cenario
        UserEntity user = UserEntity.builder().name("wayster").email("waystermelo@gmail.com").build();
        testEntityManager.persist(user);
        //acao
        boolean result = userRepository.existsByEmail("waystermelo@gmail.com");
        //verificação
        Assertions.assertTrue(result);
    }

    @Test
    public void verifyUserExistsShouldReturnFalse(){
        boolean result =  userRepository.existsByEmail("waystermelo@gmail.com");
        Assertions.assertFalse(result);
    }

    @Test
    public void shouldPersistAnUser(){
        UserEntity user = UserEntity.builder().name("wayster").email("waystermelo@gmail.com").password("123").build();
        UserEntity user_salved = userRepository.save(user);
        Assertions.assertNotNull(user_salved.getId());
    }

    @Test
    public void shouldGetAnUserByEmailTrue(){
        UserEntity user = criarUsuario();
        testEntityManager.persist(user);
        Optional<UserEntity> rs = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(rs.isPresent());
    }

    @Test
    public void shouldGetAnUserByEmailFalse(){
        Optional<UserEntity> rs = userRepository.findByEmail("admin@gmail.com");
        Assertions.assertFalse(rs.isPresent());
    }



}