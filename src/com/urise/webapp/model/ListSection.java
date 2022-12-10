package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    public static final ListSection EMPTY = new ListSection("");
    private List<String> strings;

    public ListSection() {
    }
    public ListSection(String... items) {
        this(Arrays.asList(items));
    }
    public ListSection(List<String> strings) {
        Objects.requireNonNull(strings, "strings must not be null");
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return strings.equals(that.strings);
    }

    @Override
    public int hashCode() {
        return strings.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }
        return stringBuilder.toString();
//        return "ListSection{" +
//                "strings=" + strings +
//                '}';
    }

    @Override
    public String getContentAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }
        return stringBuilder.toString();
    }
}
