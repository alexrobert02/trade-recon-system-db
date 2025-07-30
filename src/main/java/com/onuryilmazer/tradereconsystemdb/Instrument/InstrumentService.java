package com.onuryilmazer.tradereconsystemdb.Instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    // Create or update an instrument
    public Instrument saveInstrument(Instrument instrument) {
        // Check if symbol already exists (for new instruments)
        if (instrument.getId() == null && instrumentRepository.existsBySymbol(instrument.getSymbol())) {
            throw new RuntimeException("Instrument with symbol '" + instrument.getSymbol() + "' already exists");
        }
        return instrumentRepository.save(instrument);
    }

    // Get all instruments
    @Transactional(readOnly = true)
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    // Get instrument by ID
    @Transactional(readOnly = true)
    public Optional<Instrument> getInstrumentById(Long id) {
        return instrumentRepository.findById(id);
    }

    // Get instrument by symbol
    @Transactional(readOnly = true)
    public Optional<Instrument> getInstrumentBySymbol(String symbol) {
        return instrumentRepository.findBySymbol(symbol);
    }

    // Get instrument by ISIN
    @Transactional(readOnly = true)
    public Optional<Instrument> getInstrumentByIsin(String isin) {
        return instrumentRepository.findByIsin(isin);
    }

    // Get instruments by name (exact match, case-insensitive)
    @Transactional(readOnly = true)
    public List<Instrument> getInstrumentsByName(String name) {
        return instrumentRepository.findByNameIgnoreCase(name);
    }

    // Search instruments by name (partial match, case-insensitive)
    @Transactional(readOnly = true)
    public List<Instrument> searchInstrumentsByName(String name) {
        return instrumentRepository.findByNameContainingIgnoreCase(name);
    }

    // Search instruments by symbol (partial match, case-insensitive)
    @Transactional(readOnly = true)
    public List<Instrument> searchInstrumentsBySymbol(String symbol) {
        return instrumentRepository.findBySymbolContainingIgnoreCase(symbol);
    }

    // Get instruments by multiple symbols
    @Transactional(readOnly = true)
    public List<Instrument> getInstrumentsBySymbols(List<String> symbols) {
        return instrumentRepository.findBySymbolIn(symbols);
    }

    // Get instruments by multiple ISINs
    @Transactional(readOnly = true)
    public List<Instrument> getInstrumentsByIsins(List<String> isins) {
        return instrumentRepository.findByIsinIn(isins);
    }

    // Update instrument
    public Instrument updateInstrument(Long id, Instrument instrumentDetails) {
        return instrumentRepository.findById(id)
                .map(instrument -> {
                    // Check if the new symbol is already taken by another instrument
                    if (!instrument.getSymbol().equals(instrumentDetails.getSymbol()) && 
                        instrumentRepository.existsBySymbol(instrumentDetails.getSymbol())) {
                        throw new RuntimeException("Instrument with symbol '" + instrumentDetails.getSymbol() + "' already exists");
                    }
                    
                    instrument.setSymbol(instrumentDetails.getSymbol());
                    instrument.setName(instrumentDetails.getName());
                    instrument.setIsin(instrumentDetails.getIsin());
                    return instrumentRepository.save(instrument);
                })
                .orElseThrow(() -> new RuntimeException("Instrument not found with id: " + id));
    }

    // Delete instrument by ID
    public void deleteInstrument(Long id) {
        if (instrumentRepository.existsById(id)) {
            instrumentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Instrument not found with id: " + id);
        }
    }

    // Check if instrument exists by symbol
    @Transactional(readOnly = true)
    public boolean existsBySymbol(String symbol) {
        return instrumentRepository.existsBySymbol(symbol);
    }

    // Check if instrument exists by ISIN
    @Transactional(readOnly = true)
    public boolean existsByIsin(String isin) {
        return instrumentRepository.existsByIsin(isin);
    }

    // Get count of all instruments
    @Transactional(readOnly = true)
    public long getInstrumentCount() {
        return instrumentRepository.count();
    }
}
