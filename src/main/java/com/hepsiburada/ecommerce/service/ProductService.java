package com.hepsiburada.ecommerce.service;

import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.service.exception.ProductException;

public interface ProductService {
    void create(String code, double price, long stock) throws ProductException;
    Product findOne(String code) throws ProductException;
    void updateStock(String code, long newStock) throws ProductException;

}
