package com.onuryilmazer.tradereconsystemdb.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping
    public Iterable<Trade> getTrades() {
        return tradeService.getAllTrades();
    }

    @GetMapping("/{id}")
    public Trade getTradeById(@PathVariable int id) {
        return tradeService.getTradeById(id).orElseThrow();
    }

    @PostMapping
    public Trade createTrade(@RequestBody Trade trade) {
        return tradeService.addTrade(trade);
    }

    @DeleteMapping("/{id}")
    public void deleteTrade(@PathVariable int id) {
        tradeService.deleteTrade(id);
    }

}
