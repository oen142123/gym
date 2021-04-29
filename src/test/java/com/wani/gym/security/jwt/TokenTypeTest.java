package com.wani.gym.security.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTypeTest {


    @Test
    void testTokenType() {
        TokenType.ACCESS.getLifeTime();
    }
}