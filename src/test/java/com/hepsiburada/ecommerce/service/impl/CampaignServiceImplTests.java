package com.hepsiburada.ecommerce.service.impl;


import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.exception.CampaignException;
import com.hepsiburada.ecommerce.service.exception.ProductException;
import com.hepsiburada.ecommerce.utility.InvalidTimeException;
import com.hepsiburada.ecommerce.utility.Time;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplTests {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ProductService productService;


    @InjectMocks
    private CampaignServiceImpl campaignService;


    @Before
    public void resetTime() {
        Time.resetTime();
    }

    @Test(expected = CampaignException.class)
    public void findOne_whenCampaignNotExistsThenShouldThrowCampaignException() throws CampaignException {
        String name = "foo";
        when(campaignRepository.findOne(name)).thenReturn(null);
        campaignService.findOne(name);


    }

    @Test
    public void findOne_whenCampaignFoundThenShouldReturnCampaign() throws CampaignException {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);

        when(campaignRepository.findOne(campaign.getName())).thenReturn(campaign);

        Campaign actualCampaign = campaignService.findOne(campaign.getName());
        Assert.assertEquals(campaign.getName(), actualCampaign.getName());
    }

    @Test
    public void findOneByProduct_whenCampaignNotExistsForProductThenShouldReturnNull() throws CampaignException {
        Product product = new Product("a", 1L, 1D);
        when(campaignRepository.findOneByProductCode(product.getCode())).thenReturn(null);
        Assert.assertNull(campaignService.findOneByProduct(product));

    }


    @Test
    public void findOneByProduct_whenCampaignFoundForProductThenShouldReturnCampaign() {
        Product product = new Product("a", 1L, 1D);

        Campaign campaign = new Campaign("foo", product.getCode(),
                5L, 5L, 5L, 5L);

        when(campaignRepository.findOneByProductCode(product.getCode())).thenReturn(campaign);
        Assert.assertEquals(campaignService.findOneByProduct(product), campaign);

    }


    @Test
    public void updateCampaignReport_whenProductAndQuantityIsValidThenShouldUpdateCampaign() {
        Product product = new Product("a", 1L, 1D);

        Campaign campaign = new Campaign("foo", product.getCode(),
                5L, 5L, 5L, 5L);

        Long orderQuantity = 1L;
        when(campaignRepository.findOneByProductCode(product.getCode())).thenReturn(campaign);

        campaignService.updateCampaignReport(campaign, product, orderQuantity);

        Assert.assertEquals(campaign.getTotalSalesCount(), orderQuantity);
        Assert.assertEquals(campaign.getTurnover(), product.getPrice());

    }

    @Test
    public void calculateAverageItemPrice_whenTotalSalesAndTurnoverIsValidThenShouldReturnAveragePrice() {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);

        campaign.setTotalSalesCount(10L);
        campaign.setTurnover(10D);

        Double expectedAveragePrice = campaign.getTurnover() / campaign.getTotalSalesCount();

        Double actualPrice = campaignService.calculateAverageItemPrice(campaign);

        Assert.assertEquals(expectedAveragePrice, actualPrice);

    }

    @Test
    public void calculateAverageItemPrice_whenTotalSalesIsZeroThenShouldReturnZero() {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);
        Double expectedAveragePrice = 0D;

        Double actualPrice = campaignService.calculateAverageItemPrice(campaign);

        Assert.assertEquals(expectedAveragePrice, actualPrice);
    }

    @Test
    public void isActive_whenCampaignIsActiveThenShouldReturnTrue() {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);

        Assert.assertTrue(campaignService.isActive(campaign));

    }

    @Test
    public void isActive_whenCampaignIsEndedThenShouldReturnFalse() throws InvalidTimeException {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);

        Time.increase(10L);
        Assert.assertFalse(campaignService.isActive(campaign));

    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenCampaignNameExistsThenShouldThrowCampaignException() throws CampaignException, ProductException {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, 5L, 5L, 5L);
        when(campaignRepository.findOne(campaign.getName())).thenReturn(campaign);
        campaignService.createCampaign(
                campaign.getName(),
                campaign.getProductCode(),
                campaign.getDuration(),
                campaign.getMaxDiscountPercent(),
                campaign.getTargetSalesCount());

    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenDurationIsNegativeThenShouldThrowCampaignException() throws CampaignException, ProductException {
        Campaign campaign = new Campaign("foo", "fooProduct",
                5L, -5L, 5L, 5L);

        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);

        when(campaignRepository.findOne(campaign.getName())).thenReturn(null);
        campaignService.createCampaign(
                "foo",
                campaign.getProductCode(),
                campaign.getDuration(),
                campaign.getMaxDiscountPercent(),
                campaign.getTargetSalesCount());
    }


    @Test(expected = CampaignException.class)
    public void createCampaign_whenCampaignEndTimeIsInvalidThenShouldThrowCampaignException()
            throws InvalidTimeException, CampaignException, ProductException {
        Time.increase(23);
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                1L,
                1L);
    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenTargetSalesCountIsBiggerThanStockThenShouldThrowCampaignException() throws CampaignException, ProductException {
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                1L,
                10L);
    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenTargetSalesCountIsNegativeThenShouldThrowCampaignException() throws ProductException, CampaignException {
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                1L,
                -1L);
    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenDiscountPercentIsNegativeThenShouldThrowCampaignException() throws CampaignException, ProductException {
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                -1L,
                1L);
    }

    @Test(expected = CampaignException.class)
    public void createCampaign_whenDiscountPercentIsBiggerThan100ThenShouldThrowCampaignException() throws CampaignException, ProductException {
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                120L,
                1L);
    }

    @Test
    public void createCampaign_whenCampaignIsValidThenShouldAddCampaign() throws CampaignException, ProductException {
        Product product = new Product("fooProduct", 1L, 1D);
        when(productService.findOne(product.getCode())).thenReturn(product);
        when(campaignRepository.findOne("foo")).thenReturn(null);

        campaignService.createCampaign(
                "foo",
                product.getCode(),
                5L,
                10L,
                1L);
    }
}
