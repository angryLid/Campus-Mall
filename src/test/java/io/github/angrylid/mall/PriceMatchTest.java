package io.github.angrylid.mall;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PriceMatchTest {
    @Test
    void test() {
        var re = "^\\d{1,4}(\\.\\d{2})?$";

        assertThat("0000.".matches(re)).isFalse();
        assertThat("0000.0".matches(re)).isFalse();
        assertThat("".matches(re)).isFalse();
        assertThat("12343".matches(re)).isFalse();
        assertThat("12345.667".matches(re)).isFalse();
        assertThat("3.666".matches(re)).isFalse();
        assertThat("6.99".matches(re)).isTrue();
        assertThat("9999.99".matches(re)).isTrue();
        assertThat("0000.00".matches(re)).isTrue();
        assertThat("0.00".matches(re)).isTrue();
    }
}
