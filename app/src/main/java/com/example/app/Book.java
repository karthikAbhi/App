package com.example.app;

public class Book {

    private double Price;
    private String Title;
    private String Category;
    private String Description;
    private int Thumbnail;

    public Book() {
    }

    public Book(String title, String category, String description, int thumbnail, double price) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price = price;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
