package APITaller.example.Tienda.Model.Request;

import APITaller.example.Tienda.Model.Entity.Category;

import java.util.Optional;


public class ProductRequest {
    private String name;
    private double price;
    private int categoryId;

    private boolean descuento;
    public ProductRequest() {
        // Default constructor
    }

    public ProductRequest(String name, double price, int categoryId, boolean descuento) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.descuento = descuento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }
}