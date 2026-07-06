package com.example.shoppingcart.nmims;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByName(String name);
}
