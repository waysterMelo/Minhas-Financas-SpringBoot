package com.wayster.minhasfinancasapp.RepositoryTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;
import com.wayster.minhasfinancasapp.Entity.TipoLancamento;
import com.wayster.minhasfinancasapp.Repositories.LancamentosRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    
    @Autowired
    LancamentosRepository lancamentoRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento(){
        Lancamentos lancamentos = Lancamentos.builder().ano(2023)
        .mes(2)
        .descricao("lancamento qualquer")
        .valor(BigDecimal.valueOf(10))
        .tipo(TipoLancamento.RECEITA)
        .statusLancamento(StatusLancamento.PENDENTE)
        .dataCadastro(LocalDate.now())
        .build();
        lancamentos = lancamentoRepository.save(lancamentos);
        Assertions.assertNotNull(lancamentos.getId()); 
    }
}
