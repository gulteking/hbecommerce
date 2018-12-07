package com.hepsiburada.ecommerce.service.impl;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.service.CampaignService;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.exception.CampaignException;
import com.hepsiburada.ecommerce.service.exception.ProductException;
import com.hepsiburada.ecommerce.utility.Time;

public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;
    private ProductService productService;

    public CampaignServiceImpl(CampaignRepository campaignRepository, ProductService productService) {
        this.campaignRepository = campaignRepository;
        this.productService = productService;
    }

    public Campaign findOne(String name) throws CampaignException {
        Campaign campaign = campaignRepository.findOne(name);
        if (campaign == null) {
            throw new CampaignException("not found");
        }
        return campaign;
    }

    public Campaign findOneByProduct(Product product) {
        return campaignRepository.findOneByProductCode(product.getCode());
    }


    public void createCampaign(String name,
                               String productCode,
                               long duration,
                               long maxDiscountPercent,
                               long targetSalesCount) throws CampaignException, ProductException {

        Campaign campaign = new Campaign(
                name,
                productCode,
                calculateCampaignEndtime(duration),
                duration,
                maxDiscountPercent,
                targetSalesCount);
        validateCampaign(campaign);

        campaignRepository.save(campaign);

    }

    public boolean isActive(Campaign campaign) {
        return campaign.getCampaignEndTime() > Time.getTime();
    }


    public double calculateAverageItemPrice(Campaign campaign) {
        if (campaign.getTotalSalesCount() == 0D) {
            return 0D;
        }
        return campaign.getTurnover() / campaign.getTotalSalesCount();
    }

    public void updateCampaignReport(Campaign campaign, Product productToReduce, long quantity) {
        campaign.setTotalSalesCount(campaign.getTotalSalesCount() + quantity);
        campaign.setTurnover(campaign.getTurnover() + productToReduce.getPrice());
        campaignRepository.save(campaign);
    }


    private long calculateCampaignEndtime(long duration) {
        return Time.getTime() + duration;
    }

    private void validateCampaign(Campaign campaign) throws CampaignException, ProductException {
        verifyCampaignNameNotExistsInDb(campaign.getName());
        validateCampaignDuration(campaign.getDuration());
        validateTime(campaign.getCampaignEndTime());


        Product product = productService.findOne(campaign.getProductCode());
        validateTargetSalesCount(campaign.getTargetSalesCount(), product.getStock());
        validateDiscountPercent(campaign.getMaxDiscountPercent());
    }


    private void verifyCampaignNameNotExistsInDb(String name) throws CampaignException {

        if (campaignRepository.findOne(name) != null) {
            throw new CampaignException("campaign already exists");
        }
    }

    private void validateCampaignDuration(long duration) throws CampaignException {

        if (duration < 0) {
            throw new CampaignException("invalid duration");
        }
    }


    private void validateTime(long time) throws CampaignException {

        if (time > 24) {
            throw new CampaignException("invalid campaign end time");
        }
    }

    private void validateTargetSalesCount(long targetSalesCount, long stock) throws CampaignException {
        if (targetSalesCount < 0) {
            throw new CampaignException("target sales is negative");
        } else if (targetSalesCount > stock) {
            throw new CampaignException("target sales count is bigger than stock");
        }

    }

    private void validateDiscountPercent(long discountPercent) throws CampaignException {

        if (discountPercent <= 0 || discountPercent > 100) {
            throw new CampaignException("invalid discount percent");
        }
    }
}

