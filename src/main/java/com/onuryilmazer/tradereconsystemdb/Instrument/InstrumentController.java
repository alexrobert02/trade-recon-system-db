package com.onuryilmazer.tradereconsystemdb.Instrument;

import com.onuryilmazer.tradereconsystemdb.Instrument.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instruments")
@CrossOrigin(origins = "*")
public class InstrumentController {

    private final InstrumentService instrumentService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    // Get all instruments
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(instruments);
    }

    // Get instrument by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getInstrumentById(@PathVariable Long id) {
        Optional<Instrument> instrument = instrumentService.getInstrumentById(id);
        return instrument.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Get instrument by symbol
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<Instrument> getInstrumentBySymbol(@PathVariable String symbol) {
        Optional<Instrument> instrument = instrumentService.getInstrumentBySymbol(symbol);
        return instrument.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Get instrument by ISIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/isin/{isin}")
    public ResponseEntity<Instrument> getInstrumentByIsin(@PathVariable String isin) {
        Optional<Instrument> instrument = instrumentService.getInstrumentByIsin(isin);
        return instrument.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Get instruments by name (exact match)
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Instrument>> getInstrumentsByName(@PathVariable String name) {
        List<Instrument> instruments = instrumentService.getInstrumentsByName(name);
        return ResponseEntity.ok(instruments);
    }

    // Search instruments by name (partial match)
    @GetMapping("/search/name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Instrument>> searchInstrumentsByName(@RequestParam String name) {
        List<Instrument> instruments = instrumentService.searchInstrumentsByName(name);
        return ResponseEntity.ok(instruments);
    }

    // Search instruments by symbol (partial match)
    @GetMapping("/search/symbol")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Instrument>> searchInstrumentsBySymbol(@RequestParam String symbol) {
        List<Instrument> instruments = instrumentService.searchInstrumentsBySymbol(symbol);
        return ResponseEntity.ok(instruments);
    }

    // Get instruments by multiple symbols
    @PostMapping("/symbols")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Instrument>> getInstrumentsBySymbols(@RequestBody List<String> symbols) {
        List<Instrument> instruments = instrumentService.getInstrumentsBySymbols(symbols);
        return ResponseEntity.ok(instruments);
    }

    // Get instruments by multiple ISINs
    @PostMapping("/isins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Instrument>> getInstrumentsByIsins(@RequestBody List<String> isins) {
        List<Instrument> instruments = instrumentService.getInstrumentsByIsins(isins);
        return ResponseEntity.ok(instruments);
    }

    // Create a new instrument
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createInstrument(@RequestBody Instrument instrument) {
        try {
            Instrument savedInstrument = instrumentService.saveInstrument(instrument);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInstrument);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                               .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                               .body("Error creating instrument");
        }
    }

    // Update an existing instrument
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateInstrument(@PathVariable Long id, @RequestBody Instrument instrumentDetails) {
        try {
            Instrument updatedInstrument = instrumentService.updateInstrument(id, instrumentDetails);
            return ResponseEntity.ok(updatedInstrument);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                   .body("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                               .body("Error updating instrument");
        }
    }

    // Delete an instrument
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteInstrument(@PathVariable Long id) {
        try {
            instrumentService.deleteInstrument(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Check if instrument exists by symbol
    @GetMapping("/exists/symbol/{symbol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsBySymbol(@PathVariable String symbol) {
        boolean exists = instrumentService.existsBySymbol(symbol);
        return ResponseEntity.ok(exists);
    }

    // Check if instrument exists by ISIN
    @GetMapping("/exists/isin/{isin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsByIsin(@PathVariable String isin) {
        boolean exists = instrumentService.existsByIsin(isin);
        return ResponseEntity.ok(exists);
    }

    // Get total count of instruments
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getInstrumentCount() {
        long count = instrumentService.getInstrumentCount();
        return ResponseEntity.ok(count);
    }
}
