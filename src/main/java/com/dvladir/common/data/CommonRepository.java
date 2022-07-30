package com.dvladir.common.data;

import com.dvladir.common.domain.PageData;
import com.dvladir.common.domain.SortField;
import com.dvladir.common.exception.BaseException;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface CommonRepository<Entity, SearchPredicate, SortFields> {
  UUID add(Entity value) throws BaseException;
  boolean update(Entity value) throws BaseException;
  boolean remove(UUID id) throws BaseException;
  @Nullable Entity find(SearchPredicate predicate) throws BaseException;
  PageData<Entity> findAll(int pageNum, int pageSize, @Nullable SearchPredicate searchPredicate, @Nullable SortField<SortFields> sort) throws BaseException;
  PageData<Entity> findAll(int pageNum, int pageSize, @Nullable SearchPredicate searchPredicate) throws BaseException;
  PageData<Entity> findAll(int pageNum, int pageSize) throws BaseException;
}
