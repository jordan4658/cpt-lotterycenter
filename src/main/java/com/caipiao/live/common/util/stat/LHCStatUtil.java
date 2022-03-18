package com.caipiao.live.common.util.stat;

import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.dto.lottery.LotterySgModel;
import com.caipiao.live.common.model.dto.stat.LHCStatDTO;
import com.caipiao.live.common.model.dto.stat.LHCStatRelation;
import com.caipiao.live.common.util.lottery.LhcUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 六合彩统计Util
 */
public class LHCStatUtil {

    private static final Logger logger = LoggerFactory.getLogger(LHCStatUtil.class);

    /** 六合彩-资讯-特码统计 */
    public static final String STAT_LHC_ZX_TM_HIT = "STAT_LHC_ZX_TM_HIT";
    /** 六合彩-资讯-特码遗漏统计 */
    public static final String STAT_LHC_ZX_TM_MISS = "STAT_LHC_ZX_TM_MISS";
    /** 六合彩-资讯-正码统计 */
    public static final String STAT_LHC_ZX_ZM_HIT = "STAT_LHC_ZX_ZM_HIT";
    /** 六合彩-资讯-正码遗漏统计 */
    public static final String STAT_LHC_ZX_ZM_MISS = "STAT_LHC_ZX_ZM_MISS";
    /** 六合彩-资讯-特码生肖统计 */
    public static final String STAT_LHC_ZX_TM_SX_HIT = "STAT_LHC_ZX_TM_SX_HIT";
    /** 六合彩-资讯-特码生肖遗漏统计 */
    public static final String STAT_LHC_ZX_TM_SX_MISS = "STAT_LHC_ZX_TM_SX_MISS";
    /** 六合彩-资讯-正码生肖统计 */
    public static final String STAT_LHC_ZX_ZM_SX_HIT = "STAT_LHC_ZX_ZM_SX_HIT";
    /** 六合彩-资讯-正码生肖遗漏统计 */
    public static final String STAT_LHC_ZX_ZM_SX_MISS = "STAT_LHC_ZX_ZM_SX_MISS";
    /** 六合彩-资讯-特码波色统计 */
    public static final String STAT_LHC_ZX_TM_COLOR_HIT = "STAT_LHC_ZX_TM_COLOR_HIT";
    /** 六合彩-资讯-特码波色遗漏统计 */
    public static final String STAT_LHC_ZX_TM_COLOR_MISS = "STAT_LHC_ZX_TM_COLOR_MISS";
    /** 六合彩-资讯-正码波色统计 */
    public static final String STAT_LHC_ZX_ZM_COLOR_HIT = "STAT_LHC_ZX_ZM_COLOR_HIT";
    /** 六合彩-资讯-正码波色遗漏统计 */
    public static final String STAT_LHC_ZX_ZM_COLOR_MISS = "STAT_LHC_ZX_ZM_COLOR_MISS";
    /** 六合彩-资讯-特码尾数统计 */
    public static final String STAT_LHC_ZX_TM_WS_HIT = "STAT_LHC_ZX_TM_WS_HIT";
    /** 六合彩-资讯-特码尾数遗漏统计 */
    public static final String STAT_LHC_ZX_TM_WS_MISS = "STAT_LHC_ZX_TM_WS_MISS";
    /** 波色统计长度 */
    public static final int BOSE_LENGTH = 3;
    /** 尾数统计长度 */
    public static final int WEISHU_LENGTH = 5;
    /** 生肖统计长度 */
    public static final int SHENGXIAO_LENGTH = 6;
    /** 特码/主码统计长度 */
    public static final int TM_LENGTH = 10;

    private static final Map<String, LHCStatRelation> KEYMAP = new HashMap<>();

    static {
        KEYMAP.put(STAT_LHC_ZX_TM_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_MISS, TM_LENGTH, "temaNumIn"));
        KEYMAP.put(STAT_LHC_ZX_TM_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_MISS, TM_LENGTH, "temaNumOut"));
        KEYMAP.put(STAT_LHC_ZX_ZM_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_MISS, TM_LENGTH, "zhengmaNumIn"));
        KEYMAP.put(STAT_LHC_ZX_ZM_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_MISS, TM_LENGTH, "zhengmaNumOut"));
        KEYMAP.put(STAT_LHC_ZX_TM_SX_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_SX_MISS, SHENGXIAO_LENGTH, "temaSxIn"));
        KEYMAP.put(STAT_LHC_ZX_TM_SX_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_SX_MISS, SHENGXIAO_LENGTH, "temaSxOut"));
        KEYMAP.put(STAT_LHC_ZX_ZM_SX_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_SX_MISS, SHENGXIAO_LENGTH, "zhengmaSxIn"));
        KEYMAP.put(STAT_LHC_ZX_ZM_SX_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_SX_MISS, SHENGXIAO_LENGTH, "zhengmaSxOut"));
        KEYMAP.put(STAT_LHC_ZX_TM_COLOR_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_COLOR_MISS, BOSE_LENGTH, "temaBsIn"));
        KEYMAP.put(STAT_LHC_ZX_TM_COLOR_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_COLOR_MISS, BOSE_LENGTH, "temaBsOut"));
        KEYMAP.put(STAT_LHC_ZX_ZM_COLOR_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_COLOR_MISS, BOSE_LENGTH, "zhengmaBsIn"));
        KEYMAP.put(STAT_LHC_ZX_ZM_COLOR_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_ZM_COLOR_MISS, BOSE_LENGTH, "zhengmaBsOut"));
        KEYMAP.put(STAT_LHC_ZX_TM_WS_HIT, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_WS_MISS, WEISHU_LENGTH, "temaWsIn"));
        KEYMAP.put(STAT_LHC_ZX_TM_WS_MISS, LHCStatRelation.newInstance(STAT_LHC_ZX_TM_WS_MISS, WEISHU_LENGTH, "temaWsOut"));
    }

    /**
     * 资讯统计
     *
     * @param infos
     * @return
     */
    public static List<MapListVO> statInfomations(List<LotterySgModel> infos) {
        if (null == infos || infos.size() == 0) {
            return new ArrayList<>();
        }
        //按开奖日期正序统计处理
        infos = Lists.reverse(infos);
        Map<String, List<LHCStatDTO>> statHitMap = new HashMap<>();
        Map<String, List<LHCStatDTO>> statMissMap = new HashMap<>();
        long start = System.currentTimeMillis();
        statHits(infos, statHitMap);
        statMiss(infos, statHitMap, statMissMap);
        compareAndFilter(statHitMap, statMissMap, infos.size());
        List<MapListVO> result = assembleResult(statHitMap);
        long end = System.currentTimeMillis();
        logger.info("statInfomations used times:{} ms.", end - start);
        return result;
    }

    private static List<MapListVO> assembleResult(Map<String, List<LHCStatDTO>> stats) {
        //logger.info("statInfomations.assembleResult for stats map:{}", JSONObject.toJSONString(stats));
        List<MapListVO> result = new ArrayList<>();
        for (Map.Entry<String, List<LHCStatDTO>> entry : stats.entrySet()) {
            List list = entry.getValue().stream().map((lhcStatDTO) -> lhcStatDTO.getValue()).collect(Collectors.toList());
            result.add(new MapListVO(KEYMAP.get(entry.getKey()).getResultKey(), list));
        }
        return result;
    }

    private static void compareAndFilter(Map<String, List<LHCStatDTO>> statHitMap, Map<String, List<LHCStatDTO>> statMissMap, int size) {
        statHitMap.putAll(statMissMap);
        for (Map.Entry<String, List<LHCStatDTO>> entry : statHitMap.entrySet()) {
            List<LHCStatDTO> lhcStatDTOS = entry.getValue();
            lhcStatDTOS = lhcStatDTOS
                    .parallelStream()
                    .sorted(LHCStatDTO::compareTo).collect(Collectors.toList());

            int valueLength = KEYMAP.get(entry.getKey()).getStatLength();
            //尾数统计
            boolean isWSStat = entry.getKey().contains("WS");
            //命中统计
            boolean isHitStat = entry.getKey().endsWith("HIT");
            if (lhcStatDTOS.size() < valueLength) {
                List list = new ArrayList();
                for (LHCStatDTO statDTO : lhcStatDTOS) {
                    try {
                        list.add(Integer.valueOf(statDTO.getValue()));
                    } catch (NumberFormatException e) {
                        list.add(statDTO.getValue());
                    }
                }
                List balls;
                switch (valueLength) {
                    case BOSE_LENGTH:
                        balls = Lists.newArrayList(LhcUtils.BOSE);
                        break;
                    case WEISHU_LENGTH:
                        balls = Lists.newArrayList(LhcUtils.WEISHU);
                        break;
                    case SHENGXIAO_LENGTH:
                        balls = Lists.newArrayList(LhcUtils.SHENGXIAO);
                        break;
                    default:
                        balls = Lists.newArrayList(LhcUtils.BALLS);
                        break;
                }
                balls.removeAll(list);

                for (int i = 0, len = valueLength - lhcStatDTOS.size(); i < len; i++) {
                    LHCStatDTO lhcStatDTO = LHCStatDTO.newInstance(balls.get(i).toString(), 0, size);
                    if (isHitStat) {
                        //命中的在后追加不足的球号
                        lhcStatDTOS.add(lhcStatDTO);
                    } else {
                        //遗漏的在前追加不足的球号
                        lhcStatDTOS.add(i, lhcStatDTO);
                    }
                }
            } else {
                lhcStatDTOS = lhcStatDTOS.subList(0, KEYMAP.get(entry.getKey()).getStatLength());
            }
            //尾数追加字符
            if (isWSStat) {
                for (LHCStatDTO statDTO : lhcStatDTOS) {
                    statDTO.setValue(statDTO.getValue().concat("尾"));
                }
            }
            statHitMap.put(entry.getKey(), lhcStatDTOS);
        }
    }

    /**
     * 统计命中
     *
     * @param list
     * @param statHitMap
     */
    private static void statHits(List<LotterySgModel> list, Map<String, List<LHCStatDTO>> statHitMap) {
        for (int i = 0, len = list.size(); i < len; i++) {
            LotterySgModel model = list.get(i);
            String date = model.getDate();
            String[] balls = model.getSg().split(",");
            int temaIndex = balls.length - 1;
            Integer teMa = Integer.valueOf(balls[temaIndex]);
            String weiShu = String.valueOf(Integer.valueOf(teMa) % 10);
            //获取当期号码的对应生肖
            List<String> shengXiaoList = LhcUtils.getNumberShengXiao(model.getSg(), date);
            //特码统计
            addLHCStatDTO(STAT_LHC_ZX_TM_HIT, LHCStatDTO.newInstance(teMa.toString(), i), statHitMap);
            addLHCStatDTO(STAT_LHC_ZX_TM_SX_HIT, LHCStatDTO.newInstance(shengXiaoList.get(temaIndex), i), statHitMap);
            if (LhcUtils.RED.contains(teMa)) {
                addLHCStatDTO(STAT_LHC_ZX_TM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[0], i), statHitMap);
            }
            if (LhcUtils.BLUE.contains(teMa)) {
                addLHCStatDTO(STAT_LHC_ZX_TM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[1], i), statHitMap);
            }
            if (LhcUtils.GREEN.contains(teMa)) {
                addLHCStatDTO(STAT_LHC_ZX_TM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[2], i), statHitMap);
            }
            addLHCStatDTO(STAT_LHC_ZX_TM_WS_HIT, LHCStatDTO.newInstance(weiShu, i), statHitMap);

            //正码统计
            for (int j = 0; j < 6; j++) {
                addLHCStatDTO(STAT_LHC_ZX_ZM_HIT, LHCStatDTO.newInstance(balls[j], i), statHitMap);
                //正码生肖统计
                addLHCStatDTO(STAT_LHC_ZX_ZM_SX_HIT, LHCStatDTO.newInstance(shengXiaoList.get(j), i), statHitMap);
                //正码波色统计
                if (LhcUtils.RED.contains(teMa)) {
                    addLHCStatDTO(STAT_LHC_ZX_ZM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[0], i), statHitMap);
                }
                if (LhcUtils.BLUE.contains(teMa)) {
                    addLHCStatDTO(STAT_LHC_ZX_ZM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[1], i), statHitMap);
                }
                if (LhcUtils.GREEN.contains(teMa)) {
                    addLHCStatDTO(STAT_LHC_ZX_ZM_COLOR_HIT, LHCStatDTO.newInstance(LhcUtils.BOSE[2], i), statHitMap);
                }
            }
        }
    }

    /**
     * 遗漏统计
     *
     * @param list
     * @param statHitMap
     * @param statMissMap
     */
    private static void statMiss(List<LotterySgModel> list, Map<String, List<LHCStatDTO>> statHitMap, Map<String, List<LHCStatDTO>> statMissMap) {

        int size = list.size() - 1;
        for (Map.Entry<String, List<LHCStatDTO>> entry : statHitMap.entrySet()) {
            List<LHCStatDTO> lhcStatDTOS = entry.getValue();
            for (LHCStatDTO lhcStatDTO : lhcStatDTOS) {
                LHCStatDTO miss = new LHCStatDTO();
                miss.setValue(lhcStatDTO.getValue());
                miss.setCount(lhcStatDTO.countMissIssues(size));
                addLHCStatDTO(KEYMAP.get(entry.getKey()).getStatKey(), miss, statMissMap);
            }
        }
    }

    private static void addLHCStatDTO(String key, LHCStatDTO lhcStatDTO, Map<String, List<LHCStatDTO>> stats) {
        if (!stats.containsKey(key)) {
            stats.put(key, new ArrayList<>());
        }
        //count==0的不添加
        if (lhcStatDTO.getCount() == 0) {
            logger.info("addLHCStatDTO for key{}:value{} is zero.", key, lhcStatDTO.getValue());
            return;
        }
        boolean hasExist = false;
        int index = -1;
        List<LHCStatDTO> list = stats.get(key);
        for (int i = 0, size = list.size(); i < size; i++) {
            if (list.get(i).getValue().equals(lhcStatDTO.getValue())) {
                hasExist = true;
                index = i;
                break;
            }
        }
        if (hasExist) {
            list.get(index).incrementCount().addIndex(lhcStatDTO.getIndex().get(0));
        } else {
            list.add(lhcStatDTO);
        }
    }
}
