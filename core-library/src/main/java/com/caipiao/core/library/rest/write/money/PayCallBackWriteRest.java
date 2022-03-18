package com.caipiao.core.library.rest.write.money;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;





import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mapper.domain.PaymentResultJetpay;

@FeignClient(name = BUSINESS_SERVER)
public interface PayCallBackWriteRest {

	@PostMapping("/money/callBack.json")
	void callBack(@RequestBody PaymentResultJetpay paymentResultJetpay);
}
