package com.onuryilmazer.tradereconsystemdb.Reconciliation;

import com.onuryilmazer.tradereconsystemdb.security.SecurityJpaConfig;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReconciliationController.class)
@WithMockUser
@DisplayName("ReconciliationController Tests")
class ReconciliationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReconciliationService reconciliationService;

    @Autowired
    private ReconciliationController reconciliationController;

    @Test
    @DisplayName("Service is called with the correct reconcilliation ID")
    void serviceIsCalledWithCorrectReconcilliationID() throws Exception {
        long expectedRunId = 1L;

        mockMvc.perform(
                        get("/api/reconciliation/differences/{runId}", expectedRunId)
                )
                .andExpect(
                        status().isOk()
                );

        assertNotNull(reconciliationService);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(reconciliationService).getDifferencesByRunId(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expectedRunId);
    }
}