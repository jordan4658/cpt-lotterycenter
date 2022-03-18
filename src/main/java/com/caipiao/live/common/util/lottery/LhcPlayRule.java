package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.util.TimeHelper;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 六合彩玩法规则
 *
 * @author lzy
 * @create 2018-07-19 9:56
 **/
public class LhcPlayRule {

    // 大
    public static final List<Integer> DA = new ArrayList<Integer>();
    // 小
    public static final List<Integer> XIAO = new ArrayList<Integer>();
    // 单
    public static final List<Integer> DAN = new ArrayList<Integer>();
    // 双
    public static final List<Integer> SHUANG = new ArrayList<Integer>();
    // 尾大
    public static final List<Integer> WEIDA = new ArrayList<Integer>();
    // 尾小
    public static final List<Integer> WEIXIAO = new ArrayList<Integer>();
    // 和单
    public static final List<Integer> HESHUDAN = new ArrayList<Integer>();
    // 和双
    public static final List<Integer> HESHUSHUANG = new ArrayList<Integer>();

    //红波
    public static final List<Integer> RED= Lists.newArrayList(1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46);
    //蓝波
    public static final List<Integer> BLUE = Lists.newArrayList(3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48);
    //绿波
    public static final List<Integer> GREEN = Lists.newArrayList(5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49);

    //红单
    public static final List<Integer> REDDAN = Lists.newArrayList(1,7,13,19,23,29,35,45);
    //红双
    public static final List<Integer> REDSHUANG = Lists.newArrayList(2,8,12,18,24,30,34,40,46);
    //红大
    public static final List<Integer> REDDA = Lists.newArrayList(29,30,34,35,40,45,46);
    //红小
    public static final List<Integer> REDXIAO = Lists.newArrayList(1,2,7,8,12,13,18,19,23,24);
    //红合单
    public static final List<Integer> REDHEDAN = Lists.newArrayList(1,7,12,18,23,29,30,34,45);
    //红合双
    public static final List<Integer> REDHESHUANG = Lists.newArrayList(2,8,13,19,24,35,40,46);
    //蓝单
    public static final List<Integer> BLUEDAN = Lists.newArrayList(3,9,15,25,31,37,41,47);
    //蓝双
    public static final List<Integer> BLUESHUANG = Lists.newArrayList(4,10,14,20,26,36,42,48);
    //蓝大
    public static final List<Integer> BLUEDA = Lists.newArrayList(25,26,31,36,37,41,42,47,48);
    //蓝小
    public static final List<Integer> BLUEXIAO = Lists.newArrayList(3,4,9,10,14,15,20);
    //蓝合单
    public static final List<Integer> BLUEHEDAN = Lists.newArrayList(3,9,10,14,25,36,41,47);
    //蓝合双
    public static final List<Integer> BLUEHESHUANG = Lists.newArrayList(04,15,20,26,31,37,42,48);
    //绿单
    public static final List<Integer> GREENDAN = Lists.newArrayList(5,11,17,21,27,33,39,43,49);
    //绿双
    public static final List<Integer> GREENSHUANG = Lists.newArrayList(6,16,22,28,32,38,44);
    //绿大
    public static final List<Integer> GREENDA = Lists.newArrayList(27,28,32,33,38,39,43,44,49);
    //绿小
    public static final List<Integer> GREENXIAO = Lists.newArrayList(5,6,11,16,17,21,22);
    //绿合单
    public static final List<Integer> GREENHEDAN = Lists.newArrayList(5,16,21,27,32,38,43,49);
    //绿合双
    public static final List<Integer> GREENHESHUANG = Lists.newArrayList(6,11,17,22,28,33,39,44);

    // 存放生肖属性的名称
    public static final List<String> SHENGXIAO = Lists.newArrayList("SHU","NIU","HU","TU","LONG","SHE","MA","YANG","HOU","JI","GOU","ZHU");
    //鼠
    public static final List<Integer> SHU = getSXBalls("SHU");
    //牛
    public static final List<Integer> NIU =getSXBalls("NIU");;
    //虎
    public static final List<Integer> HU =getSXBalls("HU");
    //兔
    public static final List<Integer> TU = getSXBalls("TU");
    //龙
    public static final List<Integer> LONG = getSXBalls("LONG");
    //蛇
    public static final List<Integer> SHE = getSXBalls("SHE");
    //马
    public static final List<Integer> MA = getSXBalls("MA");
    //羊
    public static final List<Integer> YANG =getSXBalls("YANG");
    //猴
    public static final List<Integer> HOU =getSXBalls("HOU");
    //鸡
    public static final List<Integer> JI = getSXBalls("JI");
    //狗
    public static final List<Integer> GOU = getSXBalls("GOU");
    //猪
    public static final List<Integer> ZHU = getSXBalls("ZHU");


    static{
        for(int i = 1; i <= 49; i++) {
            if(DA(i)) {
                DA.add(i);
            }
            if(XIAO(i)) {
                XIAO.add(i);
            }
            if(DAN(i)) {
                DAN.add(i);
            }
            if(SHUANG(i)) {
                SHUANG.add(i);
            }
            if(WEIDA(i)) {
                WEIDA.add(i);
            }
            if(WEIXIAO(i)) {
                WEIXIAO.add(i);
            }
            if(HESHUDAN(i)) {
                HESHUDAN.add(i);
            }
            if(HESHUSHUANG(i)) {
                HESHUSHUANG.add(i);
            }
        }
    }

    /**
     * 获取对应生肖的号码
     * @param sx 获取的生肖
     * @return
     */
    public static List<Integer> getSXBalls(String sx) {

        String nowSx = getThisYearSX();
        List<Integer> ret=Lists.newArrayList();
        Integer beginNum = 1;
        int size = 4;
        if(nowSx.equals(sx)) {
            size=5;
        } else {
            int sxPostion = SHENGXIAO.indexOf(sx);
            int thisYearSxPostion = SHENGXIAO.indexOf(nowSx);
            int distince = thisYearSxPostion - sxPostion;
            if(distince < 0) {
                distince = distince + 12;
            }
            beginNum = 1 + distince;
        }
        for(int i=0;i<size;i++) {
            ret.add(beginNum);
            beginNum = beginNum + 12;
        }
        return ret;
    }

    /**
     * 获取当前年份的生肖
     * @return
     */
    public static String getThisYearSX() {
        String year= TimeHelper.date("yyyy");
        String thisYearSX = SHENGXIAO.get((Integer.valueOf(year) - 4) % 12);
        return thisYearSX;
    }

    /**
     * 是否为和值
     * @param ballNum
     * @return
     */
    public static boolean HE(Integer ballNum) {
        if(ballNum.intValue()==49) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean  DA(Integer ballNum) {
        if(!HE(ballNum)&&ballNum>=25) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean  XIAO(Integer ballNum) {
        if(!HE(ballNum)&&ballNum<=24) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean  DAN(Integer ballNum) {
        if(!HE(ballNum)&&ballNum.intValue()%2!=0) {
            return true;
        } else {
            return false;
        }
    }

    public  static boolean  SHUANG(Integer ballNum) {
        if(!HE(ballNum)&&ballNum.intValue()%2==0) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * 是否为尾大
     * @param ballNum
     * @return
     */
    public  static boolean  WEIDA(Integer ballNum) {
        if(HE(ballNum)) {
            return false;
        }
        Integer ws=ballNum%10;
        if(ws>=5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否为尾小
     * @param ballNum
     * @return
     */
    public  static boolean  WEIXIAO(Integer ballNum) {
        if(HE(ballNum)) {
            return false;
        }
        Integer ws=ballNum%10;
        if(ws<=4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否为和数单
     * @param ballNum
     * @return
     */
    public  static boolean  HESHUDAN(Integer ballNum) {
        if(HE(ballNum)) {
            return false;
        }
        if(ballNum <10)
        {
            if (ballNum.intValue() % 2 != 0) {
                return true;
            }
        }
        if(ballNum >= 10)
        {
            Integer gw=ballNum%10;
            Integer sw = (ballNum/10);
            if((gw+sw)%2!=0) {
                return true;
            }

        }
        return false;

    }

    /**
     * 是否为和数双
     * @param ballNum
     * @return
     */
    public  static boolean  HESHUSHUANG(Integer ballNum) {
        if(HE(ballNum)) {
            return false;
        }
        return !HESHUDAN(ballNum);
    }

    /**
     * 获取号码的半波
     * @param num
     * @return
     */
    public static String getNumBanbo(int num, String split) {
        List<String> list = getNumBanboList(num);
        int size =  list.size();
        if (size > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < size; i++) {
                sb.append(list.get(i)).append(split);
            }
            return sb.substring(0, sb.length() - 1);
        }
        return null;
    }

    /**
     * 获取号码的半波
     * @param num
     * @return
     */
    public static List<String> getNumBanboList(int num) {
        ArrayList<String> list = new ArrayList<>();
        if (RED.contains(num)) {
            if (REDDA.contains(num)) {
                list.add("红大");
            } else if (REDXIAO.contains(num)) {
                list.add("红小");
            }
            if (REDDAN.contains(num)) {
                list.add("红单");
            } else if (REDSHUANG.contains(num)) {
                list.add("红双");
            }
            if (REDHEDAN.contains(num)) {
                list.add("红合单");
            } else if (REDHESHUANG.contains(num)) {
                list.add("红合双");
            }
        } else if (BLUE.contains(num)) {
            if (BLUEDA.contains(num)) {
                list.add("蓝大");
            } else if (BLUEXIAO.contains(num)) {
                list.add("蓝小");
            }
            if (BLUEDAN.contains(num)) {
                list.add("蓝单");
            } else if (BLUESHUANG.contains(num)) {
                list.add("蓝双");
            }
            if (BLUEHEDAN.contains(num)) {
                list.add("蓝合单");
            } else if (BLUEHESHUANG.contains(num)) {
                list.add("蓝合双");
            }
        } else if (GREEN.contains(num)) {
            if (GREENDA.contains(num)) {
                list.add("绿大");
            } else if (GREENXIAO.contains(num)) {
                list.add("绿小");
            }
            if (GREENDAN.contains(num)) {
                list.add("绿单");
            } else if (GREENSHUANG.contains(num)) {
                list.add("绿双");
            }
            if (GREENHEDAN.contains(num)) {
                list.add("绿合单");
            } else if (GREENHESHUANG.contains(num)) {
                list.add("绿合双");
            }
        }
        return list;
    }

    /**
     * 获取号码的两面
     * @param num
     * @return
     */
    public static List<String> getNumLiangMianList(int num) {
        ArrayList<String> list = new ArrayList<>();
        if (num != 49) {
            if (num >= 25) {
                list.add("大");
            } else {
                list.add("小");
            }
            if (num % 2 == 1) {
                list.add("单");
            } else {
                list.add("双");
            }
            int tou = num / 10;
            int wei = num % 10;
            if ((tou + wei) % 2 == 1) {
                list.add("合单");
            } else {
                list.add("合双");
            }
            if (wei >= 5) {
                list.add("尾大");
            } else {
                list.add("尾小");
            }
        }
        if (RED.contains(num)) {
           list.add("红波");
        } else if (BLUE.contains(num)) {
            list.add("蓝波");
        } else if (GREEN.contains(num)) {
            list.add("绿波");
        }
        return list;
    }

}
