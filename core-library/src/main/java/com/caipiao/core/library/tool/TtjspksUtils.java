package com.caipiao.core.library.tool;

import java.util.Date;
import java.util.List;

import com.mapper.domain.FtftjspksCountSglh;
import com.mapper.domain.FtjspksCountSgds;
import com.mapper.domain.FtjspksCountSgdx;
import com.mapper.domain.FtjspksKillNumber;
import com.mapper.domain.FtjspksLotterySg;


/**
 * 極速pk10番攤功能类
 *
 * @author lzy
 * @create 2018-07-30 17:32
 **/
public class TtjspksUtils {
	
	/**
     * 统计赛果单双次数
     *
     * @param sgs
     * @return
     */
    public static FtjspksCountSgds countSgDsJs(List<FtjspksLotterySg> sgs) {
    	if (sgs == null) {
            return null;
        }
    	
    	FtjspksCountSgds countSgds =  new FtjspksCountSgds();
    	FtjspksLotterySg sg1 = sgs.get(0);
    	countSgds.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //单，双
        for (int i = 0; i < totalIssue; i++) {
        	FtjspksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num % 2 == 1) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }
        setCountDsData(countSgds, count);
        return countSgds;
    }
    
    /**
     * 统计赛果大小次数
     *
     * @param sgs
     * @return
     */
	public static FtjspksCountSgdx countSgDxJs(List<FtjspksLotterySg> sgs) {
		if (sgs == null) {
			return null;
		}

		FtjspksCountSgdx countSgdx = new FtjspksCountSgdx();
		FtjspksLotterySg sg1 = sgs.get(0);
		countSgdx.setDate(sg1.getTime().substring(0, 10));
		int totalIssue = sgs.size();
		int[] count = new int[20]; // 大，小
		for (int i = 0; i < totalIssue; i++) {
			FtjspksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num >= 6) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDxData(countSgdx, count);
        return countSgdx;
	}
	
	/**
     * 统计赛果龙虎次数
     *
     * @param sgs
     * @return
     */
	public static FtftjspksCountSglh countSgLhJs(List<FtjspksLotterySg> sgs) {
		if (sgs == null) {
			return null;
		}

		FtftjspksCountSglh countSglh = new FtftjspksCountSglh();
		FtjspksLotterySg sg1 = sgs.get(0);
		countSglh.setDate(sg1.getTime().substring(0, 10));

		int totalIssue = sgs.size();
		int[] count = new int[10]; // 龙,虎
		for (int i = 0; i < totalIssue; i++) {
			FtjspksLotterySg sg = sgs.get(i);
			String numStr = sg.getNumber();
			String[] numStrArr = numStr.split(",");
			for (int j = 0; j < 5; j++) {
				int num1 = Integer.valueOf(numStrArr[j]);
				int num2 = Integer.valueOf(numStrArr[9 - j]);
				if (num1 > num2) {
					count[(j * 2)]++;
				} else {
					count[(j * 2 + 1)]++;
				}
			}
		}

		setCountLhData(countSglh, count);
		return countSglh;
	}
	
    /**
     * 保存極速pk10番攤的龙虎统计数据
     *
     * @param countSglh
     * @param countArr
     */
    public static void setCountLhData(FtftjspksCountSglh countSglh, int[] countArr) {
        if (countSglh != null && countArr.length >= 10) {
            countSglh.setOnel(countArr[0]);
            countSglh.setOneh(countArr[1]);
            countSglh.setTwol(countArr[2]);
            countSglh.setTwoh(countArr[3]);
            countSglh.setThreel(countArr[4]);
            countSglh.setThreeh(countArr[5]);
            countSglh.setFourl(countArr[6]);
            countSglh.setFourh(countArr[7]);
            countSglh.setFivel(countArr[8]);
            countSglh.setFiveh(countArr[9]);
        }
    }
    
    /**
     * 保存極速pk10番攤的单双统计数据
     *
     * @param countSgds
     * @param countArr
     */
    public static void setCountDsData(FtjspksCountSgds countSgds, int[] countArr) {
        if (countSgds != null && countArr.length >= 20) {
            countSgds.setOned(countArr[0]);
            countSgds.setOnes(countArr[1]);
            countSgds.setTwod(countArr[2]);
            countSgds.setTwos(countArr[3]);
            countSgds.setThreed(countArr[4]);
            countSgds.setThrees(countArr[5]);
            countSgds.setFourd(countArr[6]);
            countSgds.setFours(countArr[7]);
            countSgds.setFived(countArr[8]);
            countSgds.setFives(countArr[9]);
            countSgds.setSixd(countArr[10]);
            countSgds.setSixs(countArr[11]);
            countSgds.setSevend(countArr[12]);
            countSgds.setSevens(countArr[13]);
            countSgds.setEightd(countArr[14]);
            countSgds.setEights(countArr[15]);
            countSgds.setNightd(countArr[16]);
            countSgds.setNights(countArr[17]);
            countSgds.setTend(countArr[18]);
            countSgds.setTens(countArr[19]);
        }
    }
    
    /**
     * 保存極速pk10番攤的大小统计数据
     *
     * @param countSgdx
     * @param countArr
     */
    public static void setCountDxData(FtjspksCountSgdx countSgdx, int[] countArr) {
        if (countSgdx != null && countArr.length >= 20) {
            countSgdx.setOned(countArr[0]);
            countSgdx.setOnex(countArr[1]);
            countSgdx.setTwod(countArr[2]);
            countSgdx.setTwox(countArr[3]);
            countSgdx.setThreed(countArr[4]);
            countSgdx.setThreex(countArr[5]);
            countSgdx.setFourd(countArr[6]);
            countSgdx.setFourx(countArr[7]);
            countSgdx.setFived(countArr[8]);
            countSgdx.setFivex(countArr[9]);
            countSgdx.setSixd(countArr[10]);
            countSgdx.setSixx(countArr[11]);
            countSgdx.setSevend(countArr[12]);
            countSgdx.setSevenx(countArr[13]);
            countSgdx.setEightd(countArr[14]);
            countSgdx.setEightx(countArr[15]);
            countSgdx.setNightd(countArr[16]);
            countSgdx.setNightx(countArr[17]);
            countSgdx.setTend(countArr[18]);
            countSgdx.setTenx(countArr[19]);
        }
    }
    
    public static FtjspksKillNumber getJsKillNumber(String issue) {
    	FtjspksKillNumber nextKillNumber = new FtjspksKillNumber();
    	nextKillNumber.setIssue(issue);
        String time = DateUtils.formatDate(new Date(), DateUtils.HOURANDMINUTES);
        if (DateUtils.TIMEOVERHOUR.equals(time)) {
            time = DateUtils.TIMEZERO;
        } else {
            time = DateUtils.formatDate(DateUtils.getMinuteAfter(new Date(), 1), DateUtils.timePattern);
        }
        nextKillNumber.setTime(time);
        nextKillNumber.setOne(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setTwo(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setThere(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setFour(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setFive(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setSix(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setSeven(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setEight(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setNine(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setTen(RandomUtils.getBjpksKillNumber());
        nextKillNumber.setCreateTime(TimeHelper.date());
        return nextKillNumber;
    }

}
