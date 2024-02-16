package com.wayster.minhasfinancasapp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wayster.minhasfinancasapp.Dtos.LancamentosDto;
import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;
import com.wayster.minhasfinancasapp.Entity.TipoLancamento;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Service.LancamentosService;
import com.wayster.minhasfinancasapp.Service.UserService;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentosController {


    private LancamentosService lancamentosService;
    private UserService userService;

    

    public LancamentosController(LancamentosService service) {
        this.lancamentosService = service;
    }

    private Lancamentos converterDtoToEntity(LancamentosDto dto){
            Lancamentos entity = new Lancamentos();
            entity.setDescricao(dto.getDescricao());
            entity.setMes(dto.getMes());
            entity.setAno(dto.getAno());
            UserEntity user = userService.obterUserId(dto.getUsuario()).orElseThrow(() -> new RegraDeNegocioException("Usuario nao encontrado para o Id informado"));
            entity.setUsuario(user);
            entity.setValor(dto.getValor());
            entity.setTipo(TipoLancamento.valueOf(dto.getTipo()));
            entity.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus())); 
            return entity;
    }

    public ResponseEntity<?> salvar(@RequestBody LancamentosDto dto){

    }
    
}
