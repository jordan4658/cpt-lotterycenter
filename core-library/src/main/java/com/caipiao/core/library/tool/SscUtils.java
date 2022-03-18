package com.caipiao.core.library.tool;

import com.caipiao.core.library.model.dao.SscModel.SscCountModel;
import com.caipiao.core.library.vo.ssc.SscTodayCountVO;
import com.mapper.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时时彩功能类
 *
 * @author lzy
 * @create 2018-07-02 16:01
 **/
public class SscUtils {

    public static final String WAN = "wan";

    public static final String QIAN = "qian";

    public static final String BAI = "bai";

    public static final String SHI = "shi";

    public static final String GE = "ge";

    public static ArrayList<SscTodayCountVO> createSscCountVoList() {
        ArrayList<SscTodayCountVO> sscTodayCountVOS = new ArrayList<>(10);
        for (int i = 0 ; i < 10; i++) {
            SscTodayCountVO sscTodayCountVO = new SscTodayCountVO();
            sscTodayCountVO.setNum(i);
            sscTodayCountVOS.add(sscTodayCountVO);
        }
        return sscTodayCountVOS;
    }

    public static void setSscCountVoMember(ArrayList<SscTodayCountVO> sscTodayCountVOList, List<SscCountModel> sscCountModels, String member) {
        if (sscCountModels != null && sscTodayCountVOList != null && StringUtils.isNotBlank(member)) {
            switch (member) {
                case WAN :
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum() == sscCountModel.getNumber()) {
                                sscTodayCountVO.setWan(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case QIAN :
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum() == sscCountModel.getNumber()) {
                                sscTodayCountVO.setQian(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case BAI :
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum() == sscCountModel.getNumber()) {
                                sscTodayCountVO.setBai(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case SHI :
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum() == sscCountModel.getNumber()) {
                                sscTodayCountVO.setShi(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case GE :
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum() == sscCountModel.getNumber()) {
                                sscTodayCountVO.setGe(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public static JssscCountSgdxds jsssscCountDxds(List<JssscLotterySg> jssscLotterySgs){
        JssscCountSgdxds jssscCountSgdxds = new JssscCountSgdxds();
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
        for(JssscLotterySg sg:jssscLotterySgs){
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
        jssscCountSgdxds.setOne(counts1);
        jssscCountSgdxds.setTwo(counts2);
        jssscCountSgdxds.setThree(counts3);
        jssscCountSgdxds.setFour(counts4);
        jssscCountSgdxds.setFive(counts5);
        jssscCountSgdxds.setSix(counts6);
        jssscCountSgdxds.setSeven(counts7);
        jssscCountSgdxds.setEight(counts8);
        jssscCountSgdxds.setNigh(counts9);
        jssscCountSgdxds.setTen(counts0);
        jssscCountSgdxds.setBig(countsBig);
        jssscCountSgdxds.setSmall(countsSmall);
        jssscCountSgdxds.setOdd(countsOdd);
        jssscCountSgdxds.setEven(countsEven);
        return jssscCountSgdxds;
    }

    public static FivesscCountSgdxds fivessscCountDxds(List<FivesscLotterySg> fivesscLotterySgs){
        FivesscCountSgdxds fivesscCountSgdxds = new FivesscCountSgdxds();
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
        for(FivesscLotterySg sg:fivesscLotterySgs){
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
        fivesscCountSgdxds.setOne(counts1);
        fivesscCountSgdxds.setTwo(counts2);
        fivesscCountSgdxds.setThree(counts3);
        fivesscCountSgdxds.setFour(counts4);
        fivesscCountSgdxds.setFive(counts5);
        fivesscCountSgdxds.setSix(counts6);
        fivesscCountSgdxds.setSeven(counts7);
        fivesscCountSgdxds.setEight(counts8);
        fivesscCountSgdxds.setNigh(counts9);
        fivesscCountSgdxds.setTen(counts0);
        fivesscCountSgdxds.setBig(countsBig);
        fivesscCountSgdxds.setSmall(countsSmall);
        fivesscCountSgdxds.setOdd(countsOdd);
        fivesscCountSgdxds.setEven(countsEven);
        return fivesscCountSgdxds;
    }

    public static TensscCountSgdxds tenssscCountDxds(List<TensscLotterySg> tensscLotterySgs){
        TensscCountSgdxds tensscCountSgdxds = new TensscCountSgdxds();
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
        for(TensscLotterySg sg:tensscLotterySgs){
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
        tensscCountSgdxds.setOne(counts1);
        tensscCountSgdxds.setTwo(counts2);
        tensscCountSgdxds.setThree(counts3);
        tensscCountSgdxds.setFour(counts4);
        tensscCountSgdxds.setFive(counts5);
        tensscCountSgdxds.setSix(counts6);
        tensscCountSgdxds.setSeven(counts7);
        tensscCountSgdxds.setEight(counts8);
        tensscCountSgdxds.setNigh(counts9);
        tensscCountSgdxds.setTen(counts0);
        tensscCountSgdxds.setBig(countsBig);
        tensscCountSgdxds.setSmall(countsSmall);
        tensscCountSgdxds.setOdd(countsOdd);
        tensscCountSgdxds.setEven(countsEven);
        return tensscCountSgdxds;
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
