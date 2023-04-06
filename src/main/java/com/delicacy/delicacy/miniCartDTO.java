package com.delicacy.delicacy;

public class miniCartDTO {
    private int miniSerial;
    private String miniItem, miniName, miniPrice;


    public miniCartDTO(int miniSerial, String miniItem, String miniName, String miniPrice) {
        this.miniSerial = miniSerial;
        this.miniItem = miniItem;
        this.miniName = miniName;
        this.miniPrice = miniPrice;
    }

    public int getMiniSerial() {
        return miniSerial;
    }

    public String getMiniItem() {
        return miniItem;
    }

    public String getMiniName() {
        return miniName;
    }

    public String getMiniPrice() {
        return miniPrice;
    }
}
