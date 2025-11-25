package com.muller.spring_store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muller.spring_store.dto.ProductRequestDTO;
import com.muller.spring_store.dto.ProductResponseDTO;
import com.muller.spring_store.mapper.ProductMapper;
import com.muller.spring_store.model.Product;
import com.muller.spring_store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ProductResponseDTO findById(long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        return mapper.toDTO(product);
    }

    private void validateProductFields(Product product) {
        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        if (product.getName().length() <= 2) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 3 caracteres");
        } else if (product.getName().length() > 60) {
            throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres");
        }
    }

    public ProductResponseDTO update(long id, ProductRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }

        Product productAtualizado = mapper.toEntity(dto);
        productAtualizado.setId(id);

        validateProductFields(productAtualizado);

        return mapper.toDTO(repository.save(productAtualizado));
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }

        repository.deleteById(id);
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);

        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        validateProductFields(product);

        return mapper.toDTO(repository.save(product));
    }

}
