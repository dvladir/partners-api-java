package com.dvladir.common.domain;

import org.springframework.lang.Nullable;

import java.util.Objects;

public class SortField<T> {

    private T field;
    private SortType sortType;

    public SortField() {
    }

    public SortField(T field, SortType sortType) {
        this.field = field;
        this.sortType = sortType;
    }

    public T getField() {
        return field;
    }

    public void setField(T field) {
        this.field = field;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return field + ";" + sortType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortField sortField = (SortField) o;
        return Objects.equals(field, sortField.field) && sortType == sortField.sortType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, sortType);
    }
}
