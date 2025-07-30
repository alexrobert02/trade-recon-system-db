package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import com.onuryilmazer.tradereconsystemdb.Trade.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReconciliationService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ReconciliationRunRepository reconciliationRunRepository;

    @Autowired
    private ReconciliationDifferenceRepository reconciliationDifferenceRepository;

    @Transactional
    public ReconciliationRun startReconciliation() {
        // Create new reconciliation run
        ReconciliationRun run = new ReconciliationRun();
        run = reconciliationRunRepository.save(run);

        // Get all trades grouped by trade_id
        List<Trade> allTrades = tradeRepository.findAll();
        Map<String, List<Trade>> tradesByTradeId = allTrades.stream()
                .collect(Collectors.groupingBy(Trade::getTradeId));

        int matchedCount = 0;
        int unmatchedCount = 0;

        for (Map.Entry<String, List<Trade>> entry : tradesByTradeId.entrySet()) {
            String tradeId = entry.getKey();
            List<Trade> trades = entry.getValue();

            if (trades.size() == 1) {
                // Only one trade with this ID - consider it unmatched
                unmatchedCount++;
            } else if (trades.size() > 1) {
                // Multiple trades with same ID - compare them
                List<ReconciliationDifference> differences = compareTrades(trades, run);

                if (differences.isEmpty()) {
                    matchedCount++;
                } else {
                    unmatchedCount++;
                    reconciliationDifferenceRepository.saveAll(differences);
                }
            }
        }

        // Update run with results
        run.setMatchedCount(matchedCount);
        run.setUnmatchedCount(unmatchedCount);
        run.setStatus("COMPLETED");

        return reconciliationRunRepository.save(run);
    }

    private List<ReconciliationDifference> compareTrades(List<Trade> trades, ReconciliationRun run) {
        List<ReconciliationDifference> differences = new ArrayList<>();
        String tradeId = trades.get(0).getTradeId();

        // Compare each pair of trades
        for (int i = 0; i < trades.size() - 1; i++) {
            for (int j = i + 1; j < trades.size(); j++) {
                Trade trade1 = trades.get(i);
                Trade trade2 = trades.get(j);

                // Compare price
                if (trade1.getPrice().compareTo(trade2.getPrice()) != 0) {
                    differences.add(new ReconciliationDifference(
                            tradeId, "price",
                            trade1.getPrice().toString(), trade2.getPrice().toString(), run));
                }

                // Compare quantity
                if (!Objects.equals(trade1.getQuantity(), trade2.getQuantity())) {
                    differences.add(new ReconciliationDifference(
                            tradeId, "quantity",
                            trade1.getQuantity().toString(), trade2.getQuantity().toString(), run));
                }


            }
        }

        return differences;
    }

    public Optional<ReconciliationRun> getReconciliationRun(Long runId) {
        return reconciliationRunRepository.findById(runId);
    }

    public List<ReconciliationDifference> getDifferencesByRunId(Long runId) {
        return reconciliationDifferenceRepository.findByReconciliationRunId(runId);
    }

    public List<ReconciliationRun> getAllReconciliationRuns() {
        return reconciliationRunRepository.findAllByOrderByRunDateDesc();
    }
}