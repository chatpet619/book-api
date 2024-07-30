package com.example.demo.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // Ensure to use the test profile if you have separate application-test.yml or properties
class AppConfigPropertiesTest {

    @Autowired
    private AppConfigProperties appConfigProperties;

    @Test
    @DisplayName("Test Isbn length")
    void testIsbnMaxLength() {
        assertNotNull(appConfigProperties);
        assertEquals(13, appConfigProperties.getIsbnMaxLength());
    }
}
