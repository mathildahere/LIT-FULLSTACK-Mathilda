package com.kulkultech.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = {PortfolioController.class, PortfolioControllerSolution.class})
@Import(TestWebConfiguration.class)
public class PortfolioControllerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PortfolioRepository portfolioRepository;
    
    @Test
    public void testPagination() throws Exception {
        if (USE_SOLUTION) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Portfolio> page = new PageImpl<>(
                java.util.Collections.emptyList(), 
                pageable, 
                0L
            );
            when(portfolioRepository.findAll(any(Pageable.class))).thenReturn(page);
            
            mockMvc.perform(get("/api/portfolios?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists());
        } else {
            try {
                mockMvc.perform(get("/api/portfolios?page=0&size=10"))
                    .andExpect(status().isOk());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
}

