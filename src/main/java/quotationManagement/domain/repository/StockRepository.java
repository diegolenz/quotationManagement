package quotationManagement.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quotationManagement.domain.entity.Stock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByStockId(String stockId);

    @EntityGraph(attributePaths = {"quotes"})
    List<Stock> findAll();

}


