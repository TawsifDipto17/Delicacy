package com.delicacy.delicacy;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

public class OrderDTO extends ArrayList<OrderDTO> {
    private String orderId;

    private String customerName;


    private String customerEmail;

    private String orderDetails;

    private String deliveryStatus;

    private String paymentStatus;



    public OrderDTO (String id ,String name,String email, String details ,String state, String pay) {
        orderId=id;
        customerName=name;
        customerEmail=email;
        orderDetails=details;
        deliveryStatus=state;
        paymentStatus=pay;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId()
    {

        return orderId;
    }

    public void setOrderId(String id)
    {
        orderId=id;
    }


    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String name) {
        customerName = name;
    }



    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public void setCustomerEmail(String email){customerEmail = email ;}

    public String getOrderDetails()
    {
        return orderDetails;
    }

    public void setOrderDetails(String details)
    {
        orderDetails = details;
    }

    public String getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String state)
    {
        deliveryStatus=state;
    }



    @Override
    public Stream<OrderDTO> stream() {
        return null;
    }
}
