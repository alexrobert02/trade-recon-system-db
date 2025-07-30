package com.onuryilmazer.tradereconsystemdb.Trade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

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
    }

    @Test
    void testGetAllTrades() {
        when(tradeRepository.findAll()).thenReturn(List.of(sampleTrade));

        Iterable<Trade> trades = tradeService.getAllTrades();

        assertNotNull(trades);
        List<Trade> tradeList = (List<Trade>) trades;
        assertEquals(1, tradeList.size());
        assertEquals("T12345", tradeList.get(0).getTradeId());
        verify(tradeRepository).findAll();
    }

    @Test
    void testGetTradeById_Found() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(sampleTrade));

        Optional<Trade> result = tradeService.getTradeById(1);

        assertTrue(result.isPresent());
        assertEquals("T12345", result.get().getTradeId());
        verify(tradeRepository).findById(1);
    }

    @Test
    void testGetTradeById_NotFound() {
        when(tradeRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Trade> result = tradeService.getTradeById(999);

        assertFalse(result.isPresent());
        verify(tradeRepository).findById(999);
    }

    @Test
    void testAddTrade() {
        when(tradeRepository.save(sampleTrade)).thenReturn(sampleTrade);

        Trade savedTrade = tradeService.addTrade(sampleTrade);

        assertNotNull(savedTrade);
        assertEquals("T12345", savedTrade.getTradeId());
        verify(tradeRepository).save(sampleTrade);
    }

    @Test
    void testDeleteTrade_Exists() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(sampleTrade));

        tradeService.deleteTrade(1);

        verify(tradeRepository).findById(1);
        verify(tradeRepository).delete(sampleTrade);
    }

    @Test
    void testDeleteTrade_NotExists() {
        when(tradeRepository.findById(999)).thenReturn(Optional.empty());

        tradeService.deleteTrade(999);

        verify(tradeRepository).findById(999);
        verify(tradeRepository, never()).delete(any());
    }
}

