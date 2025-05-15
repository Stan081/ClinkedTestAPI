package com.clinked.article_api.utils;

import org.springframework.http.ResponseEntity;
import java.util.Map;

public class ResponseUtil {

    public static <T> ResponseEntity<Object> success(String message, T data) {
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", message,
                "data", data
        ));
    }

    public static <T> ResponseEntity<Object> paginatedSuccess(String message, T data, int page, int size, long totalElements) {
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", message,
                "data", data,
                "totalPages", size,
                "totalElements",totalElements,
                "currentPage", page
        ));
    }

    public static ResponseEntity<Object> error(String message, int statusCode, String errorDetails) {
        return ResponseEntity.status(statusCode).body(Map.of(
                "status", "error",
                "message", message,
                "error", errorDetails
        ));
    }
}