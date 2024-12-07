package com.github.apoioSolidario.repository.querybuilder;

import com.github.apoioSolidario.model.Campaign;
import com.github.apoioSolidario.model.Event;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.repository.querybuilder.interfaces.QueryBuilder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class EntityQueryBuilderImpl<T> implements QueryBuilder<T> {
    @Override
    public Example<T> makeQuery(T entity) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();

        if (entity instanceof Ong){
            exampleMatcher = exampleMatcher.withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);
        }

        if (entity instanceof Event || entity instanceof Campaign){
            exampleMatcher = exampleMatcher.withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains);
        }

        return Example.of(entity, exampleMatcher);    }
}
