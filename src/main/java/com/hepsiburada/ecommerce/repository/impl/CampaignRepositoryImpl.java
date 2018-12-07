package com.hepsiburada.ecommerce.repository.impl;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.repository.CampaignRepository;

import java.util.ArrayList;
import java.util.List;

public class CampaignRepositoryImpl implements CampaignRepository {

    private static CampaignRepositoryImpl instance;

    public static CampaignRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CampaignRepositoryImpl();
        }
        return instance;
    }

    private List<Campaign> campaignList;

    public void save(Campaign campaign) {
        removeIfCampaignNameAlreadyExists(campaign.getName());
        campaignList.add(campaign);
    }

    public Campaign findOne(String name) {
        return campaignList.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Campaign findOneByProductCode(String productCode) {
        return campaignList.stream().filter(e -> e.getProductCode().equals(productCode)).findFirst().orElse(null);
    }


    private CampaignRepositoryImpl() {
        campaignList = new ArrayList<>();
    }

    private void removeIfCampaignNameAlreadyExists(String name) {
        campaignList.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().
                ifPresent(existingCampaign -> campaignList.remove(existingCampaign));
    }
}
