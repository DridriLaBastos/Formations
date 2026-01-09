package fr.adriencournand.formation.ecom.service.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.adriencournand.formation.ecom.service.product.dto.ProductRequest;
import fr.adriencournand.formation.ecom.service.product.dto.ProductResponse;
import fr.adriencournand.formation.ecom.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> CreateProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<ProductResponse>(productService.CreateProduct(productRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> GetProducts() {
        return ResponseEntity.ok(productService.GetAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> SearchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.SearchProducts(keyword));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> GetProductById(@PathVariable Long productId) {
        return productService.getProductById(Long.valueOf(productId)).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> UpdateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest) {
        return productService.UpdateProduct(id, productRequest).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteProduct(@PathVariable Long id) {
        boolean deleted = productService.DeleteProduct(id);
        if (!deleted)
            log.warn("Unable to delete request product {}", id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
