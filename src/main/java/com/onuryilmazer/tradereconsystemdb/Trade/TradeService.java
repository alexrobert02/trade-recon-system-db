package com.onuryilmazer.tradereconsystemdb.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public Iterable<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(int id) {
        return tradeRepository.findById(id);
    }

    public Trade addTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void deleteTrade(int id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            tradeRepository.delete(trade.get());
        }
    }
}
