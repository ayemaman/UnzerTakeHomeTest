package com.forUnzer.paymentprocessorapi.payment;

import javax.persistence.*;


/**
 * Since not specifically stated, most of the variables in this class are set to @Transient,
 * so that we only store Client's information that is needed.
 */
@Entity
@Table
public class Payment {
    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long id;
    @Transient
    private String cardNumber;  //cardNumber accepted size of 8-20 digits
    @Transient
    private String cardExpiryDate; //format "MM/yy"
    @Transient
    private String cardCvc; //3-digit format
    @Transient
    private String amount; // format d.dd
    @Transient
    private String currency;  //format Currency ISO 4217 codes, only accepting USD|AUD|BRL|GBP|CAD|CNY|DKK|AED|EUR|HKD|INR|MYR|MXN|NZD|PHP|SGD|THB|ARS|COP|CLP|PEN|VEF
    private String Status; // processing, canceled
    private String approvalCode;  //random string provided by 3rd Party API

    public Payment() {
    }

    public Payment(String cardNumber, String cardExpiryDate, String cardCvc, String amount, String currency) {
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCvc = cardCvc;
        this.amount = amount;
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber.replace(" ","");
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {

        this.currency = currency.toUpperCase();
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardExpiryDate='" + cardExpiryDate + '\'' +
                ", cardCvc='" + cardCvc + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", Status='" + Status + '\'' +
                ", approvalCode='" + approvalCode + '\'' +
                '}';
    }
}
