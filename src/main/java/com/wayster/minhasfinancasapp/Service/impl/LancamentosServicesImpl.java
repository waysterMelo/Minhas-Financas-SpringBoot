package com.wayster.minhasfinancasapp.Service.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Repositories.LancamentosRepository;
import com.wayster.minhasfinancasapp.Service.LancamentosService;

@SuppressWarnings("null")
@Service
public class LancamentosServicesImpl implements LancamentosService {

    private LancamentosRepository lancamentosRepository;

    LancamentosServicesImpl(LancamentosRepository repository){
            this.lancamentosRepository = repository;
    }
    
    @Override
    @Transactional
    public Lancamentos salvar(Lancamentos lancamento) {
        validar(lancamento); 
        lancamento.setStatusLancamento(StatusLancamento.PENDENTE);  
       return lancamentosRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamentos atualizar(Lancamentos lancamentos) {
            Objects.requireNonNull(lancamentos.getId());
            return lancamentosRepository.save(lancamentos);
    }

    @Override
    @Transactional
    public void deletar(Lancamentos lancamentos) {
        lancamentosRepository.delete(lancamentos);
    }

    @Override
    @Transactional
    public List<Lancamentos> buscar(Lancamentos lancamentoFiltro) {
        Example<Lancamentos> example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(StringMatcher.CONTAINING));

        return lancamentosRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamentos lancamentos, StatusLancamento statusLancamento) {
        lancamentos.setStatusLancamento(statusLancamento);;
        atualizar(lancamentos);
    }

    @Override
    public void validar(Lancamentos lancamentos) {
     if (lancamentos.getDescricao() == null || lancamentos.getDescricao().trim().equals("")) {
        throw new RegraDeNegocioException("Informe uma descricao valida");
     }
     if (lancamentos.getMes() == null || lancamentos.getMes() < 1 || lancamentos.getMes() > 12) {
        throw new RegraDeNegocioException("Informe um Mes valido");
     }
     if (lancamentos.getAno() == null || lancamentos.getAno().toString().length() != 4) {
        throw new RegraDeNegocioException("Informe um ano valido");
     }
     if (lancamentos.getUsuario() == null || lancamentos.getUsuario().getId() == null) {
        throw new RegraDeNegocioException("Informe uma descricao valida");
     }
     if (lancamentos.getValor() == null || lancamentos.getValor().compareTo(BigDecimal.ZERO) < 1 ) {
        throw new RegraDeNegocioException("Informe um valor valido");
     }

        
    }

    
    
}
