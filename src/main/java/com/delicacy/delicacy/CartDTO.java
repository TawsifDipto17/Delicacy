package com.delicacy.delicacy;

public class CartDTO {
    private int colserial;
    private String colITEM, colname, colprice;

    public int getColserial() {
        return colserial;
    }

    public String getColITEM() {return colITEM;}

    public String getColname() {
        return colname;
    }

    public String getColprice() {
        return colprice;
    }

    public CartDTO(int colserial, String icode, String colname, String colprice) {
        this.colserial = colserial;
        colITEM = icode;
        this.colname = colname;
        this.colprice = colprice;
    }
}
