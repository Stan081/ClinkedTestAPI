package com.clinked.article_api.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseUtilTest {

    @Test
    void testSuccess() {
        ResponseEntity<Object> response = ResponseUtil.success("Success", Map.of("key", "value"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("success", ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Success", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(Map.of("key", "value"), ((Map<?, ?>) response.getBody()).get("data"));

        // Failure cases
        assertEquals(false, ((Map<?, ?>) response.getBody()).containsKey("error"));
    }

    @Test
    void testPaginatedSuccess() {
        ResponseEntity<Object> response = ResponseUtil.paginatedSuccess("Success", Map.of("key", "value"), 1, 10, 100);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("success", ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Success", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(Map.of("key", "value"), ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals(10, ((Map<?, ?>) response.getBody()).get("totalPages"));
        assertEquals(100L, ((Map<?, ?>) response.getBody()).get("totalElements"));
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("currentPage"));

        // Failure cases
        assertEquals(false, ((Map<?, ?>) response.getBody()).containsKey("error"));
    }

    @Test
    void testError() {
        ResponseEntity<Object> response = ResponseUtil.error("Error", 500, "Details");

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("error", ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Error", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals("Details", ((Map<?, ?>) response.getBody()).get("error"));

        // Failure cases
        assertEquals(false, ((Map<?, ?>) response.getBody()).containsKey("data"));
    }
}