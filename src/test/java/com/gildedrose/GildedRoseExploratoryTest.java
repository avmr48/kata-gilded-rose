package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ok, these tests are not good...
 * I just want to cover the code with some examples!
 */
public class GildedRoseExploratoryTest {

    public static final String EXAMPLE = "EXAMPLE";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private GildedRose app;

    private Item[] of(Item... items) {
        return items;
    }

    private Item item(String backstage, int si, int q) {
        return new Item(backstage, si, q);
    }

    private void updateQualityAndAssertState(Item item, String name, int expectedSellIn, int expectedQuality) {
        app.updateQuality();
        assertState(item, name, expectedSellIn, expectedQuality);
    }

    private void assertState(Item item, String name, int expectedSellIn, int expectedQuality) {
        assertThat(item).isEqualToComparingFieldByField(item(name, expectedSellIn, expectedQuality));
    }

    @Test
    void exploreExampleItem() {
        // All items have a SellIn value which denotes the number of days we have to sell the item
        // All items have a Quality value which denotes how valuable the item is
        app = new GildedRose(of(item(EXAMPLE, 10, 15)));
        assertState(app.items[0], EXAMPLE, 10, 15);

        // At the end of each day our system lowers both values for every item

        // Our system lowers both values for every item
        updateQualityAndAssertState(app.items[0], EXAMPLE, 9, 14);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 8, 13);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 7, 12);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 6, 11);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 5, 10);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 4, 9);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 3, 8);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 2, 7);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 1, 6);
        updateQualityAndAssertState(app.items[0], EXAMPLE, 0, 5);

        // Once the sell by date has passed, Quality degrades twice as fast
        updateQualityAndAssertState(app.items[0], EXAMPLE, -1, 3);
        updateQualityAndAssertState(app.items[0], EXAMPLE, -2, 1);

        updateQualityAndAssertState(app.items[0], EXAMPLE, -3, 0);

        // The Quality of an item is never negative
        updateQualityAndAssertState(app.items[0], EXAMPLE, -4, 0);
        updateQualityAndAssertState(app.items[0], EXAMPLE, -5, 0);
        updateQualityAndAssertState(app.items[0], EXAMPLE, -6, 0);
    }

    @Test
    void exploreAgedBrieItem() {
        app = new GildedRose(of(item(AGED_BRIE, 15, 25)));
        assertState(app.items[0], AGED_BRIE, 15, 25);

        // “Aged Brie” actually increases in Quality the older it gets
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 14, 26);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 13, 27);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 12, 28);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 11, 29);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 10, 30);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 9, 31);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 8, 32);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 7, 33);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 6, 34);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 5, 35);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 4, 36);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 3, 37);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 2, 38);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 1, 39);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, 0, 40);

        // [Surprise] quality decreases twice as fast once sell by date has passed
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -1, 42);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -2, 44);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -3, 46);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -4, 48);

        // The Quality of an item is never more than 50
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -5, 50);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -6, 50);
        updateQualityAndAssertState(app.items[0], AGED_BRIE, -7, 50);
    }

    @Test
    void exploreBackStages() {
        /*
        “Backstage passes”, like aged brie, increases in Quality as it’s SellIn value approaches;
        Quality increases by 2 when there are 10 days or less
        and by 3 when there are 5 days or less
        but Quality drops to 0 after the concert
         */

        app = new GildedRose(of(item(BACKSTAGE, 11, 28)));
        assertState(app.items[0], BACKSTAGE, 11, 28);

        // “Backstage passes”, like aged brie, increases in Quality as it’s SellIn value approaches;
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 10, 29);
        // Quality increases by 2 when there are 10 days or less
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 9, 31);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 8, 33);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 7, 35);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 6, 37);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 5, 39);
        // and by 3 when there are 5 days or less
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 4, 42);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 3, 45);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 2, 48);
        // The Quality of an item is never more than 50
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 1, 50);
        updateQualityAndAssertState(app.items[0], BACKSTAGE, 0, 50);
        // but Quality drops to 0 after the concert
        updateQualityAndAssertState(app.items[0], BACKSTAGE, -1, 0);
    }

    @Test
    void exploreSulfurasItem() {
        app = new GildedRose(of(item(SULFURAS, 11, 20)));
        assertState(app.items[0], SULFURAS, 11, 20);

        // “Sulfuras”, being a legendary item, never has to be sold or decreases in Quality
        updateQualityAndAssertState(app.items[0], SULFURAS, 11, 20);
        updateQualityAndAssertState(app.items[0], SULFURAS, 11, 20);
        updateQualityAndAssertState(app.items[0], SULFURAS, 11, 20);
    }
}
