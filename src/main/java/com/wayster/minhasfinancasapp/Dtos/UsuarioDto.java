package com.wayster.minhasfinancasapp.Dtos;

import java.time.LocalDate;

import com.wayster.minhasfinancasapp.Entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private String email;
    private String nome;
    private String senha;
    private LocalDate dataCadastro = LocalDate.now();

    public UsuarioDto(UserEntity entity) {
        this.email = entity.getEmail();
        this.nome = entity.getName();
        this.senha = entity.getPassword();
        this.dataCadastro = entity.getDataCadastro();
    }

}
