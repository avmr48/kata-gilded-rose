package com.gildedrose;

import com.gildedrose.Functions.AnItem;
import com.gildedrose.Functions.ItemList;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gildedrose.Functions.assertStateTransition;

@DisplayName("Gilded Rose update system")
public class GildedRoseParameterizedNestedUnitTest {

    /**
     * Convert a test parametr in Pair as input of a test
     */
    public static class ItemConverter implements ArgumentConverter {

        @Override
        public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
            Pattern pattern = Pattern.compile("\\((.*),(.*)\\)");
            Matcher matcher = pattern.matcher((String) source);
            if (matcher.matches()) {
                return Pair.of(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))
                );
            }
            throw new RuntimeException("Cannot convert:" + source);
        }
    }

    /**
     * Alias to convert with item converter
     */
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @ConvertWith(ItemConverter.class)
    public @interface ItemValue {
    }

    @Test
    void update_all_items() {
        Item[] items = ItemList.of(
                AnItem.of("A", 2, 2),
                AnItem.of("B", 1, 1)
        );

        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();

        AnItem.assertState(gildedRose.items[0], "A", 1, 1);
        AnItem.assertState(gildedRose.items[1], "B", 0, 0);
    }

    @DisplayName("For common item")
    @Nested
    class ForCommonItem {

        @DisplayName("our system lowers both values for every item")
        @CsvSource(value = {
                "(20,20) = (19,19)",
                "(10,15) = (9,14)",
                "(5,4) = (4,3)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void system_lowers_both_values_for_every_item2(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.EXAMPLE, input, expected);
        }

        @DisplayName("The Quality of an item is never negative")
        @CsvSource(value = {
                "(1,0) = (0,0)",
                "(0,0) = (-1,0)",
                "(-1,0) = (-2,0)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void the_quality_of_an_item_is_never_negative(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.EXAMPLE, input, expected);
        }

        @DisplayName("The Quality of an item stay negative if it was")
        @CsvSource(value = {
                "(1,-1) = (0,-1)",
                "(0,-1) = (-1,-1)",
                "(-1,-1) = (-2,-1)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void the_quality_of_an_item_stay_negative_if_it_was(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.EXAMPLE, input, expected);
        }

        @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
        @CsvSource(value = {
                "(0,9) = (-1,7)",
                "(-1,7) = (-2,5)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void once_the_sell_by_date_has_passed_quality_degrades_twice_as_fast(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.EXAMPLE, input, expected);
        }
    }

    @DisplayName("For Aged Brie")
    @Nested
    class ForAgedBrieItem {

        @DisplayName("“Aged Brie” actually increases in Quality the older it gets")
        @CsvSource(value = {
                "(5,15) = (4,16)",
                "(1,5) = (0,6)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void aged_brie_actually_increases_in_quality_the_older_it_gets(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.AGED_BRIE, input, expected);
        }

        @DisplayName("quality increases twice as fast once sell by date has passed")
        @CsvSource(value = {
                "(0,20) = (-1,22)",
                "(-1,22) = (-2,24)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void aged_brie_once_the_sell_by_date_has_passed_quality_increases_twice_as_fast(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.AGED_BRIE, input, expected);
        }

        @DisplayName("The Quality of an item is never more than 50")
        @CsvSource(value = {
                "(1,50) = (0,50)",
                "(0,50) = (-1,50)",
                "(-1,50) = (-2,50)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void the_quality_of_an_item_is_never_more_than_50(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.AGED_BRIE, input, expected);
        }

        // NOTE kills mutation 3
        @DisplayName("The Quality of an item is never more than 50 when sell in below 0")
        @CsvSource(value = {
                "(1,49) = (0,50)",
                "(0,49) = (-1,50)",
                "(-1,49) = (-2,50)",
                "(1,50) = (0,50)",
                "(0,50) = (-1,50)",
                "(-1,50) = (-2,50)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void the_quality_of_an_item_is_never_more_than_50_even_below_sellin_0(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.AGED_BRIE, input, expected);
        }
    }

    @DisplayName("For Backstage item")
    @Nested
    class ForBackstageItem {

        @DisplayName("“Backstage passes”, like aged brie, increases in Quality as it’s SellIn value approaches;")
        @CsvSource(value = {
                "(11,11) = (10,12)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void backstage_passes_like_aged_brie_increases_in_quality_as_its_sellin_value_approaches(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.BACKSTAGE, input, expected);
        }

        @DisplayName("Quality increases by 2 when there are 10 days or less")
        @CsvSource(value = {
                "(10,12) = (9,14)",
                "(9,14) = (8,16)",
                "(8,16) = (7,18)",
                "(7,18) = (6,20)",
                "(6,20) = (5,22)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void backstage_passes_increases_by_2_when_less_or_equal_than_10_until_5(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.BACKSTAGE, input, expected);
        }

        @DisplayName("and by 3 when there are 5 days or less")
        @CsvSource(value = {
                "(5,22) = (4,25)",
                "(4,25) = (3,28)",
                "(3,28) = (2,31)",
                "(2,31) = (1,34)",
                "(1,34) = (0,37)",
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void backstage_passes_increases_by_3_when_less_or_equal_than_5_until_1(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.BACKSTAGE, input, expected);
        }
        
        @DisplayName("but Quality drops to 0 after the concert")
        @CsvSource(value = {
                "(0,10) = (-1,0)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void backstage_passes_drop_to_0(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.BACKSTAGE, input, expected);
        }

        @DisplayName("The Quality of an item is never more than 50")
        @CsvSource(value = {
                "(10,50) = (9,50)",
                // NOTE mutation 1
                "(8,49) = (7,50)",
                "(7,50) = (6,50)",
                // NOTE mutation 2
                "(2,49) = (1,50)",
                "(1,50) = (0,50)"
        }, delimiter = '=')
        @ParameterizedTest(name = "{0} -> {1}")
        void the_quality_of_an_item_is_never_more_than_50_also_for_backstage(
                @ItemValue Pair<Integer, Integer> input, @ItemValue Pair<Integer, Integer> expected
        ) {
            assertStateTransition(Constant.BACKSTAGE, input, expected);
        }
    }
}
