package by.klubnikov.internetbanking.dto;

import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    CustomerDtoMapper INSTANCE = Mappers.getMapper(CustomerDtoMapper.class);

    CustomerDto toCustomerDTO(Customer customer);


    default CardDto toCardDTO(Card card) {
        String hidedNum = card.getCardNumber().replaceFirst("[0-9]{12}", "************");
        return new CardDto(hidedNum, card.getBalance());
    }
}
