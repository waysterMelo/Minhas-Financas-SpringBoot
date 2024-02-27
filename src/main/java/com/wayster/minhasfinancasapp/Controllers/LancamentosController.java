package com.wayster.minhasfinancasapp.Controllers;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wayster.minhasfinancasapp.Dtos.AtualizaStatusDto;
import com.wayster.minhasfinancasapp.Dtos.LancamentosDto;
import com.wayster.minhasfinancasapp.Dtos.LancamentosUpdateDto;
import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;
import com.wayster.minhasfinancasapp.Entity.TipoLancamento;
import com.wayster.minhasfinancasapp.Entity.UserEntity;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Service.LancamentosService;
import com.wayster.minhasfinancasapp.Service.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping(value = "/lancamentos")
@RequiredArgsConstructor
@SuppressWarnings({"unused","null", "rawtypes", "unchecked"})
public class LancamentosController {
 

    
    private final LancamentosService lancamentosService;


    private final UserService userService;

    private Lancamentos converterDtoToEntitySave(LancamentosDto dto){
            Lancamentos entity = new Lancamentos();
            entity.setDescricao(dto.getDescricao());
            entity.setMes(dto.getMes());
            entity.setAno(dto.getAno());
            UserEntity user = userService.obterUserId(dto.getUsuario()).orElseThrow(() -> new RegraDeNegocioException("Usuario nao encontrado para o Id informado"));
            entity.setUsuario(user);
            entity.setValor(dto.getValor());
            entity.setDataCadastro(dto.getDataCadastro()); 

            if (dto.getTipo() != null) {
                entity.setTipo(TipoLancamento.valueOf(dto.getTipo()));
            }
           

            if (dto.getStatus() != null) { 
                entity.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus())); 
            }

            return entity;
    }

    private Lancamentos converterDtoToEntityUpdate(LancamentosUpdateDto dto){
        Lancamentos entity = new Lancamentos();
        entity.setDescricao(dto.getDescricao());
        entity.setMes(dto.getMes());
        entity.setAno(dto.getAno());
        UserEntity user = userService.obterUserId(dto.getUsuario()).orElseThrow(() -> new RegraDeNegocioException("Usuario nao encontrado para o Id informado"));
        entity.setUsuario(user);
        entity.setValor(dto.getValor());
        entity.setDataModificacao(dto.getDataModificacao());
        entity.setDataCadastro(dto.getDataCadastro()); 
        entity.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));  

        if (dto.getTipo() != null) {
            entity.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }
        if (dto.getStatus() != null) { 
            entity.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus())); 
        }

        return entity;
}

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentosDto dto){
            try {
                Lancamentos entity = converterDtoToEntitySave(dto);
                entity = lancamentosService.salvar(entity);
                return new ResponseEntity(entity, HttpStatus.CREATED);

            } catch (RegraDeNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentosUpdateDto dto) {
        return lancamentosService.obterPorId(id).map(entity -> {
           try {
            Lancamentos lancamentos = converterDtoToEntityUpdate(dto);
            lancamentos.setId(entity.getId());
            lancamentosService.atualizar(lancamentos);
            return ResponseEntity.ok(lancamentos);
           } catch (RegraDeNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
           }
        }).orElseGet(() -> new ResponseEntity("Lancamento nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
           
                return lancamentosService.obterPorId(id).map(entity -> {
                lancamentosService.deletar(entity);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseGet(() -> new ResponseEntity("Lancamento nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
           
    }

    @GetMapping
	public ResponseEntity buscar(
			@RequestParam(value ="descricao" , required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
			) {
		
		Lancamentos lancamentoFiltro = new Lancamentos();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);
		
		Optional<UserEntity> usuario = userService.obterUserId(idUsuario); 
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
		}else {
			lancamentoFiltro.setUsuario(usuario.get()); 
		}
		
		List<Lancamentos> lancamentos = lancamentosService.buscar(lancamentoFiltro);
		return ResponseEntity.ok().body(lancamentos);
	}




    @PutMapping("/{id}/atualizar-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDto atualizaStatusDto ){
        try {
            Optional<Lancamentos> l = lancamentosService.obterPorId(id);
            StatusLancamento status = StatusLancamento.valueOf(atualizaStatusDto.getStatus());
            lancamentosService.atualizarStatus(id, status);
            return ResponseEntity.ok().body(l);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(EntityNotFoundException ex){
            return ResponseEntity.badRequest().body("LANÇAMENTO NÃO ENCONTRADO NA BASE DE DADOS");
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id){
            Optional<UserEntity> usuarios = userService.obterUserId(id);

            if (!usuarios.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            BigDecimal saldo = lancamentosService.obterSaldoPorUsuario(id);
            return ResponseEntity.ok(saldo);
            
    }

}