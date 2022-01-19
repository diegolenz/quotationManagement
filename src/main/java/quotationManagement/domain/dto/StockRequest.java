package quotationManagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    private String id;
    private String stockId;
    private Map<LocalDate, Double> quotes;

}
