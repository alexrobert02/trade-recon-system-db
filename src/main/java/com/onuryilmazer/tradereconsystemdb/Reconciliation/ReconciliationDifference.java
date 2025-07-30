package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "reconciliation_difference")
public class ReconciliationDifference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_id", nullable = false)
    private String tradeId;

    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;

    @Column(name = "value_system_a")
    private String valueSystemA;

    @Column(name = "value_system_b")
    private String valueSystemB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reconciliation_run_id", nullable = false)
    @JsonBackReference("run-difference")
    private ReconciliationRun reconciliationRun;


    public ReconciliationDifference() {}

    public ReconciliationDifference(String tradeId, String fieldName,
                                    String valueSystemA, String valueSystemB,
                                    ReconciliationRun reconciliationRun) {
        this.tradeId = tradeId;
        this.fieldName = fieldName;
        this.valueSystemA = valueSystemA;
        this.valueSystemB = valueSystemB;
        this.reconciliationRun = reconciliationRun;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getValueSystemA() { return valueSystemA; }
    public void setValueSystemA(String valueSystemA) { this.valueSystemA = valueSystemA; }

    public String getValueSystemB() { return valueSystemB; }
    public void setValueSystemB(String valueSystemB) { this.valueSystemB = valueSystemB; }

    public ReconciliationRun getReconciliationRun() { return reconciliationRun; }
    public void setReconciliationRun(ReconciliationRun reconciliationRun) {
        this.reconciliationRun = reconciliationRun;
    }
}