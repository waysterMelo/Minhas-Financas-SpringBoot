package com.wayster.minhasfinancasapp.Service.impl;

import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.ErroAutentificacao;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Repositories.UserRepository;
import com.wayster.minhasfinancasapp.Service.UserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


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
        Optional<UserEntity> usuario =  userRepository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new ErroAutentificacao("Usuário não encontrado para o email informado."); 
        }

        boolean senhasBatem = usuario.get().getPassword().equals(senha);

        if (!senhasBatem) {
            throw new ErroAutentificacao("Senha inválida.");
            
        }
        return usuario.get();
    }

    @Override
        public void validarEmail(String email) throws RegraDeNegocioException {
            boolean existe = userRepository.existsByEmail(email);
            if (existe){
                throw new RegraDeNegocioException("Ja existe um usuario cadastrado com esse email");
            }
        }

    @Override
    public Optional<UserEntity> obterUserId(Long id) {
       return  userRepository.findById(id); 
    }

   

        


}
