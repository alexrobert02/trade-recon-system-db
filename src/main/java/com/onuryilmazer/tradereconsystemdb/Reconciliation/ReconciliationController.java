package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/start")
    public void startReconciliation() {
        reconciliationService.startReconciliation();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/differences/{runId}")
    public List<ReconciliationDifference> differences(@PathVariable("runId") Long runId) {
        return reconciliationService.getDifferencesByRunId(runId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{runId}")
    public ReconciliationRun getReconciliationStatus(@PathVariable("runId") Long runId) {
        return reconciliationService.getReconciliationRun(runId).get();
    }
}
