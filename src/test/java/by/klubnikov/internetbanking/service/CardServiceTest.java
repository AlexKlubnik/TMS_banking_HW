package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.factory.StandardCardFactory;
import by.klubnikov.internetbanking.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Optional;

class CardServiceTest {
    @Mock
    private CardRepository repository;
    @Mock
    private StandardCardFactory factory;

    @Spy
    private CardService service;
    private Card card;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(CardRepository.class);
        factory = Mockito.mock(StandardCardFactory.class);
        service = Mockito.spy(new CardService(repository, factory));
        card = new Card();
        card.setCardNumber(Long.toString(2640097792346594L));
        card.setCvv(456);
        card.setId(1L);
    }


    @Test
    public void findById() {

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(card));

        Assertions.assertNotNull(service.findById(1L));

        Assertions.assertEquals(card, service.findById(1L));

    }

    @Test
    void addToCard() {
        double sum = 100.0;
        service.addToCard(card.getId(), sum);
        Mockito.verify(repository, Mockito.times(1)).addToCard(card.getId(), sum);


    }

    @Test
    void createCard() {
        double balance = 219.0;
        Mockito.when(factory.createCard()).thenReturn(card);
        Card card1 = service.createCard(balance);
        Mockito.verify(factory, Mockito.times(1)).createCard();
        Assertions.assertNotNull(card1);
        Assertions.assertEquals(balance, card1.getBalance());
    }
}