package com.hepsiburada.ecommerce.service;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.service.exception.CampaignException;
import com.hepsiburada.ecommerce.service.exception.ProductException;

public interface CampaignService {
    Campaign findOne(String name) throws CampaignException;

    Campaign findOneByProduct(Product product);

    void createCampaign(
            String name,
            String productCode,
            long duration,
            long maxDiscountPercent,
            long targetSalesCount) throws CampaignException, ProductException;

    boolean isActive(Campaign campaign);

    double calculateAverageItemPrice(Campaign campaign);

    void updateCampaignReport(Campaign campaign, Product productToReduce, long quantity);
}
