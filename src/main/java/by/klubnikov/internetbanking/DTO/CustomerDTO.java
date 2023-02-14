package by.klubnikov.internetbanking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private Long customerId;

    private String name;

    private String personalNumber;

    private List<CardDTO> cards;
}
