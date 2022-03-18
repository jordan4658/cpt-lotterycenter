package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class BetInfoDTO {

	private int betCount;

	private String betNumber;

	private BigDecimal betAmount;

	public String getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(String betNumber) {
		this.betNumber = betNumber;
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public BigDecimal getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}
}
