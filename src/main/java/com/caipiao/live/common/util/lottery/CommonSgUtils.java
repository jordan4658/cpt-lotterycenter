package com.caipiao.live.common.util.lottery;


import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;

/**
 * 彩种赛果 工具类 公用
 *
 * @author sam
 * @create 2019-11-21 17:32
 **/
public class CommonSgUtils {
    //根据彩种id 得到赛果样式数据
    public static String getSgType(String lotteryId) {
        String sgType = "";
        if (lotteryId.equals(CaipiaoTypeEnum.CQSSC.getTagType())) {
            sgType = "5,6,4,6,8";
        } else if (lotteryId.equals(CaipiaoTypeEnum.XJSSC.getTagType())) {
            sgType = "7,5,2,1,8";
        } else if (lotteryId.equals(CaipiaoTypeEnum.TJSSC.getTagType())) {
            sgType = "8,0,2,6,7";
        } else if (lotteryId.equals(CaipiaoTypeEnum.TENSSC.getTagType())) {
            sgType = "3,5,0,1,4";
        }
//        else if (lotteryId.equals(CaipiaoTypeEnum.FIVESSC.getTagType()) || lotteryId.equals(CaipiaoTypeEnum.KLNIU.getTagType())) {
//            sgType = "2,6,5,0,1";
//        }
        else if (lotteryId.equals(CaipiaoTypeEnum.JSSSC.getTagType())) {
            sgType = "3,0,3,7,7";
        } else if (lotteryId.equals(CaipiaoTypeEnum.LHC.getTagType())) {
            sgType = "33,21,05,20,16,38,36";
        } else if (lotteryId.equals(CaipiaoTypeEnum.ONELHC.getTagType())) {
            sgType = "33,21,05,20,16,38,36";
        } else if (lotteryId.equals(CaipiaoTypeEnum.FIVELHC.getTagType())) {
            sgType = "33,21,05,20,16,38,36";
        } else if (lotteryId.equals(CaipiaoTypeEnum.AMLHC.getTagType())) {
            sgType = "33,21,05,20,16,38,36";
        } else if (lotteryId.equals(CaipiaoTypeEnum.BJPKS.getTagType())) {
            sgType = "08,07,09,01,02,10,05,06,04,03";
        } else if (lotteryId.equals(CaipiaoTypeEnum.TENPKS.getTagType())) {
            sgType = "08,07,09,01,02,10,05,06,04,03";
        } else if (lotteryId.equals(CaipiaoTypeEnum.FIVEPKS.getTagType())) {
            sgType = "08,07,09,01,02,10,05,06,04,03";
        }
//        else if (lotteryId.equals(CaipiaoTypeEnum.JSPKS.getTagType()) || lotteryId.equals(CaipiaoTypeEnum.JSNIU.getTagType())) {
//            sgType = "08,07,09,01,02,10,05,06,04,03";
//        }
        else if (lotteryId.equals(CaipiaoTypeEnum.XYFEIT.getTagType())) {
            sgType = "05,04,02,08,07,01,09,06,10,03";
        } else if (lotteryId.equals(CaipiaoTypeEnum.PCDAND.getTagType())) {
            sgType = "6,0,1";
        } else if (lotteryId.equals(CaipiaoTypeEnum.TXFFC.getTagType())) {
            sgType = "1,7,0,0,4";
        }
//        else if (lotteryId.equals(CaipiaoTypeEnum.DLT.getTagType())) {
//            sgType = "08,22,23,33,34,04,06";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.TCPLW.getTagType())) {
//            sgType = "2,1,1,5,5";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.TC7XC.getTagType())) {
//            sgType = "2,6,0,2,0,8,9";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.FCSSQ.getTagType())) {
//            sgType = "09,12,21,27,29,30,05";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.FC3D.getTagType())) {
//            sgType = "6,0,8";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.FC7LC.getTagType())) {
//            sgType = "03,04,05,15,20,24,28,18";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.AZNIU.getTagType()) || lotteryId.equals(CaipiaoTypeEnum.AUSPKS.getTagType())) {
//            sgType = "7,3,1,9,8,2,10,5,4,6";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKFT.getTagType())) {
//            sgType = "05,02,04,10,09,06,08,01,07,03";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.XYFTFT.getTagType())) {
//            sgType = "05,03,10,06,08,02,01,04,09,07";
//        } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSCFT.getTagType())) {
//            sgType = "0,4,3,1,6";
//        }
        else if (lotteryId.equals(CaipiaoTypeEnum.AUSACT.getTagType())) {
            sgType = "16,69,62,30,54,55,26,71,45,68,38,76,7,43,37,24,60,44,29,61";
        } else if (lotteryId.equals(CaipiaoTypeEnum.AUSSSC.getTagType())) {
            sgType = "3,3,1,9,0";
        } else if (lotteryId.equals(CaipiaoTypeEnum.XJPLHC.getTagType())) {
            sgType = "33,21,05,20,16,38,36";
        } else if (lotteryId.equals(CaipiaoTypeEnum.DZPCDAND.getTagType())) {
            sgType = "1,5,2";
        } else if (lotteryId.equals(CaipiaoTypeEnum.DZXYFEIT.getTagType())) {
            sgType = "05,04,01,06,10,03,09,08,02,07";
        } else if (lotteryId.equals(CaipiaoTypeEnum.DZKS.getTagType())) {
            sgType = "2,6,6";
        } else if (lotteryId.equals(CaipiaoTypeEnum.AZKS.getTagType())) {
            sgType = "2,6,6";
        }
        return sgType;
    }

}
