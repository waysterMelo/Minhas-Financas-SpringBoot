package com.wayster.minhasfinancasapp.Controllers;

import com.wayster.minhasfinancasapp.Dtos.UsuarioDto;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.ErroAutentificacao;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Repositories.UserRepository;
import com.wayster.minhasfinancasapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class Usuario {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private static void copyDtoToEntity(UsuarioDto usuarioDto, UserEntity entity) {
        entity.setName(usuarioDto.getNome());
        entity.setEmail(usuarioDto.getEmail());
        entity.setPassword(usuarioDto.getSenha());
        entity.setDataCadastro(usuarioDto.getDataCadastro()); 
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody UsuarioDto usuarioDto){
        UserEntity newUser = new UserEntity();
        copyDtoToEntity(usuarioDto, newUser);
        try {
            newUser = userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (RegraDeNegocioException ex){
            return  ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @PostMapping(value = "/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDto dto){
        try {
               UserEntity entity = userService.authenticated(dto.getEmail(), dto.getSenha());
               return ResponseEntity.ok(entity);
        } catch (ErroAutentificacao error) {
                return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

}
