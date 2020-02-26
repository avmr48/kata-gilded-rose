package com.gildedrose;

import org.apache.commons.lang3.tuple.Pair;

import static com.gildedrose.Functions.AnItem.updateQualityOn;
import static org.assertj.core.api.Assertions.assertThat;

public class Functions {

    /**
     * Assert state transition : AAA
     */
    static void assertStateTransition(String name, Pair<Integer, Integer> input, Pair<Integer, Integer> expected) {
        assertThat(updateQualityOn(AnItem.of(name, input.getLeft(), input.getRight())))
                .isEqualToComparingFieldByField(AnItem.of(name, expected.getLeft(), expected.getRight()));
    }

    /**
     * An item
     */
    public static class AnItem {
        /**
         * Factory method: get an item
         */
        public static Item of(String backstage, int si, int q) {
            return new Item(backstage, si, q);
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
