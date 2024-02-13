package com.wayster.minhasfinancas.Service;

import com.wayster.minhasfinancas.Entity.UserEntity;
import com.wayster.minhasfinancas.Exceptions.RegraDeNegocioException;

public interface UserService {

    UserEntity authenticated(String email, String senha);
    UserEntity saveUser(UserEntity user);
    void validarEmail(String email) throws RegraDeNegocioException;
}
