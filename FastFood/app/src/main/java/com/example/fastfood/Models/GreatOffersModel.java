package com.example.fastfood.Models;

public class GreatOffersModel {
    public GreatOffersModel(){

    }
    private int offer_img;

    public GreatOffersModel(int offer_img, String text_title, String text_description, String text_saleoff, String text_ratting) {
        this.offer_img = offer_img;
        this.text_title = text_title;
        this.text_description = text_description;
        this.text_saleoff = text_saleoff;
        this.text_ratting = text_ratting;
    }

    public int getOffer_img() {
        return offer_img;
    }

    public void setOffer_img(int offer_img) {
        this.offer_img = offer_img;
    }

    public String getText_title() {
        return text_title;
    }

    public void setText_title(String text_title) {
        this.text_title = text_title;
    }

    public String getText_description() {
        return text_description;
    }

    public void setText_description(String text_description) {
        this.text_description = text_description;
    }

    public String getText_saleoff() {
        return text_saleoff;
    }

    public void setText_saleoff(String text_saleoff) {
        this.text_saleoff = text_saleoff;
    }

    public String getText_ratting() {
        return text_ratting;
    }

    public void setText_ratting(String text_ratting) {
        this.text_ratting = text_ratting;
    }

    private String text_title,text_description,text_saleoff,text_ratting;
}
