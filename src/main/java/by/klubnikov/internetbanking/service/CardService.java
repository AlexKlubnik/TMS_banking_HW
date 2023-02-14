package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    public Card findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void addToCard (Long id, double sum){
        repository.addToCard(id, sum);
    }

}




