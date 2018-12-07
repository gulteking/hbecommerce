package com.hepsiburada.ecommerce.service.impl;


import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.service.CampaignService;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.exception.OrderException;
import com.hepsiburada.ecommerce.service.exception.ProductException;
import com.hepsiburada.ecommerce.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTests {

    @Mock
    private CampaignService campaignService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test(expected = OrderException.class)
    public void createOrder_whenOrderQuantityIsNegativeThenShouldThrowOrderException() throws ProductException, OrderException {
        Product product = new Product("foo", 1L, 1D);

        Mockito.when(productService.findOne(product.getCode())).thenReturn(product);
        Mockito.when(campaignService.findOneByProduct(product)).thenReturn(null);
        orderService.createOrder(product.getCode(), -5L);
    }


    @Test
    public void createOrder_whenOrderIsValidThenShouldCreateOrder() throws ProductException, OrderException {
        Product product = new Product("foo", 1L, 1D);
        Mockito.when(productService.findOne(product.getCode())).thenReturn(product);
        Mockito.when(campaignService.findOneByProduct(product)).thenReturn(null);
        orderService.createOrder(product.getCode(), 1L);
    }
}
