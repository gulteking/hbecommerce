package com.hepsiburada.ecommerce.service.impl;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.CampaignPriceCalculationService;
import com.hepsiburada.ecommerce.utility.Time;

public class CampaignPriceCalculationServiceImpl implements CampaignPriceCalculationService {


    private CampaignRepository campaignRepository;
    private ProductRepository productRepository;


    public CampaignPriceCalculationServiceImpl(CampaignRepository campaignRepository, ProductRepository productRepository) {
        this.campaignRepository = campaignRepository;
        this.productRepository = productRepository;
    }

    public void calculateCampaignPrices() {
        for (Product product : productRepository.findAll()) {
            Campaign campaign = campaignRepository.findOneByProductCode(product.getCode());
            if (isValid(campaign)) {
                calculateCampaignPrice(product, campaign);
            } else {
                rollbackProductPrice(product);
            }
        }
    }

    private void rollbackProductPrice(Product product) {
        product.setPrice(product.getDefaultPrice());
        productRepository.save(product);
    }


    private boolean isValid(Campaign campaign) {
        return campaign != null && isActive(campaign);
    }

    private boolean isActive(Campaign campaign) {
        return campaign.getCampaignEndTime() > Time.getTime();
    }


    private void calculateCampaignPrice(Product product, Campaign campaign) {
        double hourlyDiscountPercent = campaign.getMaxDiscountPercent() / campaign.getDuration();
        long hourlyTargetSalesCount = (long) Math.ceil(campaign.getTargetSalesCount() / campaign.getDuration());

        long remainingHours = campaign.getCampaignEndTime() - Time.getTime();

        long spentHours = campaign.getDuration() - remainingHours;


        long expectedTotalSales = spentHours * hourlyTargetSalesCount;

        if (campaign.getTotalSalesCount() < expectedTotalSales) {

            double discountPercent = hourlyDiscountPercent * spentHours;

            double discountPrice = (product.getDefaultPrice() / 100D) * discountPercent;

            double newProductPrice = product.getDefaultPrice() - discountPrice;

            product.setPrice(newProductPrice);
            productRepository.save(product);
        }

    }
}
