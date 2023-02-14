package by.klubnikov.internetbanking.repository;

import by.klubnikov.internetbanking.entity.Card;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
//    @Query("from Card c where c.customer.customerId = :customerId")
//    List<Card> findAllByCustomerId(@Param("customerId") Long id);
//
//    @Modifying
//    @Query("update Card c set c.balance=c.balance - :sum where c.id = :id")
//    void subtractFromCard(@Param("id") Long cardId, @Param("sum") double sum);

    @Modifying
    @Query("update Card c set c.balance = c.balance + :sum where c.id = :id")
    void addToCard(@Param("id") Long id, @Param("sum") double sum);
    @Query("select c.balance from Card c where c.id = :id")
    double getCardBalanceById(@Param("id") Long id);
}
