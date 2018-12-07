package com.hepsiburada.ecommerce.service;

import com.hepsiburada.ecommerce.service.exception.OrderException;
import com.hepsiburada.ecommerce.service.exception.ProductException;

public interface OrderService {
    void createOrder(String productCode, long quantity) throws ProductException, OrderException;
}
