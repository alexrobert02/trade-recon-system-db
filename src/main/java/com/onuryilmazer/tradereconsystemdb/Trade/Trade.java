package com.onuryilmazer.tradereconsystemdb.Trade;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "trade_id", nullable = false)
    private String tradeId;


    @Column(name = "instrument", nullable = false)
    private String instrument;


    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @Column(name = "source_system", nullable = false)
    private String sourceSystem;


    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;


    public Trade() {}

    public Trade(String tradeId, String instrument, BigDecimal price,
                 Integer quantity, String sourceSystem, LocalDate tradeDate) {
        this.tradeId = tradeId;
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
        this.sourceSystem = sourceSystem;
        this.tradeDate = tradeDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public String getInstrument() { return instrument; }
    public void setInstrument(String instrument) { this.instrument = instrument; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }
}