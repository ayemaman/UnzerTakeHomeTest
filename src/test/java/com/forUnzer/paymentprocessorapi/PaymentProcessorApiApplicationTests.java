package com.forUnzer.paymentprocessorapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentProcessorApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void statusCheckDoesNotExist() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/payments/123/status"))
				.andExpect(status().isNotFound())
				.andExpect(status().reason("Payment with approval code 123 does not exist"));
	}
	@Test
	public void cancelDoesNotExist() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/123/cancel")
						.content("123"))
				.andExpect(status().isNotFound())
				.andExpect(status().reason("Payment with approval code 123 does not exist"));
	}

	@Test
	public void processPayment() throws Exception {
		MvcResult mvcResult= this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/process").content(
						"{\"cardNumber\": \"123456789\"," +
								" \"cardExpiryDate\": \"03/25\"," +
								" \"cardCvc\": \"123\"," +
								" \"amount\": \"22.33\", " +
								"\"currency\": \"eur\"}").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assert(content.contains("approvalCode"));


	}

	@Test
	public void wrongCVC() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/process").content(
				"{\"cardNumber\": \"1\"," +
						" \"cardExpiryDate\": \"03/61\"," +
						" \"cardCvc\": \"12\"," +
						" \"amount\": \"abc\", " +
						"\"currency\": \"eur\"}").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(containsString("Wrong Card Number")));

	}






}
