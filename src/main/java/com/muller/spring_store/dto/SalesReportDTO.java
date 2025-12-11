package com.muller.spring_store.dto;

import java.math.BigDecimal;

public interface SalesReportDTO {
    String getProductName();

    Long getQuantity();

    BigDecimal getTotalRevenue();
}
