package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import com.onuryilmazer.tradereconsystemdb.Trade.Trade;
import com.onuryilmazer.tradereconsystemdb.Trade.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("ReconciliationService Tests")
class ReconciliationServiceTest {
    @Autowired
    private ReconciliationService reconciliationService;

    @MockitoBean
    private TradeRepository tradeRepository;

    @MockitoBean
    private ReconciliationRunRepository reconciliationRunRepository;

    @MockitoBean
    private ReconciliationDifferenceRepository reconciliationDifferenceRepository;

    @Test
    @DisplayName("Non-matching trades are identified correctly")
    @Transactional  //spring automatically rolls back transactions after test
    void testNonMatchingTrades() {
        LocalDate tradeDate = LocalDate.of(2020, 1, 1);

        List<Trade> nonMatchingTrades = List.of(
                new Trade("T1000", "AAPL", BigDecimal.valueOf(160), 20, "Bloomberg", tradeDate),
                new Trade("T1000", "AAPL", BigDecimal.valueOf(170), 30, "Bloomberg's Evil Twin", tradeDate)
        );

        when(tradeRepository.findAll()).thenReturn(nonMatchingTrades);

        // Start reconciliation
        ReconciliationRun myReconciliationRun = new ReconciliationRun();
        when(reconciliationRunRepository.save(any())).thenReturn(myReconciliationRun);
        ReconciliationRun run = reconciliationService.startReconciliation();

        // Check if the run has unmatched trades
        assertNotNull(run);
        assertTrue(run.getUnmatchedCount() > 0, "There should be unmatched trades");

        assertThat(myReconciliationRun.getMatchedCount()).isEqualTo(0);
        assertThat(myReconciliationRun.getUnmatchedCount()).isEqualTo(1);


        ArgumentCaptor<List<ReconciliationDifference>> captor2 = ArgumentCaptor.forClass(List.class);
        verify(reconciliationDifferenceRepository).saveAll(captor2.capture());
        List<ReconciliationDifference> capturedDifferences = captor2.getValue();
        assertThat(capturedDifferences).isNotEmpty();
        assertThat(capturedDifferences.get(0).getTradeId()).isEqualTo("T1000");
        assertThat(capturedDifferences.get(0).getFieldName()).isEqualTo("price");

    }
}