package quotationManagement.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "stock_idd", unique = true)
    private String stockId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock", targetEntity = Quote.class,
            cascade = {CascadeType.ALL})
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        if (quotes == null) quotes = new ArrayList<>();
        return quotes;
    }
}
