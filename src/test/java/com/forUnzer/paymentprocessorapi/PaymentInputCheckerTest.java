package com.forUnzer.paymentprocessorapi;

import com.forUnzer.paymentprocessorapi.payment.PaymentInputChecker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PaymentInputCheckerTest {

    @Autowired
    private PaymentInputChecker paymentInputChecker;

    @Test
    public void testAmountCheck(){
        assertTrue(paymentInputChecker.checkAmount("22.3"));
        assertTrue(paymentInputChecker.checkAmount("22"));
        assertTrue(paymentInputChecker.checkAmount("22.30"));
        assertTrue(paymentInputChecker.checkAmount("22.03"));
        assertTrue(paymentInputChecker.checkAmount("22.00"));
        assertFalse(paymentInputChecker.checkAmount(null));
        assertFalse(paymentInputChecker.checkAmount("-22.3"));
        assertFalse(paymentInputChecker.checkAmount("22.3032"));
        assertFalse(paymentInputChecker.checkAmount("22.30.231"));
        assertFalse(paymentInputChecker.checkAmount("qweqweqw"));
    }
    @Test
    public void testCvcCheck(){
        assertTrue(paymentInputChecker.checkCvc("111"));
        assertFalse(paymentInputChecker.checkCvc(null));
        assertFalse(paymentInputChecker.checkCvc("11.1"));
        assertFalse(paymentInputChecker.checkCvc("-11"));
        assertFalse(paymentInputChecker.checkCvc("22231"));
        assertFalse(paymentInputChecker.checkCvc("qweqweqw"));
    }
    @Test
    public void testCardNumberCheck(){
        assertTrue(paymentInputChecker.checkCardNumber("12345678"));
        assertTrue(paymentInputChecker.checkCardNumber("123456789012"));
        assertTrue(paymentInputChecker.checkCardNumber("12345678901234567"));
        assertFalse(paymentInputChecker.checkCardNumber(null));
        assertFalse(paymentInputChecker.checkCardNumber("11.1"));
        assertFalse(paymentInputChecker.checkCardNumber("-11"));
        assertFalse(paymentInputChecker.checkCardNumber("22231"));
        assertFalse(paymentInputChecker.checkCardNumber("qweqweqw"));
        assertFalse(paymentInputChecker.checkCardNumber("123456789012345678901234567890"));
    }
    @Test
    public void testExpirationDateCheck(){
        assertTrue(paymentInputChecker.checkExpirationDate("01/33"));
        assertFalse(paymentInputChecker.checkExpirationDate("1/22"));
        assertFalse(paymentInputChecker.checkExpirationDate(null));
        assertFalse(paymentInputChecker.checkExpirationDate("1212"));
        assertFalse(paymentInputChecker.checkExpirationDate("13/25"));
        assertFalse(paymentInputChecker.checkExpirationDate("11 11"));
        assertFalse(paymentInputChecker.checkExpirationDate("01/53"));

    }

    @Test
    public void testCurrencyCheck(){
        assertTrue(paymentInputChecker.checkCurrency("EUR"));
        assertTrue(paymentInputChecker.checkCurrency("USD"));
        assertTrue(paymentInputChecker.checkCurrency("AUD"));
        assertTrue(paymentInputChecker.checkCurrency("euro"));
        assertTrue(paymentInputChecker.checkCurrency("dollars"));
    }

}
