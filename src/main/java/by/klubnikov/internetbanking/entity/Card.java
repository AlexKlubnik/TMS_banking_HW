package by.klubnikov.internetbanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Random;

@Entity
@Getter
@EqualsAndHashCode
@ToString
@Table(name = "banking_cards")
public class Card {
    public Card() {
        this.cvv = new Random().nextInt(999);
        Long cardNumber = new Random().nextLong(10000000000000000L);
        this.cardNumber = cardNumber.toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Customer customer;

    @Pattern(regexp = "\\d{16}")
    private String cardNumber;

    private int cvv;

    private double balance;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
