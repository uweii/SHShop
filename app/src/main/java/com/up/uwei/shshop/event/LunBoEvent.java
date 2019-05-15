package com.up.uwei.shshop.event;

public class LunBoEvent {
    private String message;
    public LunBoEvent(String message){
        this.message = message;;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
