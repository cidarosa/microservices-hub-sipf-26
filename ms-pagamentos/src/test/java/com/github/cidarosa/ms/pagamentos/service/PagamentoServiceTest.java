package com.github.cidarosa.ms.pagamentos.service;

import com.github.cidarosa.ms.pagamentos.dto.PagamentoResponseDTO;
import com.github.cidarosa.ms.pagamentos.entities.Pagamento;
import com.github.cidarosa.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.pagamentos.repository.PagamentoRepository;
import com.github.cidarosa.ms.pagamentos.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existingId;
    private Long nonExistingId;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {

        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
        pagamento = Factory.createPagamento();
    }

    @Test
    void deleteByIdShouldDeleteWhenIdExists() {

        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existingId);

        Mockito.verify(pagamentoRepository).existsById(existingId);

        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    void deletePagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    pagamentoService.deletePagamentoById(nonExistingId);
                }
        );

        Mockito.verify(pagamentoRepository).existsById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(nonExistingId);
    }

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists() {

        Mockito.when(pagamentoRepository.findById(existingId))
                .thenReturn(Optional.of(pagamento)
                );

        PagamentoResponseDTO result = pagamentoService.findPagamentoById(existingId);
        Assertions.assertNotNull(result);
        ;
        Assertions.assertEquals(pagamento.getId(), result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());

        Mockito.verify(pagamentoRepository).findById(existingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void findPagamentoByIdShouldThrowResourceNotFoundExcptionWhenIdDoesNotExist() {

        Mockito.when(pagamentoRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.findPagamentoById(nonExistingId)
        );

        Mockito.verify(pagamentoRepository).findById(nonExistingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void givenValidParamsAndIdIsNull_whenSave_thenShouldPersistPagamento() {

        pagamento.setId(null);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);

        PagamentoResponseDTO inputDTO = new PagamentoResponseDTO(pagamento);

        PagamentoResponseDTO result = pagamentoService.savePagamento(inputDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());

        Mockito.verify(pagamentoRepository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);

    }

    @Test
    void updatePagamentoShouldReturnPagamentoDTOWhneIdExists() {

        Long id = pagamento.getId();
        Mockito.when(pagamentoRepository.getReferenceById(id))
                .thenReturn(pagamento);

        Mockito.when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);

        PagamentoResponseDTO result = pagamentoService.update(id, new PagamentoResponseDTO(pagamento));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());
        Mockito.verify(pagamentoRepository).getReferenceById(id);
        Mockito.verify(pagamentoRepository).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Mockito.when(pagamentoRepository.getReferenceById(nonExistingId))
                .thenThrow(ResourceNotFoundException.class);

        PagamentoResponseDTO inputDTO = new PagamentoResponseDTO(pagamento);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.update(nonExistingId, inputDTO)
        );

        Mockito.verify(pagamentoRepository).getReferenceById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }
}
