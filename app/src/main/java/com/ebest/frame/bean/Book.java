package com.ebest.frame.bean;


import com.ebest.frame.annomationlib.parama.annomation.Arg;
import com.ebest.frame.annomationlib.parama.annomation.NonNull;

public class Book{
    @NonNull
    @Arg
    String username;
    @Arg
    float price;

    public Book() {
    }

    public Book(String username, float price) {
        this.username = username;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "username='" + username + '\'' +
                ", price=" + price +
                '}';
    }
}
