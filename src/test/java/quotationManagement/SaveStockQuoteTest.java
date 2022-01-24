
package quotationManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import quotationManagement.domain.dto.StockRequest;
import quotationManagement.domain.entity.Stock;
import quotationManagement.domain.useCases.SaveStockQuoteUseCase;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestConfiguration
public class SaveStockQuoteTest {

    @Autowired
    private SaveStockQuoteUseCase saveStockQuoteUseCase;


    //fail region
    @Test(expected = Exception.class)
    public void registerStockNotQuoteError()
    {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setStockId("MGLu");
        Stock stock = saveStockQuoteUseCase.handle(stockRequest);

    }

    @Test()
    public void registerQuotestock() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setStockId("petr4");
        Map<LocalDate, Double> quotes = new HashMap<>();
        quotes.put(LocalDate.now(), 3.41D);
        stockRequest.setQuotes(quotes);
        Stock stock = saveStockQuoteUseCase.handle(stockRequest);
        Assert.notNull(stock.getId());
    }

   //region sucess

}