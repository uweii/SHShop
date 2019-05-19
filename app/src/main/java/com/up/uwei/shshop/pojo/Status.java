package com.up.uwei.shshop.pojo;

public class Status {
    private String status;
    private int code;
    public Status(String status, int code) {
        this.code = code;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
