package com.spring.boot.social.utils;

import com.spring.boot.social.exceptions.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationHelper {
    private PaginationHelper() {
    }

    public static Pageable getPageable(int page, int size) {
        if (page < 1) {
            throw new BadRequestException("error.min.one.page");
        }
        return PageRequest.of(page - 1, size);
    }
}
