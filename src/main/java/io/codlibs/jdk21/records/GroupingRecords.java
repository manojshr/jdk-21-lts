package io.codlibs.jdk21.records;

public class GroupingRecords {
    public sealed interface DineIn permits Restaurant, Bar{
        int calculateTotal();
    }

    public static record Order(int orderId, int totalPrice, int tax) { }

    public static record Food(String name, int quantity) { }

    public static record Drink(String name, String type, int units) { }

    public record Restaurant(Order order, Food food) implements DineIn {
        @Override
        public int calculateTotal() {
            return order.totalPrice() + order.tax();
        }
    }

    public record Bar(Order order, Drink drink) implements DineIn {
        @Override
        public int calculateTotal() {
            return order.totalPrice() + (order.tax() * drink.units());
        }
    }
}
