package com.example.javafx_train;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Billing_Table_Data_Add {
    private StringProperty id;
    private StringProperty product_name;
    private StringProperty product_Quntity;
    private StringProperty product_Price;

    public Billing_Table_Data_Add() {
        id=new SimpleStringProperty(this,"id");
        product_name =new SimpleStringProperty(this,"product_name");
        product_Quntity =new SimpleStringProperty(this,"product_Quantity");
        product_Price =new SimpleStringProperty(this,"product_price");
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public StringProperty product_nameProperty() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public String getProduct_Quntity() {
        return product_Quntity.get();
    }

    public StringProperty product_QuntityProperty() {
        return product_Quntity;
    }

    public void setProduct_Quntity(String product_Quntity) {
        this.product_Quntity.set(product_Quntity);
    }

    public String getProduct_Price() {
        return product_Price.get();
    }

    public StringProperty product_PriceProperty() {
        return product_Price;
    }

    public void setProduct_Price(String product_Price) {
        this.product_Price.set(product_Price);
    }
}
