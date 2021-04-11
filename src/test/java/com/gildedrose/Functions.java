package com.gildedrose;

import org.apache.commons.lang3.tuple.Pair;

import static com.gildedrose.Functions.AnItem.updateQualityOn;
import static org.assertj.core.api.Assertions.assertThat;

public class Functions {

    /**
     * An item
     */
    public static class AnItem {
        /**
         * Factory method: get an item
         */
        public static Item of(String type, int sellIn, int quantity) {
            return new Item(type, sellIn, quantity);
        }

        /**
         * get a sut and update quality
         */
        static Item updateQualityOn(Item item) {
            Item[] items = new Item[]{item};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            return item;
        }

        static void assertState(Item item, String name, int expectedSellIn, int expectedQuality) {
            assertThat(item).isEqualToComparingFieldByField(AnItem.of(name, expectedSellIn, expectedQuality));
        }
    }

    /**
     * A list of items
     */
    public static class ItemList {
        /**
         * Factory method: get a list of items
         */
        public static Item[] of(Item... items) {
            return items;
        }
    }
}
