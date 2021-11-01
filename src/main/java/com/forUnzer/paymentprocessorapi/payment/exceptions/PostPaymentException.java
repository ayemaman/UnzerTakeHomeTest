package com.forUnzer.paymentprocessorapi.payment.exceptions;

public class PostPaymentException extends Exception{
    public PostPaymentException(String reason){
        System.out.println(reason);
    }
}
