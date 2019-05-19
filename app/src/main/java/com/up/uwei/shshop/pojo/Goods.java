package com.up.uwei.shshop.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Goods implements Parcelable{
    private int id;
    private String name;
    private int price;
    private String tag;
    private String place;
    private String express;
    private int salecount;
    private int bonus;
    private String promise;
    private String desc;
    private int secondhand;  //0代表新的，1代表二手  2.代表新旧所有
    private String imgs;


    public static final Creator<Goods> CREATOR  = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel parcel) {
            return new Goods(parcel);
        }

        @Override
        public Goods[] newArray(int i) {
            return new Goods[0];
        }
    };

    Goods(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        price = parcel.readInt();
        tag = parcel.readString();
        place = parcel.readString();
         express = parcel.readString();
         salecount = parcel.readInt();
         bonus = parcel.readInt();
        promise = parcel.readString();
        desc = parcel.readString();
        secondhand = parcel.readInt();  //0代表新的，1代表二手  2.代表新旧所有
        imgs = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeString(tag);
        parcel.writeString(place);
        parcel.writeString(express);
        parcel.writeInt(salecount);
        parcel.writeInt(bonus);
        parcel.writeString(promise);
        parcel.writeString(desc);
        parcel.writeInt(secondhand);  //0代表新的，1代表二手  2.代表新旧所有
        parcel.writeString(imgs);
    }

    public Goods(int id, String name, int price, String tag, String place, String express, int salecount, int bonus,
                 String promise, String desc, int secondhand, String imgs) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.place = place;
        this.express = express;
        this.salecount = salecount;
        this.bonus = bonus;
        this.promise = promise;
        this.desc = desc;
        this.secondhand = secondhand;
        this.imgs = imgs;
    }



    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Goods() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getExpress() {
        return express;
    }
    public void setExpress(String express) {
        this.express = express;
    }
    public int getSalecount() {
        return salecount;
    }
    public void setSalecount(int salecount) {
        this.salecount = salecount;
    }
    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public String getPromise() {
        return promise;
    }
    public void setPromise(String promise) {
        this.promise = promise;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getSecondhand() {
        return secondhand;
    }
    public void setSecondhand(int secondhand) {
        this.secondhand = secondhand;
    }



}
