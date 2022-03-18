package com.caipiao.core.library.rest.read.money;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.PaymentRequestJetpay;
import com.mapper.domain.PaymentResultJetpay;
import com.mapper.domain.PaymentSummary;

@FeignClient(name = BUSINESS_READ)
public interface PaymentRequestJetpayRest {
	
	
	@GetMapping("/money/pagefullPaymentRequest.json")
    PageResult<List<HashMap<String, Object>>> pagefullPaymentRequest(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                                                     @RequestParam("account") String account, @RequestParam("status") String status, @RequestParam("orderno") String orderno, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
                                                                     @RequestParam("paytype") String paytype, @RequestParam("mid") String mid, @RequestParam("dsf") String dsf, @RequestParam("fy") String fy);
	
	
	@GetMapping("/money/sumfullBymap.json")
	HashMap<String, Object> sumfullBymap(@RequestParam("account") String account, @RequestParam("status") String status, @RequestParam("orderno") String orderno, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
                                         @RequestParam("paytype") String paytype, @RequestParam("mid") String mid, @RequestParam("dsf") String dsf, @RequestParam("fy") String fy);
	
	
	
	@GetMapping("/money/getpaymentResult.json")
	public PaymentResultJetpay getpaymentResult(@RequestParam("orderno") String orderno);
	
	
	@GetMapping("/money/getpaymentRequest.json")
	public PaymentRequestJetpay getpaymentRequest(@RequestParam("orderno") String orderno);
	
	@GetMapping("/money/countfullbystate.json")
	public PageResult<List<HashMap<String, Object>>> countfullbystate(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("state") String state, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime);

	@GetMapping("/money/pagefullPayment.json")
	public PageResult<List<PaymentSummary>> pagefullPayment(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("uid") String uid, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime, @RequestParam("status") String status);

}
