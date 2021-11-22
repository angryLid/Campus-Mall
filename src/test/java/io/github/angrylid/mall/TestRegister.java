package io.github.angrylid.mall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestRegister {
    @Test
    public void testHelloWorld(){
        assertEquals("Hello", "Hellox".replaceAll("x", ""));
    }

    @Test
    public void testNotPassed(){
        assertEquals("Hello", "Hellox".replaceAll("l", ""));
    }
}
