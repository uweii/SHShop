package com.up.uwei.shshop.pojo;

public class SearchBean {
    private int offset;
    private int length;
    private int secondhand;
    private String key;
    private String place;

    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getSecondhand() {
        return secondhand;
    }
    public void setSecondhand(int secondhand) {
        this.secondhand = secondhand;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public SearchBean(int offset, int length, int secondhand, String key, String place) {
        super();
        this.offset = offset;
        this.length = length;
        this.secondhand = secondhand;
        this.key = key;
        this.place = place;
    }



    public SearchBean() {}
}
