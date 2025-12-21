package fr.adriencournand.formation.ecom_application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom_application.dto.CartItemRequest;
import fr.adriencournand.formation.ecom_application.model.CartItem;
import fr.adriencournand.formation.ecom_application.model.Product;
import fr.adriencournand.formation.ecom_application.model.User;
import fr.adriencournand.formation.ecom_application.repository.ICartItemRepository;
import fr.adriencournand.formation.ecom_application.repository.IProductRepository;
import fr.adriencournand.formation.ecom_application.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // A bit obscure but apparently needed to perform a remove operation on a
               // database
public class CartService {

    private final IProductRepository productRepository;
    private final ICartItemRepository cartItemRepository;
    private final IUserRepository userRepository;

    public boolean AddToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());

        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean DeleteItem(String userId, Long productId) {

        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (!productOpt.isPresent() || !userOpt.isPresent()) {
            return false;
        }

        cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
        return true;
    }

    public List<CartItem> CartForUser(String userId) {
        return userRepository.findById(Long.valueOf(userId)).map(cartItemRepository::findByUser).orElseGet(List::of);
    }

    public void ClearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
