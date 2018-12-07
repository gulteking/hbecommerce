package com.hepsiburada.ecommerce.repository;

import com.hepsiburada.ecommerce.model.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    Product findOne(String productCode);

    List<Product> findAll();
}
