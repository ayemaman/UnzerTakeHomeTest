package com.forUnzer.paymentprocessorapi.payment.exceptions;

public class CancelPaymentException extends Exception {
    public CancelPaymentException(String reason){
        System.out.println(reason);
    }
}
