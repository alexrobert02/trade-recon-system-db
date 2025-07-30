package com.onuryilmazer.tradereconsystemdb.Trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
@WithMockUser(roles = "ADMIN")
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;  // mock bean to inject into controller

    @Autowired
    private ObjectMapper objectMapper;

    private Trade sampleTrade;

    @BeforeEach
    void setUp() {
        sampleTrade = new Trade(
                "T12345",
                "AAPL",
                new BigDecimal("150.25"),
                100,
                "Bloomberg",
                LocalDate.of(2025, 7, 30)
        );
        sampleTrade.setId(1L);
        reset(tradeService);
    }

    @Test
    void testGetAllTrades() throws Exception {
        when(tradeService.getAllTrades()).thenReturn(List.of(sampleTrade));

        mockMvc.perform(get("/api/trades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value("T12345"))
                .andExpect(jsonPath("$[0].instrument").value("AAPL"));
    }

    @Test
    void testGetTradeById_Found() throws Exception {
        when(tradeService.getTradeById(1)).thenReturn(Optional.of(sampleTrade));

        mockMvc.perform(get("/api/trades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T12345"))
                .andExpect(jsonPath("$.instrument").value("AAPL"));
    }

    @Test
    void testGetTradeById_NotFound() throws Exception {
        when(tradeService.getTradeById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/trades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateTrade() throws Exception {
        when(tradeService.addTrade(any())).thenReturn(sampleTrade);

        mockMvc.perform(post("/api/trades")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T12345"));
    }

    @Test
    void testDeleteTrade() throws Exception {
        doNothing().when(tradeService).deleteTrade(1);

        mockMvc.perform(delete("/api/trades/1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(tradeService).deleteTrade(1);
    }
}
