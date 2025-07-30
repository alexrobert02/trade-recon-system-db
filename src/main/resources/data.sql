-- TRADE Table
-- Unmatched trades
INSERT INTO trade VALUES("T1000", "AAPL", 160, 20, "Bloomberg", "2025-06-30");
INSERT INTO trade VALUES("T1000", "AAPL", 160, 25, "Reuters", "2025-06-05");
INSERT INTO trade VALUES("T1001", "GOOG", 160, 22, "Reuters", "2025-06-04");
INSERT INTO trade VALUES("T1001", "GOOG", 158, 22, "Bloomberg", "2025-06-06");
INSERT INTO trade VALUES("T1002", "MSFT", 160, 22, "Reuters", "2025-06-04");
INSERT INTO trade VALUES("T1002", "MSFT", 160, 25, "New York Times", "2025-06-13");
INSERT INTO trade VALUES("T1003", "AAPL", 160, 25, "Reuters", "2025-06-05");
INSERT INTO trade VALUES("T1003", "AAPL", 165, 25, "Bloomberg", "2025-06-06");
-- Matched trades
INSERT INTO trade VALUES("T1004", "GOOG", 160, 28, "Bloomberg", "2025-06-06");
INSERT INTO trade VALUES("T1004", "GOOG", 160, 28, "New York Times", "2025-06-10");

-- INSTRUMENT table
INSERT INTO instrument VALUES("AAPL", "Apple Inc.", "US8342432822");
INSERT INTO instrument VALUES("GOOG", "Alphabet Inc.", "US3437289291");
INSERT INTO instrument VALUES("MSFT", "Microsoft Inc.", "US4139824322");

-- RECONCILIATION_RUN table
INSERT INTO reconciliation_run VALUES("2025-07-30 10:00:00", "COMPLETED", 2, 8);

-- RECONCILIATION_DIFFERENCE table
INSERT INTO reconciliation_difference VALUES("T1000", "quantity", "20", "25", 1);
INSERT INTO reconciliation_difference VALUES("T1001", "price", "160", "158", 1);
INSERT INTO reconciliation_difference VALUES("T1002", "quantity", "22", "25", 1);
INSERT INTO reconciliation_difference VALUES("T1003", "price", "160", "165", 1);