
# Requirements
    *Run 3rd Party API
# Couple of notes on decisions made in this project:
## Given user stories:
    1.As a User I want to create a payment so that I retrieve an approval code.
    2.As a User I want to cancel an existing payment based on a given payment id.
    3.As a User I want to search for a payment by a given payment id so that I can see the status of the payment.


## Some decisions, reasons and explanations
    ** In the provided 3rd Party API the URL mapping in the "processor.json" for payments/process 
    was different from the one provided in "api-spec.json", so I changed it from "/process" to "/payments/process".

    ** Used Spring Boot for implementation of REST API
    
    ** Used H2 in-memmory DB. At first I was thinking about using postgresql, 
    but decided to use h2 because it's self contained and easier for this given scenario.

    ** No security was implemented, since it was not specified in task and I am supposed to "Keep it simple".
    Real life application would have included secure User Login and OAuth2 authentication.

### API SPECIFICATION

--------------------------------------------------------------------
    server: http://localhost:8080
--------------------------------------------------------------------
    POST /payments/process
    Request body(required)
        
        {
            "cardNumber": "string",                 // (8-19 char) example:("1234567890123")
            "cardExpiryDate": "string",             // (MM/yy) example:("11/23")
            "cardCvc": "string",                    // 3 digits example:("323")
            "amount": "string",                     // d+.d || d+.dd   examples:("22.20", "222.00","2222.02")
            "currency": "string"                    // format Currency ISO 4217 (EUR, USD...)
        }

    Response
    {
        "approvalCode": "string"
    }
--------------------------------------------------------------------
    POST /payments/{approvalCode}/cancel
    
--------------------------------------------------------------------
   
    GET /payments/{approvalCode}/status

    Reponse
    {
        "status": "string"
    }





