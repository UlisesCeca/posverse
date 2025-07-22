package com.ulises.posverse.common.redis.keygenerators;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("pagedProductsKeyGenerator")
public class PagedProductsKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(final Object target, final Method method, final Object... params) {
        return Arrays.stream(params)
                .filter(Objects::nonNull)
                .map(this::flattenObject)
                .flatMap(Collection::stream)
                .collect(Collectors.joining("-"));
    }

    private List<String> flattenObject(final Object obj) {
        if (obj instanceof String || obj instanceof Number || obj instanceof Enum) {
            return List.of(obj.toString());
        }

        final List<String> values = new ArrayList<>();
        for (final Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                values.add(field.getName() + "=" + (value != null ? value.toString() : "null"));
            } catch (IllegalAccessException ignored) {
            }
        }
        return values;
    }
}
