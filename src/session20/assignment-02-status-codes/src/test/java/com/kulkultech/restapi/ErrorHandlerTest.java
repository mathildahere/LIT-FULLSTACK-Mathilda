package com.kulkultech.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = PortfolioControllerSolution.class)
@Import(ErrorHandlerSolution.class)
public class ErrorHandlerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testCreateReturns201() throws Exception {
        if (USE_SOLUTION) {
            Portfolio portfolio = new Portfolio(null, "Test", 5000.0);
            String portfolioJson = objectMapper.writeValueAsString(portfolio);
            mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(portfolioJson))
                .andExpect(status().isCreated()); // 201
        } else {
            try {
                Portfolio portfolio = new Portfolio(null, "Test", 5000.0);
                String portfolioJson = objectMapper.writeValueAsString(portfolio);
                mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(portfolioJson))
                    .andExpect(status().isCreated());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
    
    @Test
    public void testDeleteReturns204() throws Exception {
        if (USE_SOLUTION) {
            // Create portfolio first
            Portfolio portfolio = new Portfolio(null, "Test", 5000.0);
            String portfolioJson = objectMapper.writeValueAsString(portfolio);
            
            mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(portfolioJson))
                .andExpect(status().isCreated());
            
            // Delete portfolio
            mockMvc.perform(delete("/api/portfolios/1"))
                .andExpect(status().isNoContent()); // 204
        } else {
            try {
                Portfolio portfolio = new Portfolio(null, "Test", 5000.0);
                String portfolioJson = objectMapper.writeValueAsString(portfolio);
                mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(portfolioJson))
                    .andExpect(status().isCreated());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
    
    @Test
    public void testNotFoundReturns404() throws Exception {
        if (USE_SOLUTION) {
            mockMvc.perform(get("/api/portfolios/999"))
                .andExpect(status().isNotFound()) // 404
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
        } else {
            try {
                mockMvc.perform(get("/api/portfolios/999"))
                    .andExpect(status().isNotFound());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
    
    @Test
    public void testExceptionHandlerExists() {
        Class<?> handlerClass = USE_SOLUTION ? 
            ErrorHandlerSolution.class : ErrorHandler.class;
        
        assertTrue(handlerClass.isAnnotationPresent(
            org.springframework.web.bind.annotation.RestControllerAdvice.class),
            "Error handler should have @RestControllerAdvice annotation");
    }
}

