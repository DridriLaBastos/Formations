package fr.adriencournand.formation.ecom.service.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom.service.product.dto.ProductRequest;
import fr.adriencournand.formation.ecom.service.product.dto.ProductResponse;
import fr.adriencournand.formation.ecom.service.product.model.Product;
import fr.adriencournand.formation.ecom.service.product.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    public final IProductRepository productRepository;

    public ProductResponse CreateProduct(ProductRequest productRequest) {
        Product p = MapProductRequestToProduct(productRequest);
        p = productRepository.save(p);
        ProductResponse response = MapProductToProductResponse(p);
        return response;
    }

    public Optional<ProductResponse> UpdateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct -> {
            UpdateProductFromProducRequest(existingProduct, productRequest);
            Product updatedProduct = productRepository.save(existingProduct);
            return MapProductToProductResponse(updatedProduct);
        });
    }

    public List<ProductResponse> GetAllProducts() {
        return productRepository.findByActiveTrue().stream().map(ProductService::MapProductToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean DeleteProduct(Long id) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setActive(false);
            productRepository.save(existingProduct);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> SearchProducts(String keyword) {
        return productRepository.findByKeyword(keyword).stream().map(ProductService::MapProductToProductResponse)
                .collect(Collectors.toList());
    }

    private static void UpdateProductFromProducRequest(Product product, ProductRequest request) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
    }

    private static Product MapProductRequestToProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        return product;
    }

    private static ProductResponse MapProductToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.isActive());

        return response;
    }
}
