package com.ulises.posverse.common.enums;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SortDirection {
    ASC(Sort.Order::asc),
    DESC(Sort.Order::desc);

    private final Function<String, Sort.Order> orderCreator;

    SortDirection(final Function<String, Sort.Order> orderCreator) {
        this.orderCreator = orderCreator;
    }

    public Sort apply(List<String> sortByFields) {
        if (sortByFields == null || sortByFields.isEmpty()) {
            return Sort.unsorted();
        }

        final List<Sort.Order> orders = sortByFields.stream()
                .map(orderCreator)
                .collect(Collectors.toList());

        return Sort.by(orders);
    }

    public Sort apply(String sortBy) {
        return apply(List.of(sortBy));
    }
}
