package io.codlibs.jdk21.records;

import io.codlibs.jdk21.records.GroupingRecords.Bar;
import io.codlibs.jdk21.records.GroupingRecords.DineIn;
import io.codlibs.jdk21.records.GroupingRecords.Food;
import io.codlibs.jdk21.records.GroupingRecords.Order;
import io.codlibs.jdk21.records.GroupingRecords.Restaurant;

import static io.codlibs.jdk21.records.GroupingRecords.*;

public class RecordPatternMatching {
    public String destructuringRecordsWithInstanceOf(DineIn dineIn) {
        if (dineIn instanceof Restaurant(Order order, Food food)) {
            return STR."Food: \{food.name()}, Quantity: \{food.quantity()}, Total: \{dineIn.calculateTotal()}";
        } else if (dineIn instanceof Bar(Order order, Drink drink)) {
            return STR."Drink: \{drink.name()}, Units: \{drink.units()}, Total: \{dineIn.calculateTotal()}";
        }
        throw new RuntimeException("Don't know");
    }

    public String destructuringRecordsWithSwitchCase(DineIn dineIn) {
        return switch (dineIn) {
            case Restaurant(Order order, Food food)
                    -> STR."OrderId: \{order.orderId()}, Food: \{food.name()}, Quantity: \{food.quantity()}, Total: \{dineIn.calculateTotal()}";
            case Bar(Order order, Drink drink)
                    -> STR."OrderId: \{order.orderId()}, Drink: \{drink.name()}, Units: \{drink.units()}, Total: \{dineIn.calculateTotal()}";
        };
    }

    public String nestedDestructuring(DineIn dineIn) {
        // _ referring to unused variables
        return switch (dineIn) {
            case Restaurant(Order(int orderId, _, _), Food(String name, int quantity))
                    -> STR."OrderId: \{orderId}, Food: \{name}, Quantity: \{quantity}, Total: \{dineIn.calculateTotal()}";
            case Bar(Order(int orderId, int totalPrice, int tax), Drink(String name, _, int units))
                    -> STR."OrderId: \{orderId}, Drink: \{name}, Units: \{units}, Total: \{dineIn.calculateTotal()}";
        };
    }
}
