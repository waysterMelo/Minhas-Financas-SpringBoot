package com.wayster.minhasfinancas.Service.impl;

import com.wayster.minhasfinancas.Entity.UserEntity;
import com.wayster.minhasfinancas.Exceptions.RegraDeNegocioException;
import com.wayster.minhasfinancas.Repositories.UserRepository;
import com.wayster.minhasfinancas.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

        @Autowired
        private UserRepository userRepository;



        public UserServiceImpl(UserRepository repository) {
            super();
            this.userRepository = repository;
        }

        @Override
        public UserEntity saveUser(UserEntity user) {
            validarEmail(user.getEmail());
            return userRepository.save(user);
        }

    @Override
    public UserEntity authenticated(String email, String senha) {
        return null;
    }

    @Override
        public void validarEmail(String email) throws RegraDeNegocioException {
            boolean existe = userRepository.existsByEmail(email);
            if (existe){
                throw new RegraDeNegocioException("Ja existe um usuario cadastrado com esse email");
            }
        }



}
