package quotationManagement.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quotationManagement.domain.useCases.CacheStockUseCase;

@RestController
@RequestMapping(value = "/stockcache")
public class StockChacheController {

    @Autowired
    private CacheStockUseCase cacheStockUseCase;

    @DeleteMapping
    private void deleteCache() {
        cacheStockUseCase.deleteCacheStock();
    }
}
