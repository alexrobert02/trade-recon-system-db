-- TRADE Table
-- Unmatched trades
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1000", "AAPL", 160, 20, "Bloomberg", "2025-06-30");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1000", "AAPL", 160, 25, "Reuters", "2025-06-05");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1001", "GOOG", 160, 22, "Reuters", "2025-06-04");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1001", "GOOG", 158, 22, "Bloomberg", "2025-06-06");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1002", "MSFT", 160, 22, "Reuters", "2025-06-04");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1002", "MSFT", 160, 25, "New York Times", "2025-06-13");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1003", "AAPL", 160, 25, "Reuters", "2025-06-05");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1003", "AAPL", 165, 25, "Bloomberg", "2025-06-06");
-- Matched trades
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1004", "GOOG", 160, 28, "Bloomberg", "2025-06-06");
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES("T1004", "GOOG", 160, 28, "New York Times", "2025-06-10");

-- INSTRUMENT table
INSERT INTO instrument (symbol, name, isin) VALUES("AAPL", "Apple Inc.", "US8342432822");
INSERT INTO instrument (symbol, name, isin) VALUES("GOOG", "Alphabet Inc.", "US3437289291");
INSERT INTO instrument (symbol, name, isin) VALUES("MSFT", "Microsoft Inc.", "US4139824322");

-- RECONCILIATION_RUN table
INSERT INTO reconciliation_run (run_date, status, matched_count, unmatched_count) VALUES("2025-07-30 10:00:00", "COMPLETED", 2, 8);

-- RECONCILIATION_DIFFERENCE table
INSERT INTO reconciliation_difference (trade_id, field_name, value_system_a, value_system_b, reconciliation_run) VALUES("T1000", "quantity", "20", "25", 1);
INSERT INTO reconciliation_difference (trade_id, field_name, value_system_a, value_system_b, reconciliation_run) VALUES("T1001", "price", "160", "158", 1);
INSERT INTO reconciliation_difference (trade_id, field_name, value_system_a, value_system_b, reconciliation_run) VALUES("T1002", "quantity", "22", "25", 1);
INSERT INTO reconciliation_difference (trade_id, field_name, value_system_a, value_system_b, reconciliation_run) VALUES("T1003", "price", "160", "165", 1);