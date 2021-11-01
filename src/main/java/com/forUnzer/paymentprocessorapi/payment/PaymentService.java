package com.forUnzer.paymentprocessorapi.payment;

import com.forUnzer.paymentprocessorapi.payment.exceptions.CancelPaymentException;
import com.forUnzer.paymentprocessorapi.payment.exceptions.PostPaymentException;
import com.forUnzer.paymentprocessorapi.payment.thirdpartyapiconnector.ThirdPartyAPIHttpRequestFetcher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ThirdPartyAPIHttpRequestFetcher httpRequestFetcher;
    private final PaymentInputChecker paymentInputChecker;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ThirdPartyAPIHttpRequestFetcher httpRequestFetcher, PaymentInputChecker paymentInputChecker) {
        this.paymentRepository = paymentRepository;
        this.httpRequestFetcher=httpRequestFetcher;
        this.paymentInputChecker=paymentInputChecker;
    }


    private Payment getPaymentByApprovalCode(String approvalCode){
        Optional<Payment> paymentOptional= paymentRepository.findByApprovalCode(approvalCode);
        if(paymentOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Payment with approval code "+approvalCode+" does not exist"
            );
        }
        return paymentOptional.get();
    }

    public String getPaymentStatus(String approvalCode){
        Payment payment=getPaymentByApprovalCode(approvalCode);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("status",payment.getStatus());
        return jsonObject.toString();
    }

    /**
     * Function first validates provided input (if not needed -> can be commented)
     * Function sends POST request to 3rd Party API for payment approval and saves payment to local DB
     * (assumes that approval code will always be unique from 3rd Party API)
     *
     * @param payment
     * @return JsonObject with payments approval code
     * @throws PostPaymentException
     * @throws IOException
     */

    public String addNewPayment(Payment payment) throws PostPaymentException, IOException {
        paymentInputChecker.checkPaymentIsOK(payment); //if validation of input is not needed for payment entries, then comment this line
        String jsonString = httpRequestFetcher.postPayment(payment);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        String approvalCode = jsonObject.get("approvalCode").toString().replace("\"", "");
        payment.setApprovalCode(approvalCode);
        String processingStatus = "processing";
        payment.setStatus(processingStatus);
        paymentRepository.save(payment);
        return jsonObject.toString();

    }

    /**
     * Checks if such approval code exists in DB, then cancels it using 3rd Party API and updates status in local DB
     * @param approvalCode
     * @throws CancelPaymentException
     * @throws IOException
     */
    @Transactional
    public void cancelPayment(String approvalCode) throws CancelPaymentException, IOException {
        Payment payment=paymentRepository.findByApprovalCode(approvalCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment with approval code "+approvalCode+" does not exist"));
        if(payment.getStatus().equals("processing")) {
            httpRequestFetcher.cancelPayment(approvalCode);
            payment.setStatus("canceled");

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment already canceled");
        }


}

}
