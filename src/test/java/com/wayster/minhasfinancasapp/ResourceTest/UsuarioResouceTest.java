package com.wayster.minhasfinancasapp.ResourceTest;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayster.minhasfinancasapp.Controllers.Usuario;
import com.wayster.minhasfinancasapp.Dtos.UsuarioDto;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Service.LancamentosService;
import com.wayster.minhasfinancasapp.Service.UserService;

@ActiveProfiles("test")
@WebMvcTest(controllers = Usuario.class)
@AutoConfigureMockMvc
public class UsuarioResouceTest {
    

    public static final String API = "/usuarios/";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService service;

    @MockBean
    LancamentosService lancamentosService;

    @Test
    public void deveAutenticarUmUsuario() throws Exception{
        String email = "usuario@gmail.com";
        String senha = "123";

        UsuarioDto dto = UsuarioDto.builder().email(email).senha(senha).build();
        UserEntity usuario = UserEntity.builder().id(1L).email(email).password(senha).build();

        Mockito.when(service.authenticated(email, senha)).thenReturn(usuario);

        String json  = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.post(API.concat("/autenticar"))
        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(json);

        mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("name").value(usuario.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }
}
