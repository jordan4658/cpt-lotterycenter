package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.dto.money.HandPaymentDTO;
import com.caipiao.core.library.dto.money.OfflinePaymentDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.money.HandPaymentVO;
import com.caipiao.core.library.vo.money.OfflinePaymentVO;
import com.caipiao.core.library.vo.money.PaymentAccountVO;
import com.mapper.domain.PaymentAccount;
import com.mapper.domain.PaymentAccountChannel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface PaymentAccountRest {
	/**
	 * 银行入款充值通道
	 */
	@RequestMapping("/paymentAccount/getChannel.json")
	List<PaymentAccountChannel> getChannel();

	@RequestMapping("/paymentAccount/pagePaymentAccount.json")
	PageResult<List<PaymentAccount>> pagePaymentAccount(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name, @RequestParam("account") String account, @RequestParam("onOff") Integer onOff, @RequestParam("channelId") Integer channelId);

	@RequestMapping("/paymentAccount/getPaymentAccountById.json")
	PaymentAccount getPaymentAccountById(@RequestParam("id") Integer id);

	@RequestMapping("/paymentAccount/toViewChannel.json")
	PageResult<List<PaymentAccountChannel>> toViewChannel(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

	@RequestMapping("/paymentAccount/findPaymentAccountChannelById.json")
	PaymentAccountChannel findPaymentAccountChannelById(@RequestParam("id") Integer id);

	@RequestMapping("/paymentAccount/getPaymentAccountByChannelId.json")
	ResultInfo<PaymentAccountVO> getPaymentAccountByChannelId(@RequestParam("channelId") Integer channelId);

	@RequestMapping("/paymentAccount/pageOfflinePayment.json")
	PageResult<List<OfflinePaymentVO>> pageOfflinePayment(@RequestBody(required = false) OfflinePaymentDTO offlinePaymentDTO);

	@RequestMapping("/paymentAccount/getSkAccountList.json")
	List<PaymentAccountChannel> getSkAccountList();

	@RequestMapping("/paymentAccount/getOfflinePaymentById.json")
	OfflinePaymentVO getOfflinePaymentById(@RequestParam("id") Integer id);

	@RequestMapping("/paymentAccount/pageHandPayment.json")
	PageResult<List<HandPaymentVO>> pageHandPayment(@RequestBody HandPaymentDTO handPaymentDTO);

	@RequestMapping("/paymentAccount/pageSummaryPayment.json")
    PageResult<List<HandPaymentVO>> pageSummaryPayment(@RequestBody OfflinePaymentDTO offlinePaymentDTO);

	@RequestMapping("/paymentAccount/calcMoney.json")
    Map<String, BigDecimal> calcMoney(@RequestBody OfflinePaymentDTO offlinePaymentDTO);

	@RequestMapping("/paymentAccount/selOfflinePaymentNum.json")
    Map<String, Integer> selOfflinePaymentNum();
}
