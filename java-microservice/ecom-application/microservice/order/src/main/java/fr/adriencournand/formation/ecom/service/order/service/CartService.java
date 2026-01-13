package fr.adriencournand.formation.ecom.service.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom.service.order.client.IProductServiceClient;
import fr.adriencournand.formation.ecom.service.order.client.IUserServiceClient;
import fr.adriencournand.formation.ecom.service.order.dto.CartItemRequest;
import fr.adriencournand.formation.ecom.service.order.dto.ProductResponse;
import fr.adriencournand.formation.ecom.service.order.dto.UserResponse;
import fr.adriencournand.formation.ecom.service.order.model.CartItem;
import fr.adriencournand.formation.ecom.service.order.repository.ICartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // A bit obscure but apparently needed to perform a remove operation on a
               // database
public class CartService {

    private final ICartItemRepository cartItemRepository;
    private final IProductServiceClient productServiceClient;
    private final IUserServiceClient userServiceClient;
    private int attempt;

    // @CircuitBreaker(name = "productService", fallbackMethod = "AddToCartFallBack" )
    @Retry(name = "productServiceRetry", fallbackMethod = "AddToCartFallBack")
    public boolean AddToCart(String userId, CartItemRequest request) {
        System.out.println("ATTEMPT COUNT : " + ++attempt);
        ProductResponse productResponse = productServiceClient.GetProductDetails(Long.valueOf(request.getProductId()));

        if (productResponse == null) {
            return false;
        }

        if (productResponse.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        UserResponse userResponse = userServiceClient.GetUserInfo(userId);

        if (userResponse == null) {
            return false;
        }

        // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        // if (userOpt.isEmpty()) {
        // return false;
        // }

        // User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,
                request.getProductId());

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() +
                    request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    // Fallback methods must have the same parameters as the function they protect + an extra Exception parameter
    public boolean AddToCartFallBack(String userId, CartItemRequest request, Exception exception) {
        System.out.println("FALLBACK\n" + exception + "\n" + exception.getStackTrace());
        return false;
    }

    public boolean DeleteItem(String userId, String productId) {

        // Optional<Product> productOpt = productRepository.findById(productId);
        // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        // if (!productOpt.isPresent() || !userOpt.isPresent()) {
        // return false;
        // }

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem == null) {
            return false;
        }

        cartItemRepository.delete(cartItem);
        return true;
    }

    public List<CartItem> CartForUser(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void ClearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
