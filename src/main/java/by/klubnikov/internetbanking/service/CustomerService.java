package by.klubnikov.internetbanking.service;

import by.klubnikov.internetbanking.dto.CustomerDto;
import by.klubnikov.internetbanking.dto.CustomerDtoMapper;
import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import by.klubnikov.internetbanking.error.SomethingWentWrongException;
import by.klubnikov.internetbanking.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final CardService cardService;

    public List<CustomerDto> findAll() {
        return repository.findAll()
                .stream()
                .map(CustomerDtoMapper.INSTANCE::toCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDto findById(Long id) {
        return repository.findById(id)
                .map(customer -> CustomerDtoMapper.INSTANCE.toCustomerDTO(customer))
                .orElseThrow(() -> new SomethingWentWrongException("No customer found by ID" + id));
    }

    public CustomerDto save(Customer customer) {
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CustomerDto saveCard(Long customerId, double balance) {
        Customer customer = repository
                .findById(customerId).orElseThrow(() ->
                        new SomethingWentWrongException("No customer found by ID" + customerId));

        Card card = cardService.createCard(balance);
        customer.addCard(card);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    public CustomerDto deleteCard(Long customerId, Long cardId) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        Card removableCard = customer
                .getCards()
                .stream()
                .filter(card -> Objects.equals(card.getId(), cardId))
                .findFirst()
                .orElseThrow(() -> new SomethingWentWrongException("No customer's cards found by ID" + cardId));
        customer.getCards().remove(removableCard);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }


    public CustomerDto createPayment(Long customerId, Long cardId, double sum) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        subtractFromCustomerCard(customer, cardId, sum);
        repository.save(customer);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

    private void subtractFromCustomerCard(Customer customer, Long cardId, double sum) {
        double balance = cardService.findById(cardId).getBalance();
        if (balance >= sum) {
            customer
                    .getCards()
                    .stream()
                    .filter(card1 -> Objects.equals(card1.getId(), cardId))
                    .findAny()
                    .orElseThrow(() -> new SomethingWentWrongException("No customer's cards found by ID" + cardId))
                    .setBalance(balance - sum);
        } else throw new SomethingWentWrongException("You don't have enough money to make payment");
    }

    @Transactional
    public CustomerDto sendFromCardToCard(Long customerId, Long customerCardId, Long cardId, double sum) {
        Customer customer = repository
                .findById(customerId)
                .orElseThrow();
        subtractFromCustomerCard(customer, customerCardId, sum);
        cardService.addToCard(cardId, sum);
        return CustomerDtoMapper.INSTANCE.toCustomerDTO(customer);
    }

}
