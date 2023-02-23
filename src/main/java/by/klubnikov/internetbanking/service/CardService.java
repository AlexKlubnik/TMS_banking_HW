package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.factory.StandardCardFactory;
import by.klubnikov.internetbanking.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    private final StandardCardFactory cardFactory;

    public Card findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void addToCard (Long id, double sum){
        repository.addToCard(id, sum);
    }

    public Card createCard(double balance){
       Card card = cardFactory.createCard();
       card.setBalance(balance);
       return card;
    }

}




