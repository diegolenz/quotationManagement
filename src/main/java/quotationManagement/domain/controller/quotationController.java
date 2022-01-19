package quotationManagement.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import quotationManagement.domain.dto.StockRequest;
import quotationManagement.domain.entity.Stock;
import quotationManagement.domain.repository.StockRepository;
import quotationManagement.domain.useCases.SaveStockUseCase;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/stock")
public class quotationController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SaveStockUseCase saveStockUseCase;

    @GetMapping
    private ResponseEntity<List<Stock>> findAll() {
        List<Stock> stocks = this.stockRepository.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{StockId}")
    private ResponseEntity<Stock>  findByStockId(@PathVariable("StockId") String stockId) {
        Assert.notNull(stockId, "descrição não informada");
        Optional<Stock> hasStock = this.stockRepository.findByStockId(stockId);

        if (!hasStock.isPresent())
            ResponseEntity.notFound().build();

        return ResponseEntity.ok(hasStock.get());
    }

    @PostMapping
    private ResponseEntity<Stock> save(@RequestBody StockRequest stock) {
        Stock stockResult = saveStockUseCase.handle(stock);
        return ResponseEntity
                .ok(stockResult);
    }

}
