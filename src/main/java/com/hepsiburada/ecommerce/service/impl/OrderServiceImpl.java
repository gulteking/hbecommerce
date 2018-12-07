package com.hepsiburada.ecommerce.service.impl;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.service.CampaignService;
import com.hepsiburada.ecommerce.service.OrderService;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.exception.OrderException;
import com.hepsiburada.ecommerce.service.exception.ProductException;

public class OrderServiceImpl implements OrderService {

    private ProductService productService;
    private CampaignService campaignService;

    public OrderServiceImpl(ProductService productService, CampaignService campaignService) {
        this.productService = productService;
        this.campaignService = campaignService;
    }

    public void createOrder(String productCode, long quantity) throws ProductException, OrderException {
        Product productToReduce = productService.findOne(productCode);
        long newStock = reduceStock(productToReduce.getStock(), quantity);
        updateCampaignReport(productToReduce,quantity);
        productService.updateStock(productToReduce.getCode(), newStock);
    }

    private void updateCampaignReport(Product product,Long orderQuantity){

        Campaign campaign = campaignService.findOneByProduct(product);

        if (campaign != null && campaignService.isActive(campaign)) {
            campaignService.updateCampaignReport(campaign, product, orderQuantity);
        }
    }

    private long reduceStock(long currentStock, long count) throws OrderException {
        validateOrderCount(count);
        return currentStock - count;

    }

    private void validateOrderCount(long orderCount) throws OrderException {
        if (orderCount <= 0) {
            throw new OrderException("invalid order count");
        }
    }
}
