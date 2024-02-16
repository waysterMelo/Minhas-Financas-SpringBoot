package com.wayster.minhasfinancasapp.Service;

import java.util.Optional;

import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;

public interface UserService {

    UserEntity authenticated(String email, String senha);
    UserEntity saveUser(UserEntity user);
    void validarEmail(String email) throws RegraDeNegocioException;
    Optional<UserEntity> obterUserId(long id);
}
