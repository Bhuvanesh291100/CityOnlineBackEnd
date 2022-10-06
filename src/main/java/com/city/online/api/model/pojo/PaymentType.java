package com.city.online.api.model.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentType implements Serializable {
    boolean cardPayment;
    boolean upiPayment;
    boolean netBanking;
    boolean cashOnDelivery;
    boolean buyNowPayLater;
}
