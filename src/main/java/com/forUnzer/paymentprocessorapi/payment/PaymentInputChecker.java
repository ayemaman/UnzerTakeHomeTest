package com.forUnzer.paymentprocessorapi.payment;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
@Component
public class PaymentInputChecker {

    public PaymentInputChecker() {
    }

    public void checkPaymentIsOK(Payment payment){
        if(!checkCardNumber(payment.getCardNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong Card Number"
            );
        }

        if(!checkCvc(payment.getCardCvc())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong CVC Code"
            );
        }

        if(!checkExpirationDate(payment.getCardExpiryDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong Expiry Date"
            );
        }

        if(!checkCurrency(payment.getCurrency())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong Currency Format"
            );
        }
        if(!checkAmount(payment.getAmount())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Wrong Amount Format"
            );

        }
    }

    public boolean checkAmount(String string){
        if(string!=null){
            return string.matches("^[1-9]\\d{0,7}(\\.\\d{1,2})$");
        }
        return false;
    }
    public boolean checkCvc(String string){
        if(string!=null) {
            return string.matches("\\d{3}");
        }
        return false;
    }
    public boolean checkCardNumber(String string) {
        if (string != null) {
            return (string.matches("\\d+") && (string.length() < 20) && (string.length() > 7));
        }
        return false;
    }

    public boolean checkExpirationDate(String string) {
        if (string != null) {
            if (string.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                currentYear = currentYear % 2000;
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;  //Adding +1 since Months are indexed from 0
                int givenYear = Integer.parseInt(string.substring(3, 5));
                int givenMonth = Integer.parseInt(string.substring(0, 2));

                if (currentYear < givenYear) {    // if year is bigger than current -> check if it's more than 21 years
                    return givenYear - currentYear < 21;

                } else if (currentYear > givenYear) {  // if year is smaller than current -> check if it's in the future, but less than 21 years
                    givenYear = +100;
                    return givenYear - currentYear < 21;

                } else {  //if year is current -> check month not expired
                    return currentMonth > givenMonth;
                }
            }
            return false;
        }
        return false;

    }

    //for this example only checking on the most common Currency ISO 4217 codes. In real-life application would get the whole list from some public db.
    public boolean checkCurrency(String string){
        if (string != null) {
            return string.matches("USD|AUD|BRL|GBP|CAD|CNY|DKK|AED|EUR|HKD|INR|MYR|MXN|NZD|PHP|SGD|THB|ARS|COP|CLP|PEN|VEF");
        }
        return false;
    }
}
