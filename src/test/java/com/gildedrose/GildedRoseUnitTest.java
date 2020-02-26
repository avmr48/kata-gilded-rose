package com.gildedrose;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.gildedrose.Functions.assertStateTransition;

@Disabled("use nested test instead")
class GildedRoseUnitTest {

    /**
     * our system lowers both values for every item
     */
    @Test
    void system_lowers_both_values_for_every_item() {
        assertStateTransition(Constant.EXAMPLE, Pair.of(20, 20), Pair.of(19, 19));
    }

    /**
     * The Quality of an item is never negative
     */
    @Test
    void the_quality_of_an_item_is_never_negative() {
        assertStateTransition(Constant.EXAMPLE, Pair.of(0, 0), Pair.of(-1, 0));
    }

    /**
     * Once the sell by date has passed, Quality degrades twice as fast
     */
    @Test
    void once_the_sell_by_date_has_passed_quality_degrades_twice_as_fast() {
        assertStateTransition(Constant.EXAMPLE, Pair.of(0, 9), Pair.of(-1, 7));
    }

    /**
     * “Aged Brie” actually increases in Quality the older it gets
     */
    @Test
    void aged_brie_actually_increases_in_quality_the_older_it_gets() {
        assertStateTransition(Constant.AGED_BRIE, Pair.of(5, 15), Pair.of(4, 16));
    }

    /**
     * /!\ Surprising requirement : quality increases twice as fast once sell by date has passed
     */
    @Test
    void aged_brie_once_the_sell_by_date_has_passed_quality_increases_twice_as_fast() {
        assertStateTransition(Constant.AGED_BRIE, Pair.of(0, 20), Pair.of(-1, 22));
    }

    /**
     * The Quality of an item is never more than 50
     */
    @Test
    void the_quality_of_an_item_is_never_more_than_50() {
        assertStateTransition(Constant.AGED_BRIE, Pair.of(10, 49), Pair.of(9, 50));
        assertStateTransition(Constant.AGED_BRIE, Pair.of(10, 50), Pair.of(9, 50));
    }

    @Test
    void the_quality_of_an_item_is_never_more_than_50_even_below_sellin_0() {
        // mutation
        assertStateTransition(Constant.AGED_BRIE, Pair.of(-1, 49), Pair.of(-2, 50));
        assertStateTransition(Constant.AGED_BRIE, Pair.of(-1, 50), Pair.of(-2, 50));
    }

    /**
     * “Backstage passes”, like aged brie, increases in Quality as it’s SellIn value approaches;
     */
    @Test
    void backstage_passes_like_aged_brie_increases_in_quality_as_its_sellin_value_approaches() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(11, 11), Pair.of(10, 12));
    }

    /**
     * Quality increases by 2 when there are 10 days or less
     */
    @Test
    void backstage_passes_increases_by_2_when_less_or_equal_than_10() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(10, 12), Pair.of(9, 14));
    }

    @Test
    void backstage_passes_increases_by_2_when_less_or_equal_than_10_until_5() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(6, 20), Pair.of(5, 22));
    }

    /**
     * and by 3 when there are 5 days or less
     */
    @Test
    void backstage_passes_increases_by_3_when_less_or_equal_than_5() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(5, 22), Pair.of(4, 25));
    }

    @Test
    void backstage_passes_increases_by_3_when_less_or_equal_than_5_until_1() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(1, 34), Pair.of(0, 37));
    }

    /**
     * but Quality drops to 0 after the concert
     */
    @Test
    void backstage_passes_drop_to_0() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(0, 10), Pair.of(-1, 0));
    }

    @Test
    void the_quality_of_an_item_is_never_more_than_50_also_for_backstage() {
        assertStateTransition(Constant.BACKSTAGE, Pair.of(10, 50), Pair.of(9, 50));

        // mutation 1
        assertStateTransition(Constant.BACKSTAGE, Pair.of(6, 49), Pair.of(5, 50));
        assertStateTransition(Constant.BACKSTAGE, Pair.of(6, 50), Pair.of(5, 50));

        // mutation 2
        assertStateTransition(Constant.BACKSTAGE, Pair.of(1, 49), Pair.of(0, 50));
        assertStateTransition(Constant.BACKSTAGE, Pair.of(1, 50), Pair.of(0, 50));
    }

    /**
     * “Sulfuras”, being a legendary item, never has to be sold or decreases in Quality
     */
    @Test
    void sulfuras_being_a_legendary_item_never_has_to_be_sold_or_decreases_in_quality() {
        assertStateTransition(Constant.SULFURAS, Pair.of(5, 40), Pair.of(5, 40));
    }

}
