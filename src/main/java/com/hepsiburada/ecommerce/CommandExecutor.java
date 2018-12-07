package com.hepsiburada.ecommerce;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.impl.CampaignRepositoryImpl;
import com.hepsiburada.ecommerce.repository.impl.ProductRepositoryImpl;
import com.hepsiburada.ecommerce.service.CampaignPriceCalculationService;
import com.hepsiburada.ecommerce.service.CampaignService;
import com.hepsiburada.ecommerce.service.OrderService;
import com.hepsiburada.ecommerce.service.ProductService;
import com.hepsiburada.ecommerce.service.impl.CampaignPriceCalculationServiceImpl;
import com.hepsiburada.ecommerce.service.impl.CampaignServiceImpl;
import com.hepsiburada.ecommerce.service.impl.OrderServiceImpl;
import com.hepsiburada.ecommerce.service.impl.ProductServiceImpl;
import com.hepsiburada.ecommerce.service.exception.CampaignException;
import com.hepsiburada.ecommerce.service.exception.OrderException;
import com.hepsiburada.ecommerce.service.exception.ProductException;
import com.hepsiburada.ecommerce.utility.InvalidTimeException;
import com.hepsiburada.ecommerce.utility.Time;


public class CommandExecutor {
    private CampaignService campaignService;
    private OrderService orderService;
    private ProductService productService;
    private CampaignPriceCalculationService campaignPriceCalculationService;

    public CommandExecutor() {
        productService = new ProductServiceImpl(ProductRepositoryImpl.getInstance());
        campaignService = new CampaignServiceImpl(CampaignRepositoryImpl.getInstance(), productService);
        orderService = new OrderServiceImpl(productService, campaignService);
        campaignPriceCalculationService = new CampaignPriceCalculationServiceImpl(CampaignRepositoryImpl.getInstance(),
                ProductRepositoryImpl.getInstance());
    }

    public void createProduct(String[] args) throws ProductException {
        String productCode = args[1];
        Long stock = Long.parseLong(args[2]);
        Double price = Double.parseDouble(args[3]);
        productService.create(productCode, price, stock);
        System.out.println("Product created; code " + productCode + ", price " + price + ", stock " + stock);
    }


    public void getProductInfo(String[] args) throws ProductException {
        Product product = productService.findOne(args[1]);
        System.out.println("Product " + product.getCode() + " info; price " + product.getPrice() + ", stock " + product.getStock());
    }

    public void createOrder(String[] args) throws OrderException, ProductException {
        String productCode = args[1];
        Long quantity = Long.parseLong(args[2]);
        orderService.createOrder(productCode, quantity);
        System.out.println("Order created; product " + productCode + ", quantity " + quantity);
    }


    public void createCampaign(String[] args) throws CampaignException, ProductException {
        String campaignName = args[1];
        String productCode = args[2];
        Long duration = Long.parseLong(args[3]);
        Long maxDiscountPercent = Long.parseLong(args[4]);
        Long targetSalesCount = Long.parseLong(args[5]);
        campaignService.createCampaign(campaignName, productCode, duration, maxDiscountPercent, targetSalesCount);
        System.out.println("Campaign created;" +
                " name " + campaignName + "," +
                " product " + productCode + "," +
                " duration " + duration + ",\n" +
                " limit " + maxDiscountPercent + "," +
                " target sales count " + targetSalesCount);
    }


    public void getCampaignInfo(String[] args) throws CampaignException {
        Campaign campaign = campaignService.findOne(args[1]);
        System.out.println(
                "Campaign " + campaign.getName() + " info; " +
                        "Status, " + campaignStatus(campaignService.isActive(campaign)) + " " +
                        "Target Sales " + campaign.getTargetSalesCount() + ",\n" +
                        "Total Sales " + campaign.getTotalSalesCount() + ", " +
                        "Turnover " + campaign.getTurnover() + ", " +
                        "Average Item Price " + campaignService.calculateAverageItemPrice(campaign));

    }

    public void increaseTime(String[] args) throws InvalidTimeException {
        Time.increase(Long.valueOf(args[1]));
        campaignPriceCalculationService.calculateCampaignPrices();

        System.out.println("Time is " + Time.getTime() + ":00");
    }

    private String campaignStatus(boolean status) {
        if (status) {
            return "Active";
        }
        return "Ended";
    }
}
