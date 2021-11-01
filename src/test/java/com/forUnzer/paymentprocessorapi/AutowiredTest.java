package com.forUnzer.paymentprocessorapi;

import com.forUnzer.paymentprocessorapi.payment.PaymentInputChecker;
import com.forUnzer.paymentprocessorapi.payment.PaymentService;
import com.forUnzer.paymentprocessorapi.payment.thirdpartyapiconnector.ThirdPartyAPIHttpRequestFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AutowiredTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ThirdPartyAPIHttpRequestFetcher thirdPartyAPIHttpRequestFetcher;

    @Autowired
    private PaymentInputChecker paymentInputChecker;

    @Test
    public void contextLoads() throws Exception{
        assertNotNull(paymentService);
        assertNotNull(thirdPartyAPIHttpRequestFetcher);
        assertNotNull(paymentInputChecker);
    }

}
