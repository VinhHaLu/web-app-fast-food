package com.example.fastfood.Models;

public class SimpleVerticalModel {

    String  price_product,quantity_product,id_product,image_product,category_product,desc_product,name_product;

    public SimpleVerticalModel(String price_product, String quantity_product, String id_product, String image_product, String category_product, String desc_product, String name_product) {
        this.price_product = price_product;
        this.quantity_product = quantity_product;
        this.id_product = id_product;
        this.image_product = image_product;
        this.category_product = category_product;
        this.desc_product = desc_product;
        this.name_product = name_product;
    }

    public String getPrice_product() {
        return price_product;
    }

    public void setPrice_product(String price_product) {
        this.price_product = price_product;
    }

    public String getQuantity_product() {
        return quantity_product;
    }

    public void setQuantity_product(String quantity_product) {
        this.quantity_product = quantity_product;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }

    public String getCategory_product() {
        return category_product;
    }

    public void setCategory_product(String category_product) {
        this.category_product = category_product;
    }

    public String getDesc_product() {
        return desc_product;
    }

    public void setDesc_product(String desc_product) {
        this.desc_product = desc_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public SimpleVerticalModel(){

    }

}
