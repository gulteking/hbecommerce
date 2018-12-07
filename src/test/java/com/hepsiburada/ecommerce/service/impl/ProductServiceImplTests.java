package com.hepsiburada.ecommerce.service.impl;

import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.exception.ProductException;
import com.hepsiburada.ecommerce.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test(expected = ProductException.class)
    public void create_whenCodeAlreadyExistsThenShouldThrowProductException() throws ProductException {
        String code = "foo";
        Product existingProduct = new Product(code, 1L, 1D);
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(existingProduct);
        productService.create(code, 1D, 1L);
    }


    @Test(expected = ProductException.class)
    public void create_whenPriceIsNegativeThenShouldThrowProductException() throws ProductException {
        String code = "foo";
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(null);
        productService.create(code, -1D, 1L);
    }


    @Test(expected = ProductException.class)
    public void create_whenStockIsNegativeThenShouldThrowProductException() throws ProductException {
        String code = "foo";
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(null);
        productService.create(code, 1D, -1L);
    }


    @Test
    public void create_whenProductIsValidThenShouldCreateProduct() throws ProductException {
        String code = "foo";
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(null);
        productService.create(code, 1D, 1L);

    }

    @Test(expected = ProductException.class)
    public void findOne_whenProductNotExistsThenShouldThrowProductException() throws ProductException {
        String code = "foo";
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(null);
        productService.findOne(code);

    }

    @Test
    public void findOne_whenProductExistsThenShouldReturnProduct() throws ProductException {
        Product expectedProduct = new Product("foo", 1L, 1D);
        Mockito.when(mockProductRepository.findOne(expectedProduct.getCode())).thenReturn(expectedProduct);
        Product actualProduct = productService.findOne(expectedProduct.getCode());

        Assert.assertEquals(expectedProduct.getCode(), actualProduct.getCode());

    }


    @Test(expected = ProductException.class)
    public void updateStock_whenProductNotExistsThenShouldThrowProductException() throws ProductException {
        String code = "foo";
        Mockito.when(mockProductRepository.findOne(code)).thenReturn(null);
        productService.updateStock(code, 5L);

    }

    @Test(expected = ProductException.class)
    public void updateStock_whenNewStockIsNegativeThenShouldThrowProductException() throws ProductException {
        Product product = new Product("foo", 1L, 1D);
        Mockito.when(mockProductRepository.findOne(product.getCode())).thenReturn(product);
        productService.updateStock(product.getCode(), -5L);

    }

    @Test
    public void updateStock_whenNewStockIsValidThenShouldUpdateStock() throws ProductException {
        Product product = new Product("foo", 1L, 1D);
        Long newStock = 0L;
        Mockito.when(mockProductRepository.findOne(product.getCode())).thenReturn(product);
        productService.updateStock(product.getCode(), newStock);

        Assert.assertEquals(product.getStock(), newStock);

    }

}
