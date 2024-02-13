package com.wayster.minhasfinancas.ServiceTest;

import com.wayster.minhasfinancas.Entity.UserEntity;
import com.wayster.minhasfinancas.Exceptions.ErroAutentificacao;
import com.wayster.minhasfinancas.Exceptions.RegraDeNegocioException;
import com.wayster.minhasfinancas.Repositories.UserRepository;
import com.wayster.minhasfinancas.Service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UserRepository userRepository;

    @SpyBean
    UserServiceImpl userService;


    @Test
    public void deveValidarEmail() {
      Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
      userService.validarEmail("waystermelo@gmail.com");
    }

    @Test
    public void shouldAuthUserWithSuccess(){
            String email = "waystermelo@gmail.com";
            String senha = "deus";
            UserEntity user = UserEntity.builder().email(email).password(senha).build();

            Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
            userService.authenticated("waystermelo@gmail.com", "deus");
    }

    @Test
    public void launchErrorWithUserNotAuthenticated(){
            Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
            userService.authenticated("waystermelo@gmail.com", "deus");

    }

    @Test
    public void launchErrorWhenKeysNotCombine(){
            String senha = "123";
            UserEntity user = UserEntity.builder().password(senha).build();
            Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
            userService.authenticated("waystermelo@gmail.com", "deus");
    }

    @Test
    public void shouldReturnErroWhenUserWithEmailNotFound(){
            Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
            userService.authenticated("waystermelo@gmail.com", "deus");
    }

    @Test
    public void deveSalvarUmUsuario() {
        //cenário
        Mockito.doNothing().when(userService).validarEmail(Mockito.anyString());
        UserEntity usuario = UserEntity.builder()
                .id(1L)
                .name("nome")
                .email("email@email.com")
                .password("senha").build();

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(usuario);

        //acao
        UserEntity usuarioSalvo = userService.saveUser(new UserEntity());

        //verificao
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertEquals(1L, usuarioSalvo.getId());
        Assertions.assertEquals(usuarioSalvo.getName(),"nome");
        Assertions.assertEquals(usuarioSalvo.getEmail(),"email@email.com");
        Assertions.assertEquals( usuarioSalvo.getPassword(),"senha");

    }

    @Test
    public void notSaveSameUser(){
            String email = "admin@gmail.com";
            UserEntity user = UserEntity.builder().email(email).build();
            Mockito.doThrow(RegraDeNegocioException.class).when(userService).validarEmail(email);

            userService.saveUser(user);

            Mockito.verify(userRepository, Mockito.never()).save(user);

    }
}
