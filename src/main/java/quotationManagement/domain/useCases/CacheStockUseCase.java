package quotationManagement.domain.useCases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import quotationManagement.domain.dto.NotificationCacheRequest;
import quotationManagement.domain.dto.StockResponse;

import javax.annotation.PostConstruct;

@Service
public class CacheStockUseCase {

    final String URL_WEBSERVICE_STOCK = "http://localhost:8080/notification";

    private StockResponse[] registeredStock;

    @Cacheable("stock")
    private StockResponse[]  getCacheStock() {
        return this.registeredStock;
    }

    private void registerCacheStock(StockResponse[] registeredStock) {
        this.registeredStock = registeredStock;
    }

    @CacheEvict
    public void deleteCacheStock() {
        this.registeredStock = null;
    }

    @PostConstruct
    protected void initialize() {
        RestTemplate restTemplate = new RestTemplate();
        NotificationCacheRequest notificationCacheRequest = new NotificationCacheRequest();
        notificationCacheRequest.setHost("localhost");
        notificationCacheRequest.setPort("8081");
        ResponseEntity response = null;
        try {
            response = restTemplate.postForEntity(this.URL_WEBSERVICE_STOCK, notificationCacheRequest, String.class);
            System.out.println(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
