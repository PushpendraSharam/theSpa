package app.myapp.myapplication.Modals;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ItemModal {
    String imageUrl;
    String productName;


    ArrayAdapter<String> adapter;

    Double []  priceByTime;

    public ItemModal(String imageUrl, String productName, ArrayAdapter<String> adapter, Double[] priceByTime) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.adapter = adapter;
        this.priceByTime = priceByTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    public Double[] getPriceByTime() {
        return priceByTime;
    }

    public void setPriceByTime(Double[] priceByTime) {
        this.priceByTime = priceByTime;
    }
}
