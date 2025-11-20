package com.muller.spring_store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muller.spring_store.model.Product;
import com.muller.spring_store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public Product update(long id, Product productAtualizado) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }
        productAtualizado.setId(id);

        if (productAtualizado.getPrice() == null || productAtualizado.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        if (productAtualizado.getName().length() <= 2) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 3 caracteres");
        } else if (productAtualizado.getName().length() > 60) {
            throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres");
        }

        return repository.save(productAtualizado);
    }

    public void delete (long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }

        repository.deleteById(id);
    }

    public Product save(Product product) {
        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        if (product.getName().length() <= 2) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 3 caracteres");
        } else if (product.getName().length() > 60) {
            throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres");
        }

        return repository.save(product);
    }

}
