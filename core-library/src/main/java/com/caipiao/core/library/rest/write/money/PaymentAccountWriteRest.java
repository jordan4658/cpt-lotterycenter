package com.caipiao.core.library.rest.write.money;

import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.PaymentAccount;
import com.mapper.domain.PaymentSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface PaymentAccountWriteRest {
    @PostMapping("/pagePaymentAccount/doEditPaymentAccount.json")
    void doEditPaymentAccount(@RequestBody PaymentAccount paymentAccount, @RequestParam("admin") String admin);

    @PostMapping("/pagePaymentAccount/addOrUpdateChannel.json")
    void addOrUpdateChannel(@RequestParam("id") Integer id, @RequestParam("type") String type, @RequestParam("sort") Integer sort, @RequestParam("admin") String admin);

    @PostMapping("/pagePaymentAccount/deleteChannelById.json")
    void deleteChannelById(@RequestParam("id") Integer id);

    @PostMapping("/pagePaymentAccount/deletePaymentAccountById.json")
    void deletePaymentAccountById(@RequestParam("id") Integer id);

    @PostMapping("/pagePaymentAccount/paySubmit.json")
    ResultInfo<Boolean> paySubmit(@RequestParam("meId") Integer meId, @RequestParam("accountId") Integer accountId, @RequestParam("amount") BigDecimal amount, @RequestParam("fuyan") Integer fuyan, @RequestParam("source") String source);

    @PostMapping("/pagePaymentAccount/doShenheOfflinePayment.json")
    void doShenheOfflinePayment(@RequestBody PaymentSummary paymentSummary);

    @PostMapping("/pagePaymentAccount/doAddHandPayment.json")
    ResultInfo<String> doAddHandPayment(@RequestBody PaymentSummary paymentSummary);
}
