package com.muller.spring_store.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.spring_store.dto.SalesReportDTO;
import com.muller.spring_store.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    // Para relatórios simples de leitura, é aceitável o Controller chamar o
    // Repository direto. Se tivesse regra de negócio, criaria um Service
    private final ProductRepository repository;

    @GetMapping("/sales")
    @Operation(summary = "Relatório de Vendas", description = "Retorna o total vendido por produto (Faturamento)")
    public List<SalesReportDTO> getSalesReport() {
        return repository.getSalesReport();
    }
}
