package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReconciliationDifferenceRepository extends JpaRepository<ReconciliationDifference, Long> {

    List<ReconciliationDifference> findByReconciliationRunId(Long runId);


}