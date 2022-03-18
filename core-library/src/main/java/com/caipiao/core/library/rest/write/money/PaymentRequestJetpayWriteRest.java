package com.caipiao.core.library.rest.write.money;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface PaymentRequestJetpayWriteRest {
	
	@PostMapping("/money/paymentRequestbyMemberj.json")
	String paymentRequestbyMemberj(@RequestParam("mid") Integer mid, @RequestParam("payType") String payType, @RequestParam("amount") BigDecimal amount, @RequestParam("cardNumber") String cardNumber, @RequestParam("source") String source);

	@PostMapping("/money/paymentResultbyMember.json")
	String paymentResultbyMember(@RequestParam("xml") String xml);
	
	@PostMapping("/money/paymentRequestbyMemberr.json")
	String paymentRequestbyMemberr(@RequestParam("mid") Integer mid, @RequestParam("payType") String payType, @RequestParam("amount") BigDecimal amount, @RequestParam("cardNumber") String cardNumber, @RequestParam("source") String source);

	@PostMapping("/money/paymentRequestbyMemberrx.json")
	String paymentRequestbyMemberrx(@RequestParam("map") String map);
	
	@PostMapping("/money/bufaFirstRecharge.json")
	String bufaFirstRecharge(@RequestParam("orderno") String orderno);
	
	@PostMapping("/money/payWayRequestbyMember.json")
	String payWayRequestbyMember(@RequestParam("mid") Integer mid, @RequestParam("payType") String payType, @RequestParam("amount") BigDecimal amount, @RequestParam("cardNumber") String cardNumber, @RequestParam("source") String source);


}
