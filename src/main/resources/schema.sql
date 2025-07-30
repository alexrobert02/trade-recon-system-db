CREATE TABLE trade
(
    id            SERIAL PRIMARY KEY,
    trade_id      VARCHAR(255),
    instrument    VARCHAR(255),
    price         NUMERIC,
    quantity      INTEGER,
    source_system VARCHAR(255),
    trade_date    DATE
);

CREATE TABLE instrument
(
    id     SERIAL PRIMARY KEY,
    symbol VARCHAR(50) NOT NULL,
    name   VARCHAR(100),
    isin   VARCHAR(20)
);

CREATE TABLE reconciliation_run
(
    id              SERIAL PRIMARY KEY,
    run_date        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status          VARCHAR(20),
    matched_count   INTEGER,
    unmatched_count INTEGER
);


CREATE TABLE reconciliation_difference
(
    id                    SERIAL PRIMARY KEY,
    trade_id              VARCHAR(255) NOT NULL,
    field_name            VARCHAR(100),
    value_system_a        VARCHAR(255),
    value_system_b        VARCHAR(255),
    reconciliation_run_id INTEGER REFERENCES reconciliation_run (id) ON DELETE CASCADE
);

