package com.ulises.posverse.persistence.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DynamicSpecificationBuilder<T> {

    public Specification<T> buildFromFilter(final Object filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Field field : filter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(filter);
                    if (value != null) {
                        String fieldName = field.getName();

                        if (fieldName.startsWith("min")) {
                            predicates.add(cb.greaterThanOrEqualTo(
                                    root.get(fieldName.substring(3, 4).toLowerCase() + fieldName.substring(4)), (Comparable) value));
                        } else if (fieldName.startsWith("max")) {
                            predicates.add(cb.lessThanOrEqualTo(
                                    root.get(fieldName.substring(3, 4).toLowerCase() + fieldName.substring(4)), (Comparable) value));
                        } else if (value instanceof String str && !str.isBlank()) {
                            predicates.add(cb.like(cb.lower(root.get(fieldName)), "%" + str.toLowerCase() + "%"));
                        } else {
                            predicates.add(cb.equal(root.get(fieldName), value));
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}