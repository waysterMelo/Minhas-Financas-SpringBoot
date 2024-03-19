package com.wayster.minhasfinancasapp.ServiceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;
import com.wayster.minhasfinancasapp.Entity.StatusLancamento;
import com.wayster.minhasfinancasapp.Exception.RegraDeNegocioException;
import com.wayster.minhasfinancasapp.Repositories.LancamentosRepository;
import com.wayster.minhasfinancasapp.RepositoryTest.LancamentoRepositoryTest;
import com.wayster.minhasfinancasapp.Service.impl.LancamentosServicesImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {
    

    @SpyBean
    LancamentosServicesImpl lancamentosServicesImpl;

    @MockBean
    LancamentosRepository lancamentosRepository;

    @Test
    public void deveSalvarUmLancamento(){
        // 1. Criar lançamento a ser salvo
        Lancamentos lancamentosASalvar = LancamentoRepositoryTest.criarLancamento();

        // 2. Simular validação bem-sucedida
        Mockito.doNothing().when(lancamentosServicesImpl).validar(lancamentosASalvar);

        // 3. Criar lançamento salvo com ID 1
        Lancamentos lancamentosSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentosSalvo.setId(1L);
        lancamentosSalvo.setStatusLancamento(StatusLancamento.PENDENTE);

        // 4. Simular salvamento bem-sucedido
        Mockito.when(lancamentosRepository.save(lancamentosASalvar)).thenReturn(lancamentosSalvo);

        // 5. Salvar o lançamento
        Lancamentos l = lancamentosServicesImpl.salvar(lancamentosASalvar);

        // 6. Verificar se o ID do lançamento salvo é 1
        Assertions.assertThat(l.getId()).isEqualTo(lancamentosSalvo.getId());
        Assertions.assertThat(l.getStatusLancamento()).isEqualTo(lancamentosSalvo.getStatusLancamento());

    }

    @Test
    public void naoDeveSalvarUmLancamentoComErroValidacao(){
          Lancamentos lancamentos =  LancamentoRepositoryTest.criarLancamento();

          Mockito.doThrow(RegraDeNegocioException.class).when(lancamentosServicesImpl).validar(lancamentos);

          Assertions.catchThrowableOfType(() -> lancamentosServicesImpl.salvar(lancamentos), RegraDeNegocioException.class);

          Mockito.verify(lancamentosRepository, Mockito.never()).save(lancamentos);
    }


    
}
