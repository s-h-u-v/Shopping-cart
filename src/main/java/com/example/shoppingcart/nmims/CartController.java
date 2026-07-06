package com.example.shoppingcart.nmims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartItemRepository repository;

    @GetMapping
    public List<CartItem> getCart() {
        return repository.findAll();
    }

    @PostMapping
    public CartItem addToCart(@RequestBody CartItem item) {
        Optional<CartItem> existingOpt = repository.findByName(item.getName());
        if (existingOpt.isPresent()) {
            CartItem existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + 1);
            return repository.save(existing);
        } else {
            item.setQuantity(1);
            return repository.save(item);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long id) {
        Optional<CartItem> itemOpt = repository.findById(id);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                repository.save(item);
                return ResponseEntity.ok(item);
            } else {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
