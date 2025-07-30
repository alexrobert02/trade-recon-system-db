package com.onuryilmazer.tradereconsystemdb.repository;

import com.onuryilmazer.tradereconsystemdb.Instrument.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    
    // Find instrument by symbol (unique)
    Optional<Instrument> findBySymbol(String symbol);
    
    // Find instrument by ISIN
    Optional<Instrument> findByIsin(String isin);
    
    // Find instruments by name (case-insensitive)
    List<Instrument> findByNameIgnoreCase(String name);
    
    // Find instruments by name containing (case-insensitive search)
    List<Instrument> findByNameContainingIgnoreCase(String name);
    
    // Find instruments by symbol containing (case-insensitive search)
    List<Instrument> findBySymbolContainingIgnoreCase(String symbol);
    
    // Custom query to find instruments by multiple symbols
    @Query("SELECT i FROM Instrument i WHERE i.symbol IN :symbols")
    List<Instrument> findBySymbolIn(@Param("symbols") List<String> symbols);
    
    // Custom query to find instruments by multiple ISINs
    @Query("SELECT i FROM Instrument i WHERE i.isin IN :isins")
    List<Instrument> findByIsinIn(@Param("isins") List<String> isins);
    
    // Check if instrument exists by symbol
    boolean existsBySymbol(String symbol);
    
    // Check if instrument exists by ISIN
    boolean existsByIsin(String isin);
}
