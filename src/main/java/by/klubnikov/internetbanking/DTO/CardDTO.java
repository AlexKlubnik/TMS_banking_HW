package by.klubnikov.internetbanking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDTO {

    private String cardNumber;

    private double balance;
}
