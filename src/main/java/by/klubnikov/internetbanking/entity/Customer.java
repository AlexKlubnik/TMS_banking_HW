package by.klubnikov.internetbanking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "banking_customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_name")
    @NotEmpty
    private String name;

    @Column(name = "customer_password")
    @NotEmpty
    @Length(min = 8)
    private String password;

    /**
     * matches personal number from belarusian пашпарт
     */
    @NotEmpty
    @Length(min = 14, max = 14)
    @Pattern(regexp = "[a-zA-Z0-9]{14}")
    private String personalNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card){
        cards.add(card);
        card.setCustomer(this);
    }
}

