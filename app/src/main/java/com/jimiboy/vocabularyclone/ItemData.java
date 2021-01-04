package com.jimiboy.vocabularyclone;

public class ItemData {
    String idx;
    String title;
    String writer;
    String date;
    String img;
    int price;
    String explan;

    public ItemData(String idx, String title, String writer, String date, String img, int price, String explan) {
        this.idx = idx;
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.img = img;
        this.price = price;
        this.explan = explan;
    }
}
