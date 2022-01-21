package quotationManagement.domain.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import quotationManagement.domain.dto.StockRequest;
import quotationManagement.domain.entity.Quote;
import quotationManagement.domain.entity.Stock;
import quotationManagement.domain.repository.StockRepository;

import java.util.Optional;

@Service
public class SaveStockQuoteUseCase {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ValidityStockUseCase validityStockUseCase;

    @Transactional
    public Stock handle(StockRequest stockRequest) {
        Assert.notNull(stockRequest.getStockId(), "O id da descrição não foi informado");
        Assert.isTrue(!stockRequest.getQuotes().isEmpty(), "Pelo menos uma cotação deve ser informada");

        Boolean existsStock = validityStockUseCase.handle(stockRequest.getStockId());
        Assert.isTrue(existsStock, "A cotação ainda não foi cadastrada");

        Optional<Stock> dbStock = Optional.empty();
        if (stockRequest.getId() != null)
            dbStock = this.stockRepository.findByStockId(stockRequest.getStockId());

        Stock saveStock = dbStock.isPresent() ? dbStock.get() : new Stock();
        saveStock.setStockId(stockRequest.getStockId());
        stockRequest.getQuotes().entrySet().forEach(localDateDoubleEntry -> {
            Quote quote = new Quote();
            quote.setStock(saveStock);
            quote.setDateVigence(localDateDoubleEntry.getKey());
            quote.setValue(localDateDoubleEntry.getValue());
            saveStock.getQuotes().add(quote);
        });

        Stock stockResult = stockRepository.saveAndFlush(saveStock);
        return stockResult;
    }

}
