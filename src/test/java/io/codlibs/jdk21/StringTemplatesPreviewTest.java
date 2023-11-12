package io.codlibs.jdk21;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.codlibs.jdk21.StringTemplatesPreview.Post;
import static java.util.FormatProcessor.FMT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTemplatesPreviewTest {
    private final StringTemplatesPreview templatifier = new StringTemplatesPreview();

    record Product(String productName, double taxPercentage, double price) {
        double priceAfterTax() {
            return price + (price * 0.01 * taxPercentage);
        }
    }

    @Test
    void testTemplatifiedMethodArgs() {
        String title = "Lorem";
        String description = "Ipsum";
        assertEquals("Title: Lorem; Description: Ipsum", templatifier.methodArgs(title, description));
    }

    @Test
    void testTemplatifiedRecordTypeMethodArgs() {
        Post post = new Post("Lorem", "Ipsum");
        assertEquals("Title: Lorem; Description: Ipsum", templatifier.methodArgsWithRecordType(post));
    }

    @Test
    void testTemplatifiedLambdaSupplier() {
        Supplier<String> supplier = () -> Stream.of("Hello", "Hey", "Hi").map(String::toUpperCase)
                .collect(Collectors.joining(" / "));
        assertEquals("Greetings: HELLO / HEY / HI", templatifier.methodArgsWithLambda(supplier));
    }

    @Test
    void testTemplatifiedExpression() {
        String reservedKeyWords = STR."Keywords: \{
                Stream.of("continue", "break", "goto")
                        .collect(Collectors.joining("; ", "<", ">"))
                }";
        assertEquals("Keywords: <continue; break; goto>", reservedKeyWords);
    }

    @Test
    void testTemplatifiedStringTemplate() {
        Post post = new Post("Lorem", "Ipsum");
        String titleTag = STR."<p>\{post.title()}</p>";
        String descriptionTag = STR."<p>\{post.description()}</p>";
        String lineBreak = "<br/>";
        String templatifiedHtml = STR."""
                <html>
                    <body>
                        \{titleTag}
                        \{descriptionTag}
                        \{lineBreak}
                    </body>
                <html>""";
        assertEquals("""
                        <html>
                            <body>
                                <p>Lorem</p>
                                <p>Ipsum</p>
                                <br/>
                            </body>
                        <html>""", templatifiedHtml);
    }

    @Test
    void processRawStringTemplate() {
        String title = "Lorem";
        String description = "Ipsum";
        StringTemplate stringTemplate = templatifier.rawStringTemplate(title, description);
        assertEquals("Title: Lorem; Description: Ipsum", stringTemplate.process(STR));
        assertEquals("Title: Lorem; Description: Ipsum", STR.process(stringTemplate));
    }

    @Test
    void formatting() {
        Product[] products = new Product[] {
                new Product("Eggs", 12.8, 101.98),
                new Product("Milk", 6.8, 72.34),
                new Product("Oreo", 9.1, 140.40),
        };
        String productsTable = FMT."""
                Product   Tax     Price    PriceAfterTax
                \{Arrays.stream(products)
                .map(product -> FMT."%-6s\{product.productName}  %7.2f\{product.taxPercentage}  %7.2f\{product.price}  %7.2f\{product.priceAfterTax()}")
                .collect(Collectors.joining(System.lineSeparator()))}
                \{" ".repeat(12)} Total Price: %7.2f\{Arrays.stream(products).mapToDouble(Product::priceAfterTax).sum()}
                """;
        assertEquals("""
                Product   Tax     Price    PriceAfterTax
                Eggs      12.80   101.98   115.03
                Milk       6.80    72.34    77.26
                Oreo       9.10   140.40   153.18
                             Total Price:  345.47
                """, productsTable);
    }
}