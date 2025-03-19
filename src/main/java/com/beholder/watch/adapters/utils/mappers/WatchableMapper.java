package com.beholder.watch.adapters.utils.mappers;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.beholder.watch.model.watchable.Watchable;

@Mapper(componentModel = "spring")
public interface WatchableMapper {
  
  @Mappings({
    @Mapping(target = "id", source = "id"),
    @Mapping(target = "name", source = "name"),
    @Mapping(target = "url", source = "url"),
    @Mapping(target = "checkInterval", source = "checkInterval"),
    @Mapping(target = "status", source = "status"),
    @Mapping(target = "createdAt", source = "createdAt"),
    @Mapping(target = "updatedAt", source = "updatedAt")
  })
  Watchable mapToWatchable(JpaEntityWatchable jpaEntityWatchable);

  @Mappings({
    @Mapping(target = "id", source = "id"),
    @Mapping(target = "name", source = "name"),
    @Mapping(target = "url", source = "url"),
    @Mapping(target = "checkInterval", source = "checkInterval"),
    @Mapping(target = "status", source = "status"),
    @Mapping(target = "createdAt", source = "createdAt"),
    @Mapping(target = "updatedAt", source = "updatedAt")
  })
  JpaEntityWatchable mapToJpaEntityWatchable(Watchable watchable);

}
