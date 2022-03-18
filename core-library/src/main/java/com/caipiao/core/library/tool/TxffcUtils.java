package com.caipiao.core.library.tool;

import com.caipiao.core.library.constant.LotteryInformationType;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.rule.CQSSCPlayRule;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.TxffcCountSgdxds;
import com.mapper.domain.TxffcLotterySg;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 重庆时时彩功能类
 *
 * @author lzy
 * @create 2018-07-28 11:46
 **/
public class TxffcUtils {

    /**
     * 今日统计
     * @param sgList
     * @return
     */
    public static Map<String, Object> todayCount(List<TxffcLotterySg> sgList) {
        if (sgList == null || sgList.size() == 0) {
            return null;
        }

        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        int[] arr3 = new int[10];
        int[] arr4 = new int[10];
        int[] arr5 = new int[10];

        int[] total = new int[10];
        int[] sanxing = new int[3]; //组六,豹子,组三
        int[] erxing = new int[2]; //对子,连号

        int totalCount = 0;
        for (TxffcLotterySg sg : sgList) {
            Integer wan = sg.getWan();
            if (wan == null) {
                continue;
            }
            totalCount ++;
            Integer qian = sg.getQian();
            Integer bai = sg.getBai();
            Integer shi = sg.getShi();
            Integer ge = sg.getGe();
            arr1[wan] += 1;
            arr2[qian] += 1;
            arr3[bai] += 1;
            arr4[shi] += 1;
            arr5[ge] += 1;
            total[wan] += 1;
            total[qian] += 1;
            total[bai] += 1;
            total[shi] += 1;
            total[ge] += 1;
            if(bai.equals(shi) && shi.equals(ge)) {
                sanxing[1] += 1;
            } else if (bai.equals(shi) || shi.equals(ge) || bai.equals(ge)) {
                sanxing[2] += 1;
            } else {
                sanxing[0] += 1;
            }
            if (shi.equals(ge)) {
                erxing[0] += 1;
            } else if (shi - ge == 1 || ge - shi == 1) {
                erxing[1] += 1;
            }
        }

        Map<String, Object> result = new HashMap<>();
        ArrayList<Map<String, Object>> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i ++) {
            Map<String, Object> mapList = new HashMap<>();
            ArrayList<Integer> list1 = new ArrayList<>(10);
            list1.add(totalCount - arr1[i]);
            list1.add(totalCount - arr2[i]);
            list1.add(totalCount - arr3[i]);
            list1.add(totalCount - arr4[i]);
            list1.add(totalCount - arr5[i]);
            ArrayList<Integer> list2 = new ArrayList<>(10);
            list2.add(arr1[i]);
            list2.add(arr2[i]);
            list2.add(arr3[i]);
            list2.add(arr4[i]);
            list2.add(arr5[i]);
            mapList.put("num", i);
            mapList.put("noOpen", list1); //号码未开次数
            mapList.put("open", list2); //号码已开次数
            list.add(mapList);
        }
        result.put("list", list);
        result.put("numTotal", ArrayUtils.toList(total)); //号码出现次数统计
        result.put("sanxing", ArrayUtils.toList(sanxing));
        result.put("erxing", ArrayUtils.toList(erxing));

        return result;
    }

    /**
     * 历史开奖之开奖
     * @param sgList 开奖结果列表
     * @return
     */
    public static List<Map<String, Object>> lishiKaiJiang(List<TxffcLotterySg> sgList) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return result;
        }
        List<String> sg;
        Map<String, Object> map;
        for (TxffcLotterySg txffcSg : sgList) {
            sg = new ArrayList<>();
            map = new HashMap<>();
            map.put("issue", txffcSg.getIssue().substring(9));
            sg.add(txffcSg.getWan() == null ? "" : txffcSg.getWan() + "," + txffcSg.getQian() + "," + txffcSg.getBai() + "," + txffcSg.getShi() + "," + txffcSg.getGe());
            sg.add(CQSSCPlayRule.daxiaoDanShuang(txffcSg.getShi()));
            sg.add(CQSSCPlayRule.daxiaoDanShuang(txffcSg.getGe()));
            sg.add(CQSSCPlayRule.houSan(txffcSg.getBai(), txffcSg.getShi(), txffcSg.getGe()));
            map.put("list", sg);
            result.add(map);
        }

        return result;
    }

    /**
     * 历史开奖
     * @param sgList
     * @return
     */
    public static List<Map<String, Object>> lishiSg(List<TxffcLotterySg> sgList) {
        if (sgList == null || sgList.size() == 0) {
            return new ArrayList<>(0);
        }
        int totalIssue = sgList.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TxffcLotterySg txffcSg = sgList.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", txffcSg.getIssue());
            map.put("price", txffcSg.getPrice());
            map.put("time", txffcSg.getTime());
//            map.put("number", new StringBuffer().append(txffcSg.getWan()).append(txffcSg.getQian()).append(txffcSg.getBai())
//                    .append(txffcSg.getShi()).append(txffcSg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(txffcSg.getWan()).append(",").append(txffcSg.getQian()).append(",").append(txffcSg.getBai()).append(",")
                    .append(txffcSg.getShi()).append(",").append(txffcSg.getGe()).toString());
            //map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(txffcSg.getWan(), txffcSg.getQian(), txffcSg.getBai(), txffcSg.getShi(), txffcSg.getGe()));
            map.put("he", txffcSg.getWan() + txffcSg.getQian() + txffcSg.getBai() + txffcSg.getShi() + txffcSg.getGe());
            getIssueSumAndAllNumber(txffcSg,map);
            result.add(map);
        }
        return result;
    }

    /**
     * 历史开奖 供第三方
     * @param sgList
     * @return
     */
    public static List<Map<String, Object>> thirdLishiSg(List<TxffcLotterySg> sgList) {
        if (sgList == null || sgList.size() == 0) {
            return new ArrayList<>(0);
        }
        int totalIssue = sgList.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TxffcLotterySg txffcSg = sgList.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", txffcSg.getIssue());
            map.put("price", txffcSg.getPrice());
            map.put("time", txffcSg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(txffcSg.getIdealTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime()/1000);
            map.put("number", new StringBuffer().append(txffcSg.getWan()).append(",").append(txffcSg.getQian()).append(",").append(txffcSg.getBai()).append(",")
                    .append(txffcSg.getShi()).append(",").append(txffcSg.getGe()).toString());
            result.add(map);
        }
        return result;
    }


    public static void getIssueSumAndAllNumber(TxffcLotterySg txffcLotterySg, Map<String, Object> result) {
        Integer wan = txffcLotterySg.getWan();
        Integer qian = txffcLotterySg.getQian();
        Integer bai = txffcLotterySg.getBai();
        Integer shi = txffcLotterySg.getShi();
        Integer ge = txffcLotterySg.getGe();
        // 计算开奖号码合值
        Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);

        // 组织开奖号码
        String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);

        //计算和值大小单双，前三，中三，后三，斗牛，前中后
        String BigOrSmall = "小";
        String danOrShuang = "单";
        String qianSan = "";
        if(sumInteger >= 23){
            BigOrSmall = "大";
        }
        if(sumInteger%2==0){
            danOrShuang = "双";
        }
        int numQian[] = {wan,qian,bai};
        int numZhong[] = {qian,bai,shi};
        int numHou[] = {bai,shi,ge};
        String qianValue = getQzhValue(numQian);
        String zhongValue = getQzhValue(numZhong);
        String houValue = getQzhValue(numHou);
        String douniu = isWinByDN(allNumberString);
        result.put("calMessage", sumInteger+","+BigOrSmall+","+danOrShuang+","+qianValue+","+zhongValue+","+houValue+","+douniu);
    }

    //获取前中后 对应的值
    public static String getQzhValue(int num[]){
        String s="";
        int[] numold=	Arrays.copyOf(num,3);
        Arrays.sort(num);
        int[] ary1 = {8,9,0};
        int[] ary2 = {9,0,1};
        if(num[0]==(num[1])&&num[1]==(num[2])){
            s="豹子";

        }else if((num[2]-num[1]==num[1]-num[0]&&num[1]-num[0]==1)||Arrays.equals(numold, ary1)||Arrays.equals(numold, ary2)){
            s="顺子";
        }else if(num[2]==num[1]||num[1]==num[0]){
            s="对子";
        }else if(num[2]-num[1]==1||num[1]-num[0]==1||(num[2]==9&&num[0]==0)){
            s="半顺";
        }else{
            s="杂六";
        }
        return s;
    }

    public static String isWinByDN(String number) {
        String[] numStr = number.split(",");
        int[] num=new int[5];
        for (int i = 0; i < numStr.length; i++) {
            num[i]=Integer.parseInt(numStr[i]);
        }
        int sum=0;
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num.length; j++) {
                if(i==j){
                    continue;
                }
                for (int k = 0; k < num.length; k++) {
                    if(i==k||j==k){
                        continue;
                    }
                    sum=num[i]+num[j]+num[k];
                    int count=0;
                    String isniu="";
                    if(sum==0||sum%10==0){
                        for (int x : num) {
                            count+=x;
                        }
                        int niu=	(count-sum)%10;
                        switch (niu) {
                            case 0:
                                isniu="牛牛";
                                break;
                            case 1:
                                isniu="牛一";
                                break;
                            case 2:
                                isniu="牛二";
                                break;
                            case 3:
                                isniu="牛三";
                                break;
                            case 4:
                                isniu="牛四";
                                break;
                            case 5:
                                isniu="牛五";
                                break;
                            case 6:
                                isniu="牛六";
                                break;
                            case 7:
                                isniu="牛七";
                                break;
                            case 8:
                                isniu="牛八";
                                break;
                            case 9:
                                isniu="牛九";
                                break;
                            default:
                                break;
                        }
                        return isniu;
                    }
                }
            }
        }
        return "无牛";
    }

    /**
     * 期次详情
     * @param sg
     * @return
     */
    public static Map<String, Object> sgDetails(TxffcLotterySg sg) {
        if (sg == null) {
            return null;
        }
        Integer wan = sg.getWan();
        Integer qian = sg.getQian();
        Integer bai = sg.getBai();
        Integer shi = sg.getShi();
        Integer ge = sg.getGe();
        String wuxing = wan + "," + qian + "," + bai + "," + shi + "," +ge;
        String wuxingdxds = CQSSCPlayRule.DAXIAO_DANSHUANG[wan] + "," +  CQSSCPlayRule.DAXIAO_DANSHUANG[qian] + "," + CQSSCPlayRule.DAXIAO_DANSHUANG[bai] + "," +  CQSSCPlayRule.DAXIAO_DANSHUANG[shi] + "," + CQSSCPlayRule.DAXIAO_DANSHUANG[ge];
        int wuxingzh = wan + qian + bai + shi + ge;
        Map<String, Object> map = new HashMap<>();
        map.put("issue", sg.getIssue());
        map.put("number", "" + wan + qian +bai + shi + ge);
        map.put("he", wan + qian +bai + shi + ge);
        map.put("wuxing", wuxing);
        map.put("qiansi", wuxing.substring(0,7));
        map.put("housi", wuxing.substring(2));
        map.put("qiansan", wuxing.substring(0,5));
        map.put("zhongsan", wuxing.substring(2,7));
        map.put("housan", wuxing.substring(4));
        map.put("qianer", wuxing.substring(0,3));
        map.put("houer", wuxing.substring(6));
        map.put("wuxingdxds", wuxingdxds);
        String wuxingzhdx = wuxingzh > 22 ? "大" : "小";
        map.put("wuxingzhdx", wuxingzhdx);
        String wuxingzhdxds = wuxingzhdx + (wuxingzh % 2 == 1 ? "单" : "双");
        map.put("wuxingzhdxds", wuxingzhdxds);
        map.put("qiansidxds", wuxingdxds.substring(0,11));
        map.put("housidxds", wuxingdxds.substring(3));
        map.put("qiansandxds", wuxingdxds.substring(0,8));
        map.put("housandxds", wuxingdxds.substring(6));
        map.put("qianerdxds", wuxingdxds.substring(0,5));
        map.put("houerdxds", wuxingdxds.substring(9));
        return map;
    }

    /**
     * 历史开奖之冷热
     * @param txffcSgs
     * @return
     */
    public static List<MapListVO> lishiLengRe(List<TxffcLotterySg> txffcSgs, String type ) {
        if (txffcSgs == null || txffcSgs.size() == 0) {
            return new ArrayList<>(0);
        }
        int totalIssue = txffcSgs.size();
        ArrayList<MapListVO> result = new ArrayList<>();
        int[] count30 = new int[10];
        int[] count50 = new int[10];
        int[] count100 = new int[10];
        Integer[] countNo = new Integer[10];

        if (LotteryInformationType.CQSSC_LSKJ_LRT_TWO.equals(type)) {
            // 二星的统计
            for (int i = 0; i < totalIssue; i++) {
                TxffcLotterySg txffcSg = txffcSgs.get(i);
                Integer shi = txffcSg.getShi();
                Integer ge = txffcSg.getGe();
                if (i < 30) {
                    count30[shi] += 1;
                    count30[ge] += 1;
                } else if (i < 50) {
                    count50[shi] += 1;
                    count50[ge] += 1;
                } else if (i < 100) {
                    count100[shi] += 1;
                    count100[ge] += 1;
                } else {
                    break;
                }
                if (countNo[shi] == null) {
                    countNo[shi] = i;
                }
                if (countNo[ge] == null) {
                    countNo[ge] = i;
                }
            }

        } else if (LotteryInformationType.CQSSC_LSKJ_LR_THERE.equals(type)) {
            // 三星的统计
            for (int i = 0; i < totalIssue; i++) {
                TxffcLotterySg txffcSg = txffcSgs.get(i);
                Integer bai = txffcSg.getBai();
                Integer shi = txffcSg.getShi();
                Integer ge = txffcSg.getGe();
                if (i < 30) {
                    count30[bai] += 1;
                    count30[shi] += 1;
                    count30[ge] += 1;
                } else if (i < 50) {
                    count50[bai] += 1;
                    count50[shi] += 1;
                    count50[ge] += 1;
                } else if (i < 100) {
                    count100[bai] += 1;
                    count100[shi] += 1;
                    count100[ge] += 1;
                } else {
                    break;
                }
                if (countNo[bai] == null) {
                    countNo[bai] = i;
                }
                if (countNo[shi] == null) {
                    countNo[shi] = i;
                }
                if (countNo[ge] == null) {
                    countNo[ge] = i;
                }
            }
        } else {
            // 一星的统计
            for (int i = 0; i < totalIssue; i++) {
                TxffcLotterySg txffcSg = txffcSgs.get(i);
                Integer ge = txffcSg.getGe();
                if (i < 30) {
                    count30[ge] += 1;
                } else if (i < 50) {
                    count50[ge] += 1;
                } else if (i < 100) {
                    count100[ge] += 1;
                } else {
                    break;
                }
                if (countNo[ge] == null) {
                    countNo[ge] = i;
                }
            }

        }
        for (int j = 0; j < 10; j++) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(count30[j]);
            list.add(count30[j] + count50[j]);
            list.add(count30[j] + count50[j] + count100[j]);
            list.add(countNo[j] == null ? totalIssue : countNo[j]);
            result.add(new MapListVO("" + j, list));
        }
        return result;
    }


    /**
     * 今日统计【统计0-9号码第一位到第五位出现的次数、大小单双第一位到第五位出现的次数】
     * @param sgList
     * @return
     */
    public static Map<String,Object>  todayCount1(List<TxffcLotterySg> sgList){
        if(sgList==null || sgList.size()==0){
            return null;
        }

        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        int[] arr3 = new int[10];
        int[] arr4 = new int[10];
        int[] arr5 = new int[10];

        int[] big=new int[5];
        int[] small=new int[5];
        int[] single=new int[5];
        int[] two=new int[5];

        int bigCount=0;
        int smallCount=0;
        int singleCount=0;
        int twoCount=0;

        //统计个十百千万 总次数
        int[] total = new int[10];

        int totalIssue = sgList.size();
        for(int i=0;i<totalIssue;i++){
            TxffcLotterySg sg = sgList.get(i);
            Integer wan = sg.getWan();
            Integer qian = sg.getQian();
            Integer bai = sg.getBai();
            Integer shi = sg.getShi();
            Integer ge = sg.getGe();


            arr1[wan] += 1;
            arr2[qian] += 1;
            arr3[bai] += 1;
            arr4[shi] += 1;
            arr5[ge] += 1;
            total[wan] += 1;
            total[qian] += 1;
            total[bai] += 1;
            total[shi] += 1;
            total[ge] += 1;


            if(wan%2==0 && wan>4){//统计大小单双
                big[0] +=1;
                bigCount++;
                two[0] +=1;
                twoCount++;
            }if(wan%2!=0 && wan>4){
                big[0] +=1;
                bigCount++;
                single[0] +=1;
                singleCount++;
            }if(wan%2==0 && wan<4){
                two[0] +=1;
                twoCount++;
                small[0] +=1;
                smallCount++;
            }if(wan%2!=0 && wan<4){
                single[0] +=1;
                singleCount++;
                small[0] +=1;
                smallCount++;
            }


            if(qian%2==0 && qian>4){
                big[1] +=1;
                bigCount++;
                two[1] +=1;
                twoCount++;
            }if(qian%2!=0 && qian>4){
                big[1] +=1;
                bigCount++;
                single[1] +=1;
                singleCount++;
            }if(qian%2==0 && qian<4){
                two[1] +=1;
                twoCount++;
                small[1] +=1;
                smallCount++;
            }if(qian%2!=0 && qian<4){
                single[1] +=1;
                singleCount++;
                small[1] +=1;
                smallCount++;
            }

            if(bai%2==0 && bai>4){
                big[2] +=1;
                bigCount++;
                two[2] +=1;
                twoCount++;
            }if(bai%2!=0 && bai>4){
                big[2] +=1;
                bigCount++;
                single[2] +=1;
                singleCount++;
            }if(bai%2==0 && bai<4){
                two[2] +=1;
                twoCount++;
                small[2] +=1;
                smallCount++;
            }if(bai%2!=0 && bai<4){
                single[2] +=1;
                singleCount++;
                small[2] +=1;
                smallCount++;
            }

            if(shi%2==0 && shi>4){
                big[3] +=1;
                bigCount++;
                two[3] +=1;
                twoCount++;
            }if(shi%2!=0 && shi>4){
                big[3] +=1;
                bigCount++;
                single[3] +=1;
                singleCount++;
            }if(shi%2==0 && shi<4){
                two[3] +=1;
                twoCount++;
                small[3] +=1;
                smallCount++;
            }if(shi%2!=0 && shi<4){
                single[3] +=1;
                singleCount++;
                small[3] +=1;
                smallCount++;
            }

            if(ge%2==0 && ge>4){
                big[4] +=1;
                bigCount++;
                two[4] +=1;
                twoCount++;
            }if(ge%2!=0 && ge>4){
                big[4] +=1;
                bigCount++;
                single[4] +=1;
                singleCount++;
            }if(ge%2==0 && ge<4){
                two[4] +=1;
                twoCount++;
                small[4] +=1;
                smallCount++;
            }if(ge%2!=0 && ge<4){
                single[4] +=1;
                singleCount++;
                small[4] +=1;
                smallCount++;
            }



        }

        Map<String, Object> result=new HashMap<>();
        List<Map<String,Object>> list=new ArrayList<>(10);
        for(int j=0;j<10;j++){
            Map<String, Object> mapList = new HashMap<>();
            ArrayList<Integer> list2 = new ArrayList<>(10); //0-9的号码
            list2.add(arr1[j]);  //第几位
            list2.add(arr2[j]);
            list2.add(arr3[j]);
            list2.add(arr4[j]);
            list2.add(arr5[j]);
            mapList.put("num", j);
            mapList.put("open", list2); //号码已开次数
            list.add(mapList);
        }

        result.put("list", list);
        result.put("numTotal", ArrayUtils.toList(total)); //号码出现次数统计
        result.put("bigCount",bigCount);
        result.put("smallCount",smallCount);
        result.put("singleCount",singleCount);
        result.put("twoCount",twoCount);
        result.put("big",big);
        result.put("single",single);
        result.put("small",small);
        result.put("two",two);

        return result;
    }

    public static TxffcCountSgdxds countDxds(List<TxffcLotterySg> txffcLotterySgs){
        TxffcCountSgdxds txffcCountSgdxds = new TxffcCountSgdxds();
        int counts0 = 0; //0 出现的次数
        int counts1 = 0; //1 出现的次数
        int counts2 = 0; //2 出现的次数
        int counts3 = 0; //3 出现的次数
        int counts4 = 0; //4 出现的次数
        int counts5 = 0; //5 出现的次数
        int counts6 = 0; //6 出现的次数
        int counts7 = 0; //7 出现的次数
        int counts8 = 0; //8 出现的次数
        int counts9 = 0; //9 出现的次数
        int countsBig = 0; //大 出现的次数
        int countsSmall = 0; //小 出现的次数
        int countsOdd = 0; //单 出现的次数
        int countsEven = 0; //双 出现的次数
        for(TxffcLotterySg sg:txffcLotterySgs){
            if(sg.getWan() == null) continue;
            Map<String,Integer> dataMap = getCountDxds(sg.getWan(),sg.getQian(),sg.getBai(),sg.getShi(),sg.getGe());
            counts0 = counts0+(dataMap.get("counts0")==null?0:dataMap.get("counts0"));
            counts1 = counts1+(dataMap.get("counts1")==null?0:dataMap.get("counts1"));
            counts2 = counts2+(dataMap.get("counts2")==null?0:dataMap.get("counts2"));
            counts3 = counts3+(dataMap.get("counts3")==null?0:dataMap.get("counts3"));
            counts4 = counts4+(dataMap.get("counts4")==null?0:dataMap.get("counts4"));
            counts5 = counts5+(dataMap.get("counts5")==null?0:dataMap.get("counts5"));
            counts6 = counts6+(dataMap.get("counts6")==null?0:dataMap.get("counts6"));
            counts7 = counts7+(dataMap.get("counts7")==null?0:dataMap.get("counts7"));
            counts8 = counts8+(dataMap.get("counts8")==null?0:dataMap.get("counts8"));
            counts9 = counts9+(dataMap.get("counts9")==null?0:dataMap.get("counts9"));
            countsBig = countsBig+(dataMap.get("countsBig")==null?0:dataMap.get("countsBig"));
            countsSmall = countsSmall+(dataMap.get("countsSmall")==null?0:dataMap.get("countsSmall"));
            countsOdd = countsOdd+(dataMap.get("countsOdd")==null?0:dataMap.get("countsOdd"));
            countsEven = countsEven+(dataMap.get("countsEven")==null?0:dataMap.get("countsEven"));
        }
        txffcCountSgdxds.setOne(counts1);
        txffcCountSgdxds.setTwo(counts2);
        txffcCountSgdxds.setThree(counts3);
        txffcCountSgdxds.setFour(counts4);
        txffcCountSgdxds.setFive(counts5);
        txffcCountSgdxds.setSix(counts6);
        txffcCountSgdxds.setSeven(counts7);
        txffcCountSgdxds.setEight(counts8);
        txffcCountSgdxds.setNigh(counts9);
        txffcCountSgdxds.setTen(counts0);
        txffcCountSgdxds.setBig(countsBig);
        txffcCountSgdxds.setSmall(countsSmall);
        txffcCountSgdxds.setOdd(countsOdd);
        txffcCountSgdxds.setEven(countsEven);
        return txffcCountSgdxds;
    }

    public static Map<String,Integer> getCountDxds(Integer wan,Integer qian,Integer bai,Integer shi,Integer ge){
        Map<String,Integer> dataMap = new HashMap<>();
        for(int i=0;i<5;i++){
            Integer thisNumber = 0;
            if(i == 0){
                thisNumber = wan;
            }else if(i == 1){
                thisNumber = qian;
            }else if(i == 2){
                thisNumber = bai;
            }else if(i == 3){
                thisNumber = shi;
            }else if(i == 4){
                thisNumber = ge;
            }
            if(thisNumber == 0){
                if(dataMap.get("counts0") == null){
                    dataMap.put("counts0",1);
                }else{
                    dataMap.put("counts0",dataMap.get("counts0")+1);
                }
                if(dataMap.get("countsSmall") == null){
                    dataMap.put("countsSmall",1);
                }else{
                    dataMap.put("countsSmall",dataMap.get("countsSmall")+1);
                }
                if(dataMap.get("countsEven") == null){
                    dataMap.put("countsEven",1);
                }else{
                    dataMap.put("countsEven",dataMap.get("countsEven")+1);
                }
            }else if(thisNumber == 1){
                if(dataMap.get("counts1") == null){
                    dataMap.put("counts1",1);
                }else{
                    dataMap.put("counts1",dataMap.get("counts1")+1);
                }
                if(dataMap.get("countsSmall") == null){
                    dataMap.put("countsSmall",1);
                }else{
                    dataMap.put("countsSmall",dataMap.get("countsSmall")+1);
                }
                if(dataMap.get("countsOdd") == null){
                    dataMap.put("countsOdd",1);
                }else{
                    dataMap.put("countsOdd",dataMap.get("countsOdd")+1);
                }
            }else if(thisNumber == 2){
                if(dataMap.get("counts2") == null){
                    dataMap.put("counts2",1);
                }else{
                    dataMap.put("counts2",dataMap.get("counts2")+1);
                }
                if(dataMap.get("countsSmall") == null){
                    dataMap.put("countsSmall",1);
                }else{
                    dataMap.put("countsSmall",dataMap.get("countsSmall")+1);
                }
                if(dataMap.get("countsEven") == null){
                    dataMap.put("countsEven",1);
                }else{
                    dataMap.put("countsEven",dataMap.get("countsEven")+1);
                }
            }else if(thisNumber == 3){
                if(dataMap.get("counts3") == null){
                    dataMap.put("counts3",1);
                }else{
                    dataMap.put("counts3",dataMap.get("counts3")+1);
                }
                if(dataMap.get("countsSmall") == null){
                    dataMap.put("countsSmall",1);
                }else{
                    dataMap.put("countsSmall",dataMap.get("countsSmall")+1);
                }
                if(dataMap.get("countsOdd") == null){
                    dataMap.put("countsOdd",1);
                }else{
                    dataMap.put("countsOdd",dataMap.get("countsOdd")+1);
                }
            }else if(thisNumber == 4){
                if(dataMap.get("counts4") == null){
                    dataMap.put("counts4",1);
                }else{
                    dataMap.put("counts4",dataMap.get("counts4")+1);
                }
                if(dataMap.get("countsSmall") == null){
                    dataMap.put("countsSmall",1);
                }else{
                    dataMap.put("countsSmall",dataMap.get("countsSmall")+1);
                }
                if(dataMap.get("countsEven") == null){
                    dataMap.put("countsEven",1);
                }else{
                    dataMap.put("countsEven",dataMap.get("countsEven")+1);
                }
            }else if(thisNumber == 5){
                if(dataMap.get("counts5") == null){
                    dataMap.put("counts5",1);
                }else{
                    dataMap.put("counts5",dataMap.get("counts5")+1);
                }
                if(dataMap.get("countsBig") == null){
                    dataMap.put("countsBig",1);
                }else{
                    dataMap.put("countsBig",dataMap.get("countsBig")+1);
                }
                if(dataMap.get("countsOdd") == null){
                    dataMap.put("countsOdd",1);
                }else{
                    dataMap.put("countsOdd",dataMap.get("countsOdd")+1);
                }
            }else if(thisNumber == 6){
                if(dataMap.get("counts6") == null){
                    dataMap.put("counts6",1);
                }else{
                    dataMap.put("counts6",dataMap.get("counts6")+1);
                }
                if(dataMap.get("countsBig") == null){
                    dataMap.put("countsBig",1);
                }else{
                    dataMap.put("countsBig",dataMap.get("countsBig")+1);
                }
                if(dataMap.get("countsEven") == null){
                    dataMap.put("countsEven",1);
                }else{
                    dataMap.put("countsEven",dataMap.get("countsEven")+1);
                }
            }else if(thisNumber == 7){
                if(dataMap.get("counts7") == null){
                    dataMap.put("counts7",1);
                }else{
                    dataMap.put("counts7",dataMap.get("counts7")+1);
                }
                if(dataMap.get("countsBig") == null){
                    dataMap.put("countsBig",1);
                }else{
                    dataMap.put("countsBig",dataMap.get("countsBig")+1);
                }
                if(dataMap.get("countsOdd") == null){
                    dataMap.put("countsOdd",1);
                }else{
                    dataMap.put("countsOdd",dataMap.get("countsOdd")+1);
                }
            }else if(thisNumber == 8){
                if(dataMap.get("counts8") == null){
                    dataMap.put("counts8",1);
                }else{
                    dataMap.put("counts8",dataMap.get("counts8")+1);
                }
                if(dataMap.get("countsBig") == null){
                    dataMap.put("countsBig",1);
                }else{
                    dataMap.put("countsBig",dataMap.get("countsBig")+1);
                }
                if(dataMap.get("countsEven") == null){
                    dataMap.put("countsEven",1);
                }else{
                    dataMap.put("countsEven",dataMap.get("countsEven")+1);
                }
            }else if(thisNumber == 9){
                if(dataMap.get("counts9") == null){
                    dataMap.put("counts9",1);
                }else{
                    dataMap.put("counts9",dataMap.get("counts9")+1);
                }
                if(dataMap.get("countsBig") == null){
                    dataMap.put("countsBig",1);
                }else{
                    dataMap.put("countsBig",dataMap.get("countsBig")+1);
                }
                if(dataMap.get("countsOdd") == null){
                    dataMap.put("countsOdd",1);
                }else{
                    dataMap.put("countsOdd",dataMap.get("countsOdd")+1);
                }
            }
        }

        return dataMap;
    }




}
