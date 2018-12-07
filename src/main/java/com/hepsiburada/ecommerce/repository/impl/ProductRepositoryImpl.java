package com.hepsiburada.ecommerce.repository.impl;

import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private static ProductRepositoryImpl instance;

    public static ProductRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ProductRepositoryImpl();
        }
        return instance;
    }

    private List<Product> productList;

    private ProductRepositoryImpl() {
        productList = new ArrayList<>();
    }


    public void save(Product product) {
        removeIfProductAlreadyExists(product.getCode());
        productList.add(product);
    }

    public Product findOne(String productCode) {
        return productList.stream().filter(e -> e.getCode().equals(productCode)).findFirst().orElse(null);
    }

    public List<Product> findAll() {
        return productList;
    }

    private void removeIfProductAlreadyExists(String productCode) {
        productList.stream().filter(e -> e.getCode().equals(productCode)).findFirst().ifPresent(
                existingProduct -> productList.remove(existingProduct));

    }
}
