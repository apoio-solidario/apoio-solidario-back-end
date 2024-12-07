package com.github.apoioSolidario.repository.querybuilder.interfaces;

import org.springframework.data.domain.Example;

public interface QueryBuilder<T> {
    public Example<T>  makeQuery(T entity);
}
