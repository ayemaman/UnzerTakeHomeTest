package com.forUnzer.paymentprocessorapi.payment;


import com.forUnzer.paymentprocessorapi.payment.exceptions.CancelPaymentException;
import com.forUnzer.paymentprocessorapi.payment.exceptions.PostPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "payments")
public class PaymentController {


    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;
    }


    @PostMapping(path = "process")
    public ResponseEntity<PaymentResponse> registerNewPayment(@RequestBody Payment payment) throws PostPaymentException, IOException {
        String approvalCode =  paymentService.addNewPayment(payment);
        return ResponseEntity.ok(new PaymentResponse(approvalCode));

    }


    @PostMapping(path = "{approvalCode}/cancel")
    public void cancelPayment(@PathVariable("approvalCode") String approvalCode) throws CancelPaymentException, IOException {
        paymentService.cancelPayment(approvalCode);
    }

    @GetMapping(path ="{approvalCode}/status")
    public String getPaymentStatus(@PathVariable("approvalCode") String approvalCode){
        return paymentService.getPaymentStatus(approvalCode);

    }


}
