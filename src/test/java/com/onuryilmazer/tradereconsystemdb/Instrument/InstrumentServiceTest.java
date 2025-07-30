package com.onuryilmazer.tradereconsystemdb.Instrument;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class InstrumentServiceTest {

    @Mock
    private com.onuryilmazer.tradereconsystemdb.repository.InstrumentRepository instrumentRepository;

    @InjectMocks
    private com.onuryilmazer.tradereconsystemdb.service.InstrumentService instrumentService;

    private Instrument sampleInstrument;

    @BeforeEach
    void setUp() {
        sampleInstrument = new Instrument();
        sampleInstrument.setId(1L);
        sampleInstrument.setSymbol("ABC");
        sampleInstrument.setName("Test Instrument");
        sampleInstrument.setIsin("ISIN123456");
    }

    @Test
    void saveInstrument_newInstrumentWithExistingSymbol_throwsException() {
        Instrument newInstrument = new Instrument();
        newInstrument.setSymbol("ABC");

        when(instrumentRepository.existsBySymbol("ABC")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> instrumentService.saveInstrument(newInstrument));
    }

    @Test
    void saveInstrument_newInstrument_savesSuccessfully() {
        Instrument newInstrument = new Instrument();
        newInstrument.setSymbol("XYZ");

        when(instrumentRepository.existsBySymbol("XYZ")).thenReturn(false);
        when(instrumentRepository.save(newInstrument)).thenReturn(sampleInstrument);

        Instrument saved = instrumentService.saveInstrument(newInstrument);
        assertEquals("ABC", saved.getSymbol());
    }

    @Test
    void getAllInstruments_returnsList() {
        when(instrumentRepository.findAll()).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.getAllInstruments();
        assertEquals(1, result.size());
    }

    @Test
    void getInstrumentById_found() {
        when(instrumentRepository.findById(1L)).thenReturn(Optional.of(sampleInstrument));
        Optional<Instrument> result = instrumentService.getInstrumentById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void getInstrumentBySymbol_found() {
        when(instrumentRepository.findBySymbol("ABC")).thenReturn(Optional.of(sampleInstrument));
        Optional<Instrument> result = instrumentService.getInstrumentBySymbol("ABC");
        assertTrue(result.isPresent());
    }

    @Test
    void getInstrumentByIsin_found() {
        when(instrumentRepository.findByIsin("ISIN123456")).thenReturn(Optional.of(sampleInstrument));
        Optional<Instrument> result = instrumentService.getInstrumentByIsin("ISIN123456");
        assertTrue(result.isPresent());
    }

    @Test
    void getInstrumentsByName_exactMatch() {
        when(instrumentRepository.findByNameIgnoreCase("Test Instrument")).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.getInstrumentsByName("Test Instrument");
        assertEquals(1, result.size());
    }

    @Test
    void searchInstrumentsByName_partialMatch() {
        when(instrumentRepository.findByNameContainingIgnoreCase("Test")).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.searchInstrumentsByName("Test");
        assertEquals(1, result.size());
    }

    @Test
    void searchInstrumentsBySymbol_partialMatch() {
        when(instrumentRepository.findBySymbolContainingIgnoreCase("AB")).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.searchInstrumentsBySymbol("AB");
        assertEquals(1, result.size());
    }

    @Test
    void getInstrumentsBySymbols_returnsList() {
        when(instrumentRepository.findBySymbolIn(List.of("ABC"))).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.getInstrumentsBySymbols(List.of("ABC"));
        assertEquals(1, result.size());
    }

    @Test
    void getInstrumentsByIsins_returnsList() {
        when(instrumentRepository.findByIsinIn(List.of("ISIN123456"))).thenReturn(List.of(sampleInstrument));
        List<Instrument> result = instrumentService.getInstrumentsByIsins(List.of("ISIN123456"));
        assertEquals(1, result.size());
    }

    @Test
    void updateInstrument_validUpdate_returnsUpdatedInstrument() {
        Instrument newDetails = new Instrument();
        newDetails.setSymbol("ABC");
        newDetails.setName("Updated");
        newDetails.setIsin("ISIN123456");

        when(instrumentRepository.findById(1L)).thenReturn(Optional.of(sampleInstrument));
        when(instrumentRepository.save(any())).thenReturn(sampleInstrument);

        Instrument updated = instrumentService.updateInstrument(1L, newDetails);
        assertEquals("ABC", updated.getSymbol());
    }

    @Test
    void updateInstrument_symbolAlreadyExists_throwsException() {
        Instrument newDetails = new Instrument();
        newDetails.setSymbol("NEW");

        when(instrumentRepository.findById(1L)).thenReturn(Optional.of(sampleInstrument));
        when(instrumentRepository.existsBySymbol("NEW")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> instrumentService.updateInstrument(1L, newDetails));
    }

    @Test
    void deleteInstrument_exists_deletesSuccessfully() {
        when(instrumentRepository.existsById(1L)).thenReturn(true);
        instrumentService.deleteInstrument(1L);
        verify(instrumentRepository).deleteById(1L);
    }

    @Test
    void deleteInstrument_notFound_throwsException() {
        when(instrumentRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> instrumentService.deleteInstrument(1L));
    }

    @Test
    void existsBySymbol_true() {
        when(instrumentRepository.existsBySymbol("ABC")).thenReturn(true);
        assertTrue(instrumentService.existsBySymbol("ABC"));
    }

    @Test
    void existsByIsin_true() {
        when(instrumentRepository.existsByIsin("ISIN123456")).thenReturn(true);
        assertTrue(instrumentService.existsByIsin("ISIN123456"));
    }

    @Test
    void getInstrumentCount_returnsValue() {
        when(instrumentRepository.count()).thenReturn(5L);
        assertEquals(5L, instrumentService.getInstrumentCount());
    }
}

