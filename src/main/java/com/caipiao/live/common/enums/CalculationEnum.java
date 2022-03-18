package com.caipiao.live.common.enums;

public enum CalculationEnum {
      LHCZERO("正码一", 0),
	
	  LHCONE("正码二", 1),
	
	  LHCTWO("正码三", 2),
	
	  LHCTHREE("正码四", 3),
	
	 LHCFOUR("正码五", 4),
	 
	 LHCFIVE("正码六", 5),
	
	 LHCSIX("特码", 6),
	 
	 LHCSEVEN("正码", 7);

	private String name;
	private Integer code;

	private CalculationEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	  public static String getValueByName(Integer code) {
	        for (CalculationEnum calculationEnum : CalculationEnum.values()) {
	            if (code==calculationEnum.getCode()) {
	                return calculationEnum.getName();
	            }
	        }
	        return null;
	    }

	  
}
