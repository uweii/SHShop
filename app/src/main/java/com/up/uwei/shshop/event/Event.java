package com.up.uwei.shshop.event;

import com.up.uwei.shshop.pojo.Goods;

import java.util.ArrayList;

public class Event {
    private String message;
    private int time;

    private ArrayList<Goods> mGoods;

    public ArrayList<Goods> getGoods() {
        return mGoods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        mGoods = goods;
    }

    public Event(String message, int time){
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
