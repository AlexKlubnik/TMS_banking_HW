package by.klubnikov.internetbanking.factory;

import by.klubnikov.internetbanking.entity.Card;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class StandardCardFactory implements CardFactory {
    @Override
    public Card createCard() {
        int cvv = new Random().nextInt(999);
        Long cardNumber = new Random().nextLong(10000000000000000L);
        Card card = new Card();
        card.setCardNumber(cardNumber.toString());
        card.setCvv(cvv);
        return card;
    }
}
