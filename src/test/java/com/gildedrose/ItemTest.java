package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void item() {
        Item example = new Item("EXAMPLE", 1, 1);
        assertThat(example.toString()).isEqualTo("EXAMPLE, 1, 1");
    }
}
