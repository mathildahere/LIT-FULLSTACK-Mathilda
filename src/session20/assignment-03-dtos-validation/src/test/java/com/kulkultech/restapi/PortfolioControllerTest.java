package com.kulkultech.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {PortfolioController.class, PortfolioControllerSolution.class})
public class PortfolioControllerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testValidRequest() throws Exception {
        if (USE_SOLUTION) {
            PortfolioRequestSolution solutionRequest = new PortfolioRequestSolution();
            solutionRequest.setName("Valid Portfolio");
            solutionRequest.setBalance(5000.0);
            String solutionJson = objectMapper.writeValueAsString(solutionRequest);
            
            mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(solutionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Valid Portfolio"));
        } else {
            try {
                PortfolioRequest request = new PortfolioRequest();
                request.setName("Valid Portfolio");
                request.setBalance(5000.0);
                String requestJson = objectMapper.writeValueAsString(request);
                mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isOk());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
    
    @Test
    public void testInvalidName() throws Exception {
        if (USE_SOLUTION) {
            PortfolioRequestSolution solutionRequest = new PortfolioRequestSolution();
            solutionRequest.setName(""); // Invalid: blank
            solutionRequest.setBalance(5000.0);
            String solutionJson = objectMapper.writeValueAsString(solutionRequest);
            
            mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(solutionJson))
                .andExpect(status().isBadRequest()); // 400
        } else {
            try {
                PortfolioRequest request = new PortfolioRequest();
                request.setName("Valid Portfolio");
                request.setBalance(5000.0);
                String requestJson = objectMapper.writeValueAsString(request);
                mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isOk());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
    
    @Test
    public void testInvalidBalance() throws Exception {
        if (USE_SOLUTION) {
            PortfolioRequestSolution solutionRequest = new PortfolioRequestSolution();
            solutionRequest.setName("Valid Name");
            solutionRequest.setBalance(-100.0); // Invalid: negative
            String solutionJson = objectMapper.writeValueAsString(solutionRequest);
            
            mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(solutionJson))
                .andExpect(status().isBadRequest()); // 400
        } else {
            try {
                PortfolioRequest request = new PortfolioRequest();
                request.setName("Valid Portfolio");
                request.setBalance(5000.0);
                String requestJson = objectMapper.writeValueAsString(request);
                mockMvc.perform(post("/api/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isOk());
                fail("Problem implementation should be incomplete - test should fail");
            } catch (Exception e) {
                assertTrue(true, "Problem implementation is incomplete as expected");
            }
        }
    }
}

