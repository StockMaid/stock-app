package com.stockmaid.stockapp.model;

import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDate;

public class Stock {

    @CsvBindByPosition(position = 0)
    private long sku;
    @CsvBindByPosition(position = 3)
    private String name;
    @CsvBindByPosition(position = 11)
    private double regularPrice;
    @CsvBindByPosition(position = 38)
    private String url;
    @CsvBindByPosition(position = 42)
    private String addToCartUrl;
    @CsvBindByPosition(position = 54)
    private boolean inStoreAvailability;
    @CsvBindByPosition(position = 56)
    private LocalDate inStoreAvailabilityUpdateDate;
    @CsvBindByPosition(position = 58)
    private boolean onlineAvailability;
    @CsvBindByPosition(position = 60)
    private LocalDate onlineAvailabilityUpdateDate;
    @CsvBindByPosition(position = 61)
    private LocalDate releaseDate;
    @CsvBindByPosition(position = 83)
    private String manufacturer;
    @CsvBindByPosition(position = 85)
    private String image;
    @CsvBindByPosition(position = 88)
    private String thumbnailImage;
    @CsvBindByPosition(position = 90)
    private String alternateViewsImage;
    @CsvBindByPosition(position = 107)
    private String condition;
    @CsvBindByPosition(position = 133)
    private String longDescription;

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddToCartUrl() {
        return addToCartUrl;
    }

    public void setAddToCartUrl(String addToCartUrl) {
        this.addToCartUrl = addToCartUrl;
    }

    public boolean isInStoreAvailability() {
        return inStoreAvailability;
    }

    public void setInStoreAvailability(boolean inStoreAvailability) {
        this.inStoreAvailability = inStoreAvailability;
    }

    public LocalDate getInStoreAvailabilityUpdateDate() {
        return inStoreAvailabilityUpdateDate;
    }

    public void setInStoreAvailabilityUpdateDate(LocalDate inStoreAvailabilityUpdateDate) {
        this.inStoreAvailabilityUpdateDate = inStoreAvailabilityUpdateDate;
    }

    public boolean isOnlineAvailability() {
        return onlineAvailability;
    }

    public void setOnlineAvailability(boolean onlineAvailability) {
        this.onlineAvailability = onlineAvailability;
    }

    public LocalDate getOnlineAvailabilityUpdateDate() {
        return onlineAvailabilityUpdateDate;
    }

    public void setOnlineAvailabilityUpdateDate(LocalDate onlineAvailabilityUpdateDate) {
        this.onlineAvailabilityUpdateDate = onlineAvailabilityUpdateDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getAlternateViewsImage() {
        return alternateViewsImage;
    }

    public void setAlternateViewsImage(String alternateViewsImage) {
        this.alternateViewsImage = alternateViewsImage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
