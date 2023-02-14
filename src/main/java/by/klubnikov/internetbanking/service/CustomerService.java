package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.DTO.CustomerDTO;
import by.klubnikov.internetbanking.DTO.CustomerDtoMapper;
import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import by.klubnikov.internetbanking.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final CardService cardService;

    public List<CustomerDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(CustomerDtoMapper.INSTANCE::toCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO findById(Long id) {
        return repository.findById(id)
                .map(customer -> CustomerDtoMapper.INSTANCE.toCustomerDTO(customer))
                .orElseThrow(() -> new NoSuchElementException("No customer found by this ID"));
    }

    public CustomerDTO save(Customer customer) {
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CustomerDTO saveCard(Long customerId, Card card) {
        Customer customer = repository
                .findById(customerId).orElseThrow(() -> new NoSuchElementException("No customer found by this ID"));
        customer.addCard(card);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    public CustomerDTO deleteCard(Long customerId, int customerCardIndex) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        Card removableCard = customer.getCards().get(customerCardIndex);
        customer.getCards().remove(removableCard);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }


    public CustomerDTO payFromCard(Long customerId, Long cardId, double sum) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        subtractFromCustomerCard(customer, cardId, sum);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    private void subtractFromCustomerCard(Customer customer, Long cardId, double sum) {
        double balance = cardService.findById(cardId).getBalance();
        customer
                .getCards()
                .stream()
                .filter(card1 -> Objects.equals(card1.getId(), cardId))
                .findAny()
                .orElseThrow()
                .setBalance(balance - sum);
    }

    @Transactional
    public CustomerDTO transferFromCardToCard(Long customerId, Long customerCardId, Long cardId, double sum) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        subtractFromCustomerCard(customer, customerCardId, sum);
        cardService.addToCard(cardId, sum);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

}
