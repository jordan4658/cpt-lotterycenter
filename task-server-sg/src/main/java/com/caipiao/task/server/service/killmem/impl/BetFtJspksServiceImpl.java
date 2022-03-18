package com.caipiao.task.server.service.killmem.impl;

import com.caipiao.core.library.tool.FanTanCalculationUtils;
import com.caipiao.task.server.service.killmem.BetFtJspksService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetFtJspksServiceImpl implements BetFtJspksService {


	//极速PK10番摊 返回值 0 表示输
	public final static Integer PLAY_ID_FH_ZERO = 0;
	//极速PK10番摊 返回值 1 表示赢
	public final static Integer PLAY_ID_FH_ONE = 1;
	//极速PK10番摊 返回值 2 表示 和(走水）
	public final static Integer PLAY_ID_FH_TWO = 2;
	//极速PK10番摊值 1
	private final Integer PLAY_HM_ONE = 1;
	//极速PK10番摊值 2
	private final Integer PLAY_HM_TWO = 2;
	//极速PK10番摊值 3
	private final Integer PLAY_HM_THREE = 3;
	//极速PK10番摊值 4
	private final Integer PLAY_HM_FOUR = 4;
	//极速PK10番摊玩法ID集合
	public static final List<Integer> playIdList= Lists.newArrayList(200101);



	/**
	 *  判断极速PK10番摊 
	 * 
	 * @param betNumber 投注号码
	 * @param number    开奖号
	 * @return
	 */
	public Integer isWin(String betNumber, String number) {
		// 投注号
		String[] betNumArrs = betNumber.split(",");
		Integer num = Integer.valueOf(FanTanCalculationUtils.ftjspksSaleResult(number));

		for (String betStr : betNumArrs) {
			if (betStr.contains("@")) {
				betStr = betStr.split("@")[1];
			}
			// 判断是否中奖
			if (betStr.contains("番")) {
				return isFWin(betStr, num);
			} else if (betStr.contains("念")) {
				return isNwin(betStr, num);
			} else if (betStr.contains("角")) {
				return isJwin(betStr, num);
			} else if (betStr.contains("正")) {
				return isZWin(betStr, num);
			} else if (betStr.contains("单") || betStr.contains("双")) {
				return isDsWin(betStr, num);
			}
		}
		return PLAY_ID_FH_ZERO;
	}

	/**
	 *极速PK10番摊 番 是否中奖
	 * 
     * @param betStr     投注
	 * @param num      开奖号码
	 * @return
	 */
	private Integer isFWin(String betStr, Integer num) {
		if ("番1".equals(betStr)) {
			if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("番2".equals(betStr)) {
			if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("番3".equals(betStr)) {
			if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("番4".equals(betStr)) {
			if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			}
		}
		return PLAY_ID_FH_ZERO;
	}

	/**
	 *极速PK10番摊 念 是否中奖
	 * 
	 * @param betStr     投注
	 * @param num      开奖号码
	 * @return
	 */
	private Integer isNwin(String betStr, Integer num) {
		// 判断是否中奖
		if ("1念2".equals(betStr)) {
			if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_TWO;
			}

		} else if ("1念3".equals(betStr)) {
			if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("1念4".equals(betStr)) {
			if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("2念1".equals(betStr)) {
			if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("2念3".equals(betStr)) {
			if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("2念4".equals(betStr)) {
			if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("3念1".equals(betStr)) {
			if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("3念2".equals(betStr)) {
			if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("3念4".equals(betStr)) {
			if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("4念1".equals(betStr)) {
			if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("4念2".equals(betStr)) {
			if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("4念3".equals(betStr)) {
			if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_TWO;
			}
		}
		return PLAY_ID_FH_ZERO;
	}

	/**
	 *极速PK10 角 是否中奖
	 * 
	 * @param betStr     投注
	 * @param num      开奖号码
	 * @return
	 */
	private Integer isJwin(String betStr, Integer num) {
		// 判断是否中奖
		if ("1-2角".equals(betStr)) {
			if (PLAY_HM_ONE == num || PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("2-3角".equals(betStr)) {
			if (PLAY_HM_THREE == num || PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("3-4角".equals(betStr)) {
			if (PLAY_HM_THREE == num || PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			}
		} else if ("4-2角".equals(betStr)) {
			if (PLAY_HM_TWO == num || PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			}
		}
		return PLAY_ID_FH_ZERO;
	}

	/**
	 *极速PK10番摊 正 是否中奖
	 * 
	 * @param betStr     投注
	 * @param num      开奖号码
	 * @return
	 */
	private Integer isZWin(String betStr, Integer num) {
		// 判断是否中奖
		if ("正1".equals(betStr)) {
			if (PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_TWO == num || PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_TWO;
			}

		} else if ("正2".equals(betStr)) {
			if (PLAY_HM_TWO == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_ONE == num || PLAY_HM_THREE == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("正3".equals(betStr)) {
			if (PLAY_HM_THREE == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_TWO == num || PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_TWO;
			}
		} else if ("正4".equals(betStr)) {
			if (PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			} else if (PLAY_HM_ONE == num || PLAY_HM_THREE == num) {
				return PLAY_ID_FH_TWO;
			}
		}
		return PLAY_ID_FH_ZERO;
	}

	/**
	   *极速PK10番摊 单双 是否中奖
	 * 
	 * @param betStr     投注
	 * @param num      开奖号码
	 * @return
	 */
	private Integer isDsWin(String betStr, Integer num) {
		// 判断是否中奖
		if ("单".equals(betStr)) {
			if (PLAY_HM_THREE == num || PLAY_HM_ONE == num) {
				return PLAY_ID_FH_ONE;
			}

		} else if ("双".equals(betStr)) {
			if (PLAY_HM_TWO == num || PLAY_HM_FOUR == num) {
				return PLAY_ID_FH_ONE;
			}
		}
		return PLAY_ID_FH_ZERO;
	}
}
