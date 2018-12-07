package com.hepsiburada.ecommerce.service.impl;

import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.exception.ProductException;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(String code, double price, long stock)
            throws ProductException {
        Product product = new Product(code, stock, price);
        validateProduct(product);
        productRepository.save(product);
    }


    public Product findOne(String code) throws ProductException {
        Product product = productRepository.findOne(code);
        if (product == null) {
            throw new ProductException("not found");
        }
        return product;
    }

    public void updateStock(String code, long newStock) throws ProductException {
        Product product = findOne(code);
        validateStock(newStock);
        product.setStock(newStock);
        productRepository.save(product);
    }


    private void validateProduct(Product product)
            throws ProductException {
        if (productRepository.findOne(product.getCode()) != null) {
            throw new ProductException("product already exists");
        }

        validatePrice(product.getPrice());
        validateStock(product.getStock());
    }

    private void validateStock(long stock) throws ProductException {
        if (stock < 0) {
            throw new ProductException("invalid stock");
        }
    }

    private void validatePrice(double price) throws ProductException {
        if (price < 0) {
            throw new ProductException("invalid price");
        }
    }
}
