package com.forUnzer.paymentprocessorapi.payment.thirdpartyapiconnector;


import com.forUnzer.paymentprocessorapi.payment.Payment;
import com.forUnzer.paymentprocessorapi.payment.exceptions.CancelPaymentException;
import com.forUnzer.paymentprocessorapi.payment.exceptions.PostPaymentException;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Component
public class ThirdPartyAPIHttpRequestFetcher {
    private static final String POST_URL = "http://localhost:8500/payments/process";
    private static final String PAYMENTS_URL = "http://localhost:8500/payments/";

    public ThirdPartyAPIHttpRequestFetcher() {
    }


    /**
     * (3rd Party API URL mapping: "http://localhost:8500/payments/process")
     * @param payment
     * @return
     * @throws IOException
     * @throws PostPaymentException
     */
    public String postPayment(Payment payment) throws IOException, PostPaymentException {
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        Gson gson=new Gson();
        String jsonInputString=gson.toJson(payment);
        try(OutputStream os= con.getOutputStream()){
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input,0,input.length);
        }
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();


        }

        throw new PostPaymentException(responseCode+" "+con.getResponseMessage());

    }

    /**
     * (3rd Party API URL mapping: "http://localhost:8500/payments/{approvalCode}/cancel")
     * @param approvalCode
     * @throws IOException
     * @throws CancelPaymentException
     */
    public void cancelPayment(String approvalCode) throws IOException, CancelPaymentException {
        URL obj = new URL(PAYMENTS_URL+approvalCode+"/cancel");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK)  // success
            throw new CancelPaymentException(responseCode+" "+con.getResponseMessage());

    }

}