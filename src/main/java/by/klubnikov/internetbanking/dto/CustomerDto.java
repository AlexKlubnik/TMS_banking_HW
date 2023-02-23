package by.klubnikov.internetbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private String personalNumber;

    private List<CardDto> cards;
}
