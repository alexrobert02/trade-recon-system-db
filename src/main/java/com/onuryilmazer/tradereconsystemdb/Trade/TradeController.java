package com.onuryilmazer.tradereconsystemdb.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/trades")
@PreAuthorize("hasRole('ADMIN')")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Trade> getTrades() {
        return tradeService.getAllTrades();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Trade getTradeById(@PathVariable int id) {
        return tradeService.getTradeById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Trade createTrade(@RequestBody Trade trade) {
        return tradeService.addTrade(trade);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTrade(@PathVariable int id) {
        tradeService.deleteTrade(id);
    }

}
