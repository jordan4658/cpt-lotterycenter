package com.caipiao.live.common.enums;

/**
 * 彩种Tag枚举
 * 
 */
public enum AppMianParamEnum {
	/**
	 * 期号 
	 * */
	TYPE("type","彩种"), 
	/**
	 * 期号 
	 * */
	TYPEID("typeId","彩种ID"),
	/**
	 * 期号 
	 * */
	PLAYTYPE("playType","玩法"),
	/**
	 * 玩法类别 
	 * */
	PLAYTAG("playTag","玩法类别"),
	/**
	 * 期号 
	 * */
	ISSUE("issue","期号"), 
	/**
	 * 开奖结果
	 * */
	NUMBER("number","开奖结果"), 
	/**
	 * 开奖结果，号码分开
	 * */
	SOURCENUMBER("sourceNumber","开奖结果"), 
	/**
	 * 下一期开奖期号
	 * */
	NEXTISSUE("nextIssue","下一期开奖结果"), 
	/**
	 * 下一期开奖时间
	 * */
	NEXTTIME("nextTime","下一期开奖时间"),
	/**
	 * 未开期数
	 * */
	NOOPENCOUNT("noOpenCount","未开期数"),
	/**
	 * 已开期数
	 * */
	OPENCOUNT("openCount","已开期数"),
	/**
	 * 开奖时间
	 * */
	TIME("time","开奖时间"),
	/**
	 * 生肖
	 * */
	SHENGXIAO("shengxiao","生肖"),
	/**
	 * 下期时间
	 * */
	NEXTOPENTIME("nextOpenTime","下期时间"),
	/**
	 * ID
	 * */
	ID("id","ID"),
	/**
	 * 彩票控开奖结果
	 * */
	CPKNUMBER("cpkNumber","ID"),
	/**
	 * 开彩网开奖结果
	 * */
	KCWNUMBER("kcwNumber","ID"),
	/**
	 * 理想开奖时间
	 * */
	IDEALTIME("idealTime","理想开奖时间"),
	/**
	 * 理想开奖时间
	 * */
	OPENSTATUS("openStatus","状态"),
	/**
	 * 牛牛系列
	 * */
	NIU_NIU("niuWinner","赢家"),
	/**
	 * 合值
	 * */
	HE("he","合值"),
	/**
	 * 长龙数量
	 * */
	DRAGONSUM("dragonSum","长龙长度"),
	/**
	 * 长龙数量
	 * */
	DRAGONType("dragonType","长龙类型"),
	/**
	 * 玩法
	 * */
	ODDS("odds","玩法赔率"),
	/**
	 * 玩法明细ID
	 * */
	PLAYTYPEID("playTypeId","玩法明细ID"),
	/**
	 * 玩法明细名称
	 * */
	PLAYTYPENAME("playTypeName","玩法明细名称"),
	/**
	 * 大类名称
	 * */
	COTEGORY("cotegory","大类名称"),
	/**
	 * 小类名称
	 * */
	PLAYTAGID("playTagId","小类名称"),
	/**
	 * 小类名称
	 * */
	SETTINGID("settingId","玩法ID");
	
	private String paramEnName;
	private String paramCnName;

	private AppMianParamEnum(String paramEnName, String paramCnName) {
		this.paramEnName = paramEnName;
		this.paramCnName = paramCnName;
	}

	public String getParamEnName() {
		return paramEnName;
	}

	public void setParamEnName(String paramEnName) {
		this.paramEnName = paramEnName;
	}

	public String getParamCnName() {
		return paramCnName;
	}

	public void setParamCnName(String paramCnName) {
		this.paramCnName = paramCnName;
	}

	
}
