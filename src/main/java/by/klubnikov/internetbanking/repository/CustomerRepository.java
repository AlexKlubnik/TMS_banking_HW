package by.klubnikov.internetbanking.repository;

import by.klubnikov.internetbanking.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();

}
