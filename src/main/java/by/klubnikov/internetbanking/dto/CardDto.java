package by.klubnikov.internetbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDto {

    private String cardNumber;

    private double balance;
}
