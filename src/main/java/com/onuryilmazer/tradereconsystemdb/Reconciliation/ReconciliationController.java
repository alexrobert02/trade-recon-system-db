package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;

    @PostMapping("/start")
    public void startReconciliation() {
        reconciliationService.startReconciliation();
    }

    @GetMapping("/differences/{runId}")
    public List<ReconciliationDifference> differences(@PathVariable("runId") Long runId) {
        return reconciliationService.getDifferencesByRunId(runId);
    }

    @GetMapping("/status/{runId}")
    public ReconciliationRun getReconciliationStatus(@PathVariable("runId") Long runId) {
        return reconciliationService.getReconciliationRun(runId).get();
    }
}
