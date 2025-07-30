package com.onuryilmazer.tradereconsystemdb.Instrument;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "ADMIN")
class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InstrumentService instrumentService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getInstrumentById_found() throws Exception {
        when(instrumentService.getInstrumentById(1L)).thenReturn(Optional.of(sampleInstrument));

        mockMvc.perform(get("/api/instruments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("ABC"));
    }

    @Test
    void getInstrumentById_notFound() throws Exception {
        when(instrumentService.getInstrumentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/instruments/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createInstrument_success() throws Exception {
        Instrument toCreate = new Instrument();
        toCreate.setSymbol("XYZ");
        toCreate.setName("New");
        toCreate.setIsin("ISIN999");

        when(instrumentService.saveInstrument(any())).thenReturn(sampleInstrument);

        mockMvc.perform(post("/api/instruments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("ABC"));
    }

    @Test
    void createInstrument_conflict() throws Exception {
        Instrument toCreate = new Instrument();
        toCreate.setSymbol("ABC");

        when(instrumentService.saveInstrument(any())).thenThrow(new RuntimeException("already exists"));

        mockMvc.perform(post("/api/instruments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("already exists")));
    }

    @Test
    void updateInstrument_success() throws Exception {
        Instrument updated = new Instrument();
        updated.setSymbol("DEF");
        updated.setName("Updated");
        updated.setIsin("ISIN000");

        when(instrumentService.updateInstrument(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/instruments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("DEF"));
    }

    @Test
    void deleteInstrument_success() throws Exception {
        doNothing().when(instrumentService).deleteInstrument(1L);

        mockMvc.perform(delete("/api/instruments/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInstrument_notFound() throws Exception {
        doThrow(new RuntimeException("not found")).when(instrumentService).deleteInstrument(1L);

        mockMvc.perform(delete("/api/instruments/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void existsBySymbol_returnsTrue() throws Exception {
        when(instrumentService.existsBySymbol("ABC")).thenReturn(true);

        mockMvc.perform(get("/api/instruments/exists/symbol/ABC"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void getInstrumentCount_returnsValue() throws Exception {
        when(instrumentService.getInstrumentCount()).thenReturn(5L);

        mockMvc.perform(get("/api/instruments/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}

