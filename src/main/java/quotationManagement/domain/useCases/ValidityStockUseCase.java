package quotationManagement.domain.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quotationManagement.domain.dto.StockDto;

import java.util.List;

@Service
public class ValidityStockUseCase {
    final String URL_WEBSERVICE_STOCK = "http://localhost:8080/stock";

    @Autowired
    private CacheStockUseCase cacheStockUseCase;

    public Boolean handle(String stockId) {

        List<StockDto> stocks = cacheStockUseCase.getStock();

        Boolean exists = stocks.stream().anyMatch(stockResponse -> stockResponse.getId().equalsIgnoreCase(stockId));

        return exists;

    }
}
