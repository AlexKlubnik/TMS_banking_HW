package by.klubnikov.internetbanking.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
public class BankingError {
    private String message;
    private int statusCode;
}
