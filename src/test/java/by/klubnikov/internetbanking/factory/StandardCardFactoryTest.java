package by.klubnikov.internetbanking.factory;

import by.klubnikov.internetbanking.entity.Card;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;


class StandardCardFactoryTest {

    private StandardCardFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new StandardCardFactory();
    }

    @Test
    void createCard() {
        Card card = factory.createCard();
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(card.getCardNumber());
        Assertions.assertNotEquals(0, card.getCvv());
        MatcherAssert.assertThat(card.getCvv(), Matchers.lessThan(999));
        Assertions.assertTrue(Pattern.matches("[0-9]{16}", card.getCardNumber()));
    }
}