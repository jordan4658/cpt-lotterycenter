package com.caipiao.task.server.util;

import java.util.ArrayList;
import java.util.List;

public  class AusPksDto{
	private Boolean finished;
	private List<Integer> numbers;
	private List<Integer> finishedNumbers;
	
	public AusPksDto() {
		finished = false;
		numbers = new ArrayList<Integer>();
		finishedNumbers = new ArrayList<Integer>();
	}
	
	public List<Integer> getFinishedNumbers() {
		return finishedNumbers;
	}
	public void setFinishedNumbers(List<Integer> finishedNumbers) {
		this.finishedNumbers = finishedNumbers;
	}
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
}

