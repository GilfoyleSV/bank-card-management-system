package com.bank.cardmanagement.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    @Test
    @DisplayName("Should correctly mask 16-digit card number")
    void testMaskCardNumber() {
        String rawNumber = "1234567890123456";
        String masked = CardUtils.maskCardNumber(rawNumber);

        assertEquals("**** **** **** 3456", masked);
    }

    @Test
    @DisplayName("Should return default mask for null or short input")
    void testMaskCardNumberShortOrNull() {
        assertEquals("****", CardUtils.maskCardNumber(null));
        assertEquals("****", CardUtils.maskCardNumber("123"));
    }

    @Test
    @DisplayName("Should generate 16-digit random card number")
    void testGenerateCardNumber() {
        String generated = CardUtils.generateCardNumber();

        assertNotNull(generated);
        assertEquals(16, generated.length());
        assertTrue(generated.matches("\\d{16}"));
    }
}
