package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reconciliation_run")
public class ReconciliationRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_date", nullable = false)
    private LocalDateTime runDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "matched_count", nullable = false)
    private Integer matchedCount;

    @Column(name = "unmatched_count", nullable = false)
    private Integer unmatchedCount;


    public ReconciliationRun() {
        this.runDate = LocalDateTime.now();
        this.status = "RUNNING";
        this.matchedCount = 0;
        this.unmatchedCount = 0;
    }

    @OneToMany(mappedBy = "reconciliationRun")
    private List<ReconciliationDifference> reconciliationDifferences;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getRunDate() { return runDate; }
    public void setRunDate(LocalDateTime runDate) { this.runDate = runDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getMatchedCount() { return matchedCount; }
    public void setMatchedCount(Integer matchedCount) { this.matchedCount = matchedCount; }

    public Integer getUnmatchedCount() { return unmatchedCount; }
    public void setUnmatchedCount(Integer unmatchedCount) { this.unmatchedCount = unmatchedCount; }

    public List<ReconciliationDifference> getReconciliationDifferences() {
        return reconciliationDifferences;
    }

    public void setReconciliationDifferences(List<ReconciliationDifference> reconciliationDifferences) {
        this.reconciliationDifferences = reconciliationDifferences;
    }
}