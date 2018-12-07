package com.hepsiburada.ecommerce.repository;

import com.hepsiburada.ecommerce.model.Campaign;

public interface CampaignRepository {
    void save(Campaign campaign);
    Campaign findOne(String name);
    Campaign findOneByProductCode(String productCode);
}
