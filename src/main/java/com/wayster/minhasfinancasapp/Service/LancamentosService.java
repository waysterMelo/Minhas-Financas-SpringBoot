package com.wayster.minhasfinancasapp.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;

public interface LancamentosService {

    Lancamentos salvar(Lancamentos lancamento);

    Lancamentos atualizar(Lancamentos lancamentos);

    void deletar(Lancamentos lancamentos);

    List<Lancamentos> buscar (Lancamentos lancamentos);
    
    void atualizarStatus(Lancamentos lancamentos, StatusLancamento statusLancamento);

    void validar(Lancamentos lancamentos);

    Optional<Lancamentos> obterPorId(Long id);

    void atualizarStatus(Long id, StatusLancamento status);
    
    BigDecimal obterSaldoPorUsuario(Long id); 
}
