package by.klubnikov.internetbanking.controller;

import by.klubnikov.internetbanking.dto.CustomerDto;
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


    @GetMapping
    public List<CustomerDto> findAll() {
        return customerService.findAll();
    }

    @GetMapping("{id}")
    public CustomerDto findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping("{customerId}/cards")
    public CustomerDto saveCard(@PathVariable Long customerId, double balance) {
        return customerService.saveCard(customerId, balance);
    }

    @PatchMapping("{customerId}/cards/{cardId}")
    public CustomerDto createPayment(@PathVariable Long customerId, @PathVariable Long cardId, @RequestParam double sum) {
        return customerService.createPayment(customerId, cardId, sum);
    }

    @PostMapping("{customerId}/cards/{customerCardId}")
    public CustomerDto sendFromCardToCard(@PathVariable Long customerId, @PathVariable Long customerCardId,
                                          Long anotherCardId, double sum) {
        return customerService.sendFromCardToCard(customerId, customerCardId, anotherCardId, sum);
    }

    @DeleteMapping("{customerId}/cards/{customerCardIndex}")
    public CustomerDto deleteCard(@PathVariable Long customerId, @PathVariable Long customerCardIndex) {
        return customerService.deleteCard(customerId, customerCardIndex);
    }

    @PostMapping
    public CustomerDto save(Customer customer) {
        return customerService.save(customer);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
