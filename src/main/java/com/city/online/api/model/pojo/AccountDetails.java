package com.city.online.api.model.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDetails implements Serializable {
    String bankName;
    String bankIfscCode;
    String bankAccountNumber;
    String upiId;
    String panNumber;
    String gstNumber;
}
