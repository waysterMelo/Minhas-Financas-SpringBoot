package com.wayster.minhasfinancasapp.Repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;

public interface LancamentosRepository extends JpaRepository<Lancamentos, Long> {

    @Query(value = "select sum(l.valor) from Lancamentos l join l.usuario u where u.id = :idUsuario and l.tipo = :tipo group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") String tipo);
    
}
