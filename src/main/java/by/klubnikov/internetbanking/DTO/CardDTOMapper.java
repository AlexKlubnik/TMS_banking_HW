package by.klubnikov.internetbanking.DTO;

import by.klubnikov.internetbanking.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardDTOMapper {

    public CardDTO toCardDTO(Card card);
}
