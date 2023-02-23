package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.dto.CustomerDto;
import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import by.klubnikov.internetbanking.error.SomethingWentWrongException;
import by.klubnikov.internetbanking.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CardService cardService;

    @Spy
    private CustomerService customerService;

    private Customer customer1;

    private Customer customer2;

    private Card card1;
    private Card card2;

    @BeforeEach
    public void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        cardService = Mockito.mock(CardService.class);
        customerService = Mockito.spy(new CustomerService(customerRepository, cardService));

        card1 = new Card();
        card1.setCardNumber(Long.toString(2640097792346594L));
        card1.setCvv(456);
        card1.setId(1L);

        card2 = new Card();
        card2.setCardNumber(Long.toString(3291769726521500L));
        card2.setCvv(785);
        card2.setId(2L);

        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Alex");
        customer1.setPassword("122445wwdw");
        customer1.setPersonalNumber("2450568D897CL4");
        customer2 = new Customer();
    }


    @Test
    void findAll() {
        Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        List<CustomerDto> expectedList = customerService.findAll();
        Mockito.verify(customerRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(expectedList);
        Assertions.assertEquals(2, expectedList.size());
    }

    @Test
    void findById() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        CustomerDto expected = customerService.findById(1L);
        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Assertions.assertNotNull(expected);
        Assertions.assertThrows(SomethingWentWrongException.class, () -> customerService.findById(500L));
    }

    @Test
    void save() {
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(new Customer());
        CustomerDto expected = customerService.save(new Customer());
        Mockito.verify(customerRepository, Mockito.times(1)).save(new Customer());
        assertNotNull(expected);

    }

    @Test
    void deleteById() {
        customerService.deleteById(1L);
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void saveCard() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        Mockito.when(cardService.createCard(Mockito.anyDouble())).thenReturn(card2);
        CustomerDto customerDto = customerService.saveCard(1L, 230.0);
        String card2LastFourNumbers = card2.getCardNumber().substring(12);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cardService, Mockito.times(1)).createCard(Mockito.anyDouble());


        assertThrows(SomethingWentWrongException.class, () -> customerService.saveCard(500L, 230.0));
        assertEquals(customerDto.getId(), customer1.getId());
        assertTrue(customerDto.getCards()
                .stream()
                .anyMatch(cardDto -> cardDto
                        .getCardNumber().endsWith(card2LastFourNumbers)));
    }

    @Test
    void deleteCard() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        customer1.addCard(card1);
        customer1.addCard(card2);
        String card1LastFourNumbers = card1.getCardNumber().substring(12);

        CustomerDto customerDto = customerService.deleteCard(1L, card1.getId());

        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any());

        assertThrows(SomethingWentWrongException.class, () -> customerService.deleteCard(1L, 20L));
        assertEquals(1, customerDto.getCards().size());
        assertFalse(customerDto.getCards()
                .stream()
                .anyMatch(cardDto -> cardDto.getCardNumber().endsWith(card1LastFourNumbers)));
    }

    @Test
    void createPayment() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        Mockito.when(cardService.findById(1L)).thenReturn(card1);
        card1.setBalance(150);
        customer1.addCard(card1);

        CustomerDto customerDto = customerService.createPayment(1L, 1L, 50);

        Mockito.verify(cardService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(customerRepository, Mockito.times(1)).findById(Mockito.anyLong());
        assertEquals(100, card1.getBalance());
//        assertThrows(SomethingWentWrongException.class, () -> customerService.createPayment(1L, 5L, 50));
        assertThrows(SomethingWentWrongException.class, () -> customerService.createPayment(1L, 1L, 200));
    }

    @Test
    void sendFromCardToCard() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        Mockito.when(cardService.findById(1L)).thenReturn(card1);
        Mockito.when(cardService.findById(2L)).thenReturn(card2);
        customer1.addCard(card1);
        card1.setBalance(150);

        CustomerDto customerDto = customerService.sendFromCardToCard(1L, 1L, 2L, 50);

        Mockito.verify(cardService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(customerRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cardService, Mockito.times(1)).addToCard(2L, 50);
        assertEquals(100, card1.getBalance());
//        assertEquals(50, card2.getBalance());
        assertThrows(SomethingWentWrongException.class,
                ()-> customerService.sendFromCardToCard(1L, 1L, 2L, 200));
    }
}