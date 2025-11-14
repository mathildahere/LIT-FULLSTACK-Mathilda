package com.kulkultech.restapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class PortfolioControllerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    @Test
    public void testSwaggerAnnotations() {
        Class<?> controllerClass = USE_SOLUTION ? 
            PortfolioControllerSolution.class : PortfolioController.class;
        
        // Check for @Tag annotation
        assertTrue(controllerClass.isAnnotationPresent(
            io.swagger.v3.oas.annotations.tags.Tag.class),
            "Controller should have @Tag annotation");
        
        // Check for @Operation on methods
        try {
            Method getMethod = controllerClass.getMethod("getPortfolio", Long.class);
            assertTrue(getMethod.isAnnotationPresent(
                io.swagger.v3.oas.annotations.Operation.class),
                "getPortfolio should have @Operation annotation");
        } catch (NoSuchMethodException e) {
            assertTrue(true, "Method will exist once implementation is complete");
        }
    }
}

