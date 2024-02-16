package com.wayster.minhasfinancasapp.Service;

import java.util.List;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;

public interface LancamentosService {

    Lancamentos salvar(Lancamentos lancamento);

    Lancamentos atualizar(Lancamentos lancamentos);

    void deletar(Lancamentos lancamentos);

    List<Lancamentos> buscar (Lancamentos lancamentos);
    
    void atualizarStatus(Lancamentos lancamentos, StatusLancamento statusLancamento);

    void validar(Lancamentos lancamentos);
    
}
