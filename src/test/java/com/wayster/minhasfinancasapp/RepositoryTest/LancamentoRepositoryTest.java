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
import org.springframework.util.Assert;

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


    private Lancamentos criarLancamento(){
        return Lancamentos.builder().ano(2023)
       .mes(2)
       .descricao("lancamento qualquer")
       .valor(BigDecimal.valueOf(10))
       .tipo(TipoLancamento.RECEITA)
       .statusLancamento(StatusLancamento.PENDENTE)
       .dataCadastro(LocalDate.now())
       .build();
   }

   @Test
   public Lancamentos criarEPersistirUmLancamento(){
        Lancamentos l = criarLancamento();
       return entityManager.persist(l);
   }
    
   @Test
    public void deveSalvarUmLancamento(){
        Lancamentos lancamentos = criarEPersistirUmLancamento();
        Assertions.assertNotNull(lancamentos.getId()); 
    }


    @Test
    public void deveDeletarUmLancamento(){
        Lancamentos lancamentos = criarEPersistirUmLancamento();
        lancamentoRepository.delete(lancamentos); 
        Lancamentos lancamentosInexistente = entityManager.find(Lancamentos.class ,l.getId());
        Assertions.assertNull(lancamentosInexistente);
    }
  

    @Test
    public void dveAtualizarUmLancamento(){
        Lancamentos lancamentos = criarEPersistirUmLancamento();
        lancamentos.setAno(2018);
        lancamentos.setDescricao("teste atualizar");
        lancamentos.setStatusLancamento(StatusLancamento.CANCELADO);

        lancamentoRepository.save(lancamentos);

        Lancamentos lancamentosAtualizado = entityManager.find(Lancamentos.class, lancamentos.getId());

        Assertions.assertEquals(2018, lancamentosAtualizado.getAno());
        Assertions.assertEquals("teste atualizar", lancamentosAtualizado.getDescricao());
        Assertions.assertEquals(StatusLancamento.CANCELADO, lancamentosAtualizado.getStatusLancamento());  

    }

}
