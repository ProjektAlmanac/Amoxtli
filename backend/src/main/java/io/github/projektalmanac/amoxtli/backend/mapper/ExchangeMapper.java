package io.github.projektalmanac.amoxtli.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeMapper {
    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);
}
