package by.klubnikov.internetbanking.DTO;

import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    CustomerDtoMapper INSTANCE = Mappers.getMapper(CustomerDtoMapper.class);

    CustomerDTO toCustomerDTO(Customer customer);


    default CardDTO toCardDTO(Card card) {
        String hidedNum = card.getCardNumber().replaceFirst("[0-9]{12}", "************");
        return new CardDTO(hidedNum, card.getBalance());
    }
}
