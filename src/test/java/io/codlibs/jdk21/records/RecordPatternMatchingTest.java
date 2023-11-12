package io.codlibs.jdk21.records;

import io.codlibs.jdk21.records.GroupingRecords.Food;
import io.codlibs.jdk21.records.GroupingRecords.Order;
import io.codlibs.jdk21.records.GroupingRecords.Restaurant;
import org.junit.jupiter.api.Test;

import static io.codlibs.jdk21.records.GroupingRecords.Bar;
import static io.codlibs.jdk21.records.GroupingRecords.DineIn;
import static io.codlibs.jdk21.records.GroupingRecords.Drink;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordPatternMatchingTest {
    private final RecordPatternMatching matcher = new RecordPatternMatching();

    @Test
    void destructuringRecordsWithInstanceOf() {
        Food food = new Food("Pasta", 1);
        Order foodOrder = new Order(1, 200, 20);
        DineIn restaurantDineIn = new Restaurant(foodOrder, food);
        assertEquals("Food: Pasta, Quantity: 1, Total: 220",
                matcher.destructuringRecordsWithInstanceOf(restaurantDineIn));
    }

    @Test
    void destructuringRecordsWithSwitch() {
        Drink drink = new Drink("Budweiser", "Bear", 3);
        Order drinkOrder = new Order(2, 500, 12);
        DineIn restaurantDineIn = new Bar(drinkOrder, drink);
        assertEquals("OrderId: 2, Drink: Budweiser, Units: 3, Total: 536",
                matcher.destructuringRecordsWithSwitchCase(restaurantDineIn));
    }


    @Test
    void destructuringNestedRecordsWithSwitch() {
        Drink drink = new Drink("Chardonnay", "Wine", 4);
        Order drinkOrder = new Order(3, 500, 12);
        DineIn restaurantDineIn = new Bar(drinkOrder, drink);
        assertEquals("OrderId: 3, Drink: Chardonnay, Units: 4, Total: 548",
                matcher.nestedDestructuring(restaurantDineIn));
    }
}