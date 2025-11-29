package com.muller.spring_store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.muller.spring_store.dto.ProductRequestDTO;
import com.muller.spring_store.dto.ProductResponseDTO;
import com.muller.spring_store.mapper.ProductMapper;
import com.muller.spring_store.model.Product;
import com.muller.spring_store.repository.ProductRepository;
import com.muller.spring_store.service.ProductService;

@ExtendWith(MockitoExtension.class)
class SpringStoreApplicationTests {

	@Mock
	private ProductRepository repository;

	@Mock
	private ProductMapper mapper;

	@InjectMocks
	private ProductService service;

	@SuppressWarnings("null")
	@Test
	void create_ShouldReturnResponseDTO_WhenDataIsValid() {

		// Teste: transformar requisição em produto
		ProductRequestDTO request = new ProductRequestDTO("Notebook", "Desc", new BigDecimal("5000"), 10);

		Product produtoMapeado = new Product();
		produtoMapeado.setPrice(new BigDecimal("5000"));
		produtoMapeado.setName("Notebook");

		when(mapper.toEntity(request)).thenReturn(produtoMapeado);

		// Teste: resposta do banco de dados
		Product produtoSalvo = new Product();
		produtoSalvo.setId(1L);
		produtoSalvo.setPrice(new BigDecimal("5000"));
		produtoSalvo.setName("Notebook");

		when(repository.save(any(Product.class))).thenReturn(produtoSalvo);

		// Teste: transformar produto em resposta
		ProductResponseDTO responseEsperado = new ProductResponseDTO(
				1L, "Notebook", "Desc", new BigDecimal("5000"), 10);
		when(mapper.toDTO(produtoSalvo)).thenReturn(responseEsperado);

		ProductResponseDTO resultado = service.create(request);

		// Verificação
		assertNotNull(resultado);
		assertEquals(responseEsperado, resultado);
		assertEquals("Notebook", resultado.getName());

		// Verifica se o service executou repository corretamente
		verify(repository, times(1)).save(any(Product.class));
	}

	@SuppressWarnings("null")
	@Test
	void create_ShouldReturnResponseDTO_WhenPriceIsNegative() {

		// Teste: exceção retorna corretamente com valores inválidos
		ProductRequestDTO requestRuim = new ProductRequestDTO("Notebook", "Desc", new BigDecimal("-10"), 10);

		Product produtoComPrecoRuim = new Product();
		produtoComPrecoRuim.setPrice(new BigDecimal("-10"));
		produtoComPrecoRuim.setName("Notebook");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			service.create(requestRuim);
		});

		when(mapper.toEntity(requestRuim)).thenReturn(produtoComPrecoRuim);

		assertEquals("O preço deve ser maior que zero", exception.getMessage());

		// Verifica se o service NUNCA executou
		verify(repository, never()).save(any());
	}

	@SuppressWarnings("null")
	@Test
	void update_ShouldReturnResponseDTO_WhenDataIsInvalid() {

		ProductRequestDTO request = new ProductRequestDTO("Notebook", "Desc", new BigDecimal("5000"), 10);

		when(repository.existsById(99L)).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> {
			service.update(99L, request);
		});

		verify(repository, never()).save(any());
	}

}
