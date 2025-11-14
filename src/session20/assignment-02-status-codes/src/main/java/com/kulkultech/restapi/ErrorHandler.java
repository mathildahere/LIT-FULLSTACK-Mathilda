/**
 * Global Exception Handler - HTTP Status Codes and Error Handling
 * 
 * Challenge: Implement global exception handling with proper status codes
 * 
 * Your task: Complete this exception handler class
 * 
 * Concepts covered:
 * - @RestControllerAdvice for global exception handling
 * - @ExceptionHandler for specific exceptions
 * - @ResponseStatus for HTTP status codes
 * - Standardized error responses
 */

package com.kulkultech.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// SOLVED: Add @RestControllerAdvice annotation
// This makes this class handle exceptions globally
@RestControllerAdvice
public class ErrorHandler {
    
    // SOLVED: Add @ExceptionHandler(PortfolioNotFoundException.class)
    // SOLVED: Add @ResponseStatus(HttpStatus.NOT_FOUND)
    // This method should handle PortfolioNotFoundException
    @ExceptionHandler(PortfolioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePortfolioNotFound(PortfolioNotFoundException e/* SOLVED: Add PortfolioNotFoundException parameter */) {
        // SOLVED: Create ErrorResponse with status 404 and exception message
        // SOLVED: Return the ErrorResponse
        return new ErrorResponse(404, e.getMessage());
    }
    
    // SOLVED: Add @ExceptionHandler(Exception.class) for generic exceptions
    // SOLVED: Add @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // This method should handle all other exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e/* SOLVED: Add Exception parameter */) {
        // SOLVED: Create ErrorResponse with status 500 and generic message
        // SOLVED: Return the ErrorResponse
        return new ErrorResponse(500, "An error occurred: " + e.getMessage());
    }
}

