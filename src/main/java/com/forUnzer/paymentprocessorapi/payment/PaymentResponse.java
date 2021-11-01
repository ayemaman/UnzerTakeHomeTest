package com.forUnzer.paymentprocessorapi.payment;

public class PaymentResponse {
    private final String approvalCode;

    public PaymentResponse(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }
}
