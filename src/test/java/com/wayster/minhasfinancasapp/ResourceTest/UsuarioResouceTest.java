package com.wayster.minhasfinancasapp.ResourceTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayster.minhasfinancasapp.Controllers.Usuario;
import com.wayster.minhasfinancasapp.Dtos.UsuarioDto;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.ErroAutentificacao;
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

    @Test
    public void deveRetornarErroDeAutentificacao() throws Exception{
        String email = "usuario@gmail.com";
        String senha = "123";

        UsuarioDto dto = UsuarioDto.builder().email(email).senha(senha).build();

         // Configurando o comportamento do serviço mockado quando o método authenticated é chamado com o email e senha definidos acima,
        // lançando uma exceção de ErroAutentificacao
        Mockito.when(service.authenticated(email, senha)).thenThrow(ErroAutentificacao.class);

        String json  = new ObjectMapper().writeValueAsString(dto);


         // Criando uma solicitação MockHttpServletRequestBuilder para simular uma solicitação POST para a API de autenticação,
         // definindo o conteúdo do tipo JSON com o JSON convertido acima
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.post(API.concat("/autenticar"))
        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(json);

            // Executando a solicitação mock e verificando se o status de resposta é BadRequest (400)
        mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCriarNovoUsuario() throws Exception {
        // Dados do novo usuário
        String email = "usuario@gmail.com";
        String senha = "123";
    
        // Construção do objeto DTO do usuário
        UsuarioDto dto = UsuarioDto.builder()
                                    .email("usuario@gmail.com")
                                    .senha("123")
                                    .build();
    
        // Construção do objeto de entidade do usuário
        UserEntity userEntity = UserEntity.builder()
                                            .id(1L)
                                            .email(email)
                                            .password(senha)
                                            .build();
    
        // Configuração do comportamento do serviço mockado
        Mockito.when(service.saveUser(Mockito.any(UserEntity.class))).thenReturn(userEntity);
    
        // Conversão do objeto DTO para JSON
        String json = new ObjectMapper().writeValueAsString(dto);
    
        // Construção da requisição HTTP mockada
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                                                                    .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                                                                    .content(json);
    
        // Execução da requisição e validação dos resultados
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(userEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(userEntity.getEmail()));
    }
    


}
