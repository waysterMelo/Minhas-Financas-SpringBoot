package com.wayster.minhasfinancasapp.Controllers;

import com.wayster.minhasfinancasapp.Dtos.UsuarioDto;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.ErroAutentificacao;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Service.LancamentosService;
import com.wayster.minhasfinancasapp.Service.UserService;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class Usuario {

    @Autowired
    UserService userService;

    @Autowired
    LancamentosService lancamentosService;

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
            newUser = userService.saveUser(newUser); 
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

    @GetMapping("/{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id){
            Optional<UserEntity> usuarios = userService.obterUserId(id);

            if (!usuarios.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            BigDecimal saldo = lancamentosService.obterSaldoPorUsuario(id);
            return ResponseEntity.ok(saldo);
            
    }


}
