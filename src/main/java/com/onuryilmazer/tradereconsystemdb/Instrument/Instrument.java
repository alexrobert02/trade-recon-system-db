package com.onuryilmazer.tradereconsystemdb.Instrument;

import jakarta.persistence.*;

@Entity
@Table(name = "instrument")
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, length = 50, unique = true)
    private String symbol;


    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "isin", length = 20)
    private String isin;

    public Instrument() {}

    public Instrument(String symbol, String name, String isin) {
        this.symbol = symbol;
        this.name = name;
        this.isin = isin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIsin() { return isin; }
    public void setIsin(String isin) { this.isin = isin; }
}