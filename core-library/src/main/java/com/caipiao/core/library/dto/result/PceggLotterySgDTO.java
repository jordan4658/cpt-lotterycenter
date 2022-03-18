package com.caipiao.core.library.dto.result;

import com.mapper.domain.PceggLotterySg;

/**
 * @author ShaoMing
 * @datetime 2018/7/27 9:12
 */
public class PceggLotterySgDTO extends PceggLotterySg {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7001880979262545455L;

	private Integer sum; // 和值

    private String bigOrSmall; // 大小

    private String singleOrDouble; // 单双
    
    private String numberstr;
    
    

    public String getNumberstr() {
		return numberstr;
	}

	public PceggLotterySgDTO() {
    }

    public PceggLotterySgDTO(Integer sum, String bigOrSmall, String singleOrDouble) {
        this.sum = sum;
        this.bigOrSmall = bigOrSmall;
        this.singleOrDouble = singleOrDouble;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getBigOrSmall() {
        return bigOrSmall;
    }

    public void setBigOrSmall(String bigOrSmall) {
        this.bigOrSmall = bigOrSmall;
    }
    
    public void setNumberstr(String numberstr) {
        this.numberstr = numberstr;
    }

    public String getSingleOrDouble() {
        return singleOrDouble;
    }

    public void setSingleOrDouble(String singleOrDouble) {
        this.singleOrDouble = singleOrDouble;
    }
}
