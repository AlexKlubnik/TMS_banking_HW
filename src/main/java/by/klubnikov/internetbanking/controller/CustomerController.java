package by.klubnikov.internetbanking.controller;

import by.klubnikov.internetbanking.DTO.CustomerDTO;
import by.klubnikov.internetbanking.entity.Card;
import by.klubnikov.internetbanking.entity.Customer;
import by.klubnikov.internetbanking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
//    private final CardService cardService;

    @GetMapping
    public List<CustomerDTO> findAll() {
        return customerService.findAll();
    }

    @GetMapping("{id}")
    public CustomerDTO findById(@PathVariable Long id) {
//        log.info("Customer entity is {}", customerService.findById(id));
        return customerService.findById(id);
    }

    @PostMapping("{customerId}/cards")
    public CustomerDTO saveCard(@PathVariable Long customerId, Card card) {
//        log.info("customer card is {}", card);
        return customerService.saveCard(customerId, card);
    }

    @PatchMapping("{customerId}/cards/{cardId}")
    public CustomerDTO payFromCard(@PathVariable Long customerId, @PathVariable Long cardId, @RequestParam double sum) {
        return customerService.payFromCard(customerId, cardId, sum);
    }

    @PatchMapping("{customerId}/cards/{customerCardId}/{anotherCardId}")
    public CustomerDTO transferFromCardToCard(@PathVariable Long customerId, @PathVariable Long customerCardId,
                                              @PathVariable Long anotherCardId, @RequestParam double sum) {
        return customerService.transferFromCardToCard(customerId, customerCardId, anotherCardId, sum);
    }

    @DeleteMapping("{customerId}/cards/{customerCardIndex}")
    public CustomerDTO deleteCard(@PathVariable Long customerId, @PathVariable int customerCardIndex) {
        return customerService.deleteCard(customerId, customerCardIndex);
    }

    @PostMapping
    public CustomerDTO save(Customer customer) {
        return customerService.save(customer);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
