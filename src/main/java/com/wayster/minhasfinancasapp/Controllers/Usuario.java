package com.wayster.minhasfinancasapp.Controllers;

import com.wayster.minhasfinancasapp.Dtos.UsuarioDto;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Repositories.UserRepository;
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

    private static void copyDtoToEntity(UsuarioDto usuarioDto, UserEntity entity) {
        entity.setName(usuarioDto.getNome());
        entity.setEmail(usuarioDto.getEmail());
        entity.setPassword(usuarioDto.getSenha());
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDto usuarioDto){
        UserEntity newUser = new UserEntity();

        copyDtoToEntity(usuarioDto, newUser);

        try {
            newUser = userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (RegraDeNegocioException ex){
            return  ResponseEntity.badRequest().body(ex.getMessage());
        }

    }


}
