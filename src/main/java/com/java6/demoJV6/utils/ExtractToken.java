package com.java6.demoJV6.utils;

public class ExtractToken {
    public static Integer extractUserIdFromToken(String token) {
        if (token != null && token.startsWith("mock-token-")) {
            return Integer.parseInt(token.replace("mock-token-", ""));
        }
        throw new RuntimeException("Invalid token");
    }

}
