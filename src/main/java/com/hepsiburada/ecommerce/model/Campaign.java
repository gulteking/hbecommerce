package com.hepsiburada.ecommerce.model;

public class Campaign {
    private String name;
    private String productCode;
    private Long campaignEndTime;
    private Long duration;
    private Long maxDiscountPercent;
    private Long targetSalesCount;
    private Double turnover;
    private Long totalSalesCount;


    public Campaign(String name, String productCode, Long campaignEndTime, Long duration,
                    Long maxDiscountPercent, Long targetSalesCount) {
        this.name = name;
        this.productCode = productCode;
        this.maxDiscountPercent = maxDiscountPercent;
        this.targetSalesCount = targetSalesCount;
        this.campaignEndTime = campaignEndTime;
        this.duration = duration;
        turnover = 0D;
        totalSalesCount = 0L;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getCampaignEndTime() {
        return campaignEndTime;
    }

    public void setCampaignEndTime(Long campaignEndTime) {
        this.campaignEndTime = campaignEndTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getMaxDiscountPercent() {
        return maxDiscountPercent;
    }

    public void setMaxDiscountPercent(Long maxDiscountPercent) {
        this.maxDiscountPercent = maxDiscountPercent;
    }

    public Long getTargetSalesCount() {
        return targetSalesCount;
    }

    public void setTargetSalesCount(Long targetSalesCount) {
        this.targetSalesCount = targetSalesCount;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Long getTotalSalesCount() {
        return totalSalesCount;
    }

    public void setTotalSalesCount(Long totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
    }
}
