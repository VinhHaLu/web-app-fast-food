package com.example.fastfood.Models;

public class CategoryModel {

    public CategoryModel(){

    }
    String cate_title, cate_img, id_cate;

    public CategoryModel(String cate_title, String cate_img, String id_cate) {
        this.cate_title = cate_title;
        this.cate_img = cate_img;
        this.id_cate = id_cate;
    }

    public String getCate_title() {
        return cate_title;
    }

    public void setCate_title(String cate_title) {
        this.cate_title = cate_title;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public String getId_cate() {
        return id_cate;
    }

    public void setId_cate(String id_cate) {
        this.id_cate = id_cate;
    }
}
