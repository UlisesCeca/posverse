package com.ulises.posverse.common.enums;

import org.springframework.data.domain.Sort;

public enum SortDirection {
    ASC, DESC;

    public Sort apply(final String sortBy) {
        return this == ASC
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }
}
