package quotationManagement.domain.useCases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import quotationManagement.domain.dto.NotificationCacheRequest;
import quotationManagement.domain.dto.StockDto;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class CacheStockUseCase {

    final String URL_WEBSERVICE_NOTIFICATION = "http://localhost:8080/notification";
    final String URL_WEBSERVICE_STOCK = "http://localhost:8080/stock";

    private List<StockDto> registeredStock;

    @Cacheable("stock")
    private List<StockDto> getCacheStock() {
        return this.registeredStock;
    }

    @CacheEvict(value = "stock")
    public void deleteCacheStock() {
        this.registeredStock = null;
    }

    public void registerCacheStock(List<StockDto> registeredStock) {
        this.registeredStock = registeredStock;
    }

    public List<StockDto> getStock() {
        List<StockDto> stocksCache = this.getCacheStock();
        if (stocksCache != null)
            return stocksCache;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StockDto[]> response = null;
        try {
            response = restTemplate.getForEntity(this.URL_WEBSERVICE_STOCK, StockDto[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.isTrue(response != null && !response.getStatusCode().isError(), "Não foi possivel verrificar as cotações ja cadastradas");
        Assert.notNull(response.getBody() , "Nenhuma cotação foi cadastrada");
        Assert.isTrue(response.getBody().length > 0, "Nenhuma cotação foi cadastrada");

        this.registeredStock = Arrays.asList(response.getBody().clone());
        return registeredStock;
    }

    @PostConstruct
    protected void initialize() {
        RestTemplate restTemplate = new RestTemplate();
        NotificationCacheRequest notificationCacheRequest = new NotificationCacheRequest();
        notificationCacheRequest.setHost("localhost");
        notificationCacheRequest.setPort("8081");
        ResponseEntity response = null;
        try {
            response = restTemplate.postForEntity(this.URL_WEBSERVICE_NOTIFICATION, notificationCacheRequest, String.class);
            System.out.println(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
