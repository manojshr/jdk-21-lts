package io.codlibs.jdk21;


import java.util.function.Supplier;

import static java.lang.StringTemplate.RAW;

public class StringTemplatesPreview {

    public record Post(String title, String description){}
    public String methodArgs(String title, String description) {
        return STR."Title: \{title}; Description: \{description}";
    }

    public String methodArgsWithRecordType(Post post) {
        return STR."Title: \{post.title()}; Description: \{post.description()}";
    }

    public String methodArgsWithLambda(Supplier<String> supplier) {
        return STR."Greetings: \{supplier.get()}";
    }

    public StringTemplate rawStringTemplate(String title, String description) {
        return RAW."Title: \{title}; Description: \{description}";
    }
}
