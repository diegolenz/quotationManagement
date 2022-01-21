package quotationManagement.domain.useCases;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import quotationManagement.domain.dto.StockDto;

@Service
public class SaveStockUseCase {

    final String URL_WEBSERVICE_STOCK = "http://localhost:8080/stock";

    public void handle(StockDto stockRequest) {
        ResponseEntity response = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            response = restTemplate.postForEntity(this.URL_WEBSERVICE_STOCK, stockRequest, String.class);
            System.out.println(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
