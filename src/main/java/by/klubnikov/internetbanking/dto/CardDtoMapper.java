package by.klubnikov.internetbanking.dto;

import by.klubnikov.internetbanking.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface CardDtoMapper {

    public CardDto toCardDTO(Card card);


}
