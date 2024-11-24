package com.github.apoioSolidario.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {

    public<T> HttpHeaders getHeaders(Page<T> page) {
        HttpHeaders headers = new HttpHeaders() {{
            // We add +1 because of 'one-indexed-parameters' is set to true
            add("X-Pagination-Page-Index", String.valueOf(page.getNumber() + 1));
            add("X-Pagination-Page-Size", String.valueOf(page.getNumberOfElements()));
            add("X-Pagination-Page-Total", String.valueOf(page.getTotalPages()));
            add("X-Pagination-Item-Total", String.valueOf(page.getTotalElements()));
        }};

        headers.add("Content-Language", "pt-br");

        return headers;
    }
}
