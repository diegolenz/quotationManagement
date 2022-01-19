package quotationManagement.domain.useCases;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import quotationManagement.domain.dto.StockResponse;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidityStockUseCase {
    final String URL_WEBSERVICE_STOCK = "http://localhost:8080/stock";

    public Boolean handle(String stockId) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<StockResponse[]> response = null;
        try {
            response
                    = restTemplate.getForEntity(this.URL_WEBSERVICE_STOCK, StockResponse[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.isTrue(response != null && !response.getStatusCode().isError(), "Não foi possivel verrificar as cotações ja cadastradas");
        Assert.notNull(response.getBody() , "Nenhuma cotação foi cadastrada");
        Assert.isTrue(response.getBody().length > 0, "Nenhuma cotação foi cadastrada");

        List<StockResponse> stocks = Arrays.asList(response.getBody().clone());

        Boolean exists = stocks.stream().anyMatch(stockResponse -> stockResponse.getId().equalsIgnoreCase(stockId));

        return exists;

    }
}
