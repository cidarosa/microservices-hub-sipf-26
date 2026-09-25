package com.github.cidarosa.ms.pagamentos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cidarosa.ms.pagamentos.dto.PagamentoResponseDTO;
import com.github.cidarosa.ms.pagamentos.entities.Pagamento;

import com.github.cidarosa.ms.pagamentos.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
public class PagamentoControllerIT {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Pagamento pagamento;
//    private Long existingId;
//    private Long nonExistingId;
//
//    @BeforeEach
//    void setUp() {
//        existingId = 1L;
//        nonExistingId = Long.MAX_VALUE;
//    }
//
//    @Test
//    void findAllPagamentosShouldReturn200AndJsonArray() throws Exception {
//
//        mockMvc.perform(get("/pagamentos")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].id").isNumber())
//                .andExpect(jsonPath("$[*].id").isArray())
//                .andExpect(jsonPath("$[1].valor").value(3599.0))
//                .andExpect(jsonPath("$[0].numeroCartao").value("6859745869874569"))
//                .andExpect(jsonPath("$[1].validade").value("01/28"));
//    }
//
//    @Test
//    void findPagamentoByIdShouldReturn200WhenIdExists() throws Exception {
//
//        mockMvc.perform(get("/pagamentos/{id}", existingId)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.nome").value("Jon Snow"))
//                .andExpect(jsonPath("$.status").value("CRIADO"));
//    }
//
//    @Test
//    void findPagamentoByIdShouldReturn404WhenIdDoesNotExist() throws Exception {
//
//        mockMvc.perform(get("/pagamento/{id}", nonExistingId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createPagamentoShouldReturn201WhenValid() throws Exception {
//
//        PagamentoResponseDTO requestDTO = new PagamentoResponseDTO(Factory.createPagamentoSemId());
//        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
//
//        mockMvc.perform(post("/pagamentos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequestBody)
//                )
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(header().exists("Location"))
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.id").value(3))
//                .andExpect(jsonPath("$.status").value("CRIADO"))
//                .andExpect(jsonPath("$.nome").value("Brienne de Tarth"))
//                .andExpect(jsonPath("$.numeroCartao").value("3654789632145698"))
//                .andExpect(jsonPath("$.validade").value("07/15"))
//                .andExpect(jsonPath("$.codigoSeguranca").value("354"))
//                .andExpect(jsonPath("$.valor").value(32.25))
//                .andExpect(jsonPath("$.pedidoId").value(1));
//    }
//
//    @Test
//    void createPagamentoShouldReturn422WhenInvalid() throws Exception {
//
//        Pagamento pagamentoInvalido = Factory.createPagamentoSemId();
//        pagamentoInvalido.setValor(BigDecimal.ZERO);
//        pagamentoInvalido.setNome(null);
//
//        PagamentoResponseDTO requestDTO = new PagamentoResponseDTO(pagamentoInvalido);
//        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
//
//        mockMvc.perform(post("/pagamentos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequestBody))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.error").value("Dados inválidos"))
//                .andExpect(jsonPath("$.errors").isArray());
//
//    }
//
//    @Test
//    void updatePagamentoShouldReturn200WhenIdExists() throws Exception {
//
//        pagamento = Factory.createPagamento();
//        PagamentoResponseDTO requestDTO = new PagamentoResponseDTO(pagamento);
//        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
//
//        mockMvc.perform(put("/pagamentos/{id}", existingId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequestBody))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.nome").value(pagamento.getNome()));
//    }
//
//    @Test
//    void updatePagamentoShouldReturn422WhenInvalid() throws Exception {
//
//        pagamento = Factory.createPagamento();
//        pagamento.setNome(null);
//        pagamento.setValor(BigDecimal.valueOf(-32.05));
//        pagamento.setPedidoId(null);
//
//        PagamentoResponseDTO requestDTO = new PagamentoResponseDTO(pagamento);
//        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
//
//        mockMvc.perform(put("/pagamentos/{id}", existingId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequestBody))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.error").value("Dados inválidos"))
//                .andExpect(jsonPath("$.errors").isArray());
//
//    }
//
//    @Test
//    void updatePagamentoShouldReturn404WhenIdDoesNotExist() throws Exception {
//
//        pagamento = Factory.createPagamento();
//        PagamentoResponseDTO requestDTO = new PagamentoResponseDTO(pagamento);
//        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
//
//        mockMvc.perform(put("/pagamentos/{id}", nonExistingId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequestBody))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//
//    }
//
//    @Test
//    void deletePagamentoShouldReturn204WhenIdExists() throws Exception{
//
//        mockMvc.perform(delete("/pagamentos/{id}", existingId))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void deletePagamentoShouldReturn404WhenIdDoesNotExist() throws Exception{
//
//        mockMvc.perform(delete("/pagamentos/{id}", nonExistingId))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }

}
