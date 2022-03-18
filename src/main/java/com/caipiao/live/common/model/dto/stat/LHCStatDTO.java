package com.caipiao.live.common.model.dto.stat;


import com.caipiao.live.common.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 六合彩统计DTO
 */
public class LHCStatDTO {

    /** 统计目标 */
    private String value;
    /** 所在开奖期数索引 */
    private List<Integer> index = new ArrayList<>();
    /** 计数和：命中或遗漏 */
    private int count = 1;

    public LHCStatDTO() {
    }

    public LHCStatDTO(String value, int index, Integer count) {
        this.value = value;
        this.index.add(index);
        if (null != count && this.count > 0) {
            this.count = count;
        }
    }

    public LHCStatDTO incrementCount() {
        this.setCount(this.getCount() + 1);
        return this;
    }

    public LHCStatDTO addIndex(int index) {
        if (!this.getIndex().contains(index)) {
            this.getIndex().add(index);
        }
        return this;
    }

    /**
     * 统计遗漏期数最大值
     *
     * @param size 统计的总期数
     * @return
     */
    public int countMissIssues(int size) {
        int diff = 0, len = this.getIndex().size();
        if (len == 1) {
            diff = size - this.getIndex().get(0);
        } else {
            //只处理最后一期(总期数 - 最后一期出现的位置)
            int endDiff = size - this.getIndex().get(len - 1);
            if (endDiff < 0) {
                endDiff = size - endDiff;//size + |endDiff|
            }
            diff = Math.max(diff, endDiff);
        }
        return diff;
    }

    /**
     * 排序：
     * 先按命中值倒序，相同则按球大小升序
     * 非数值统一按命中值倒序
     *
     * @return
     */
    public int compareTo(LHCStatDTO dto) {
        int diff = this.compareWithCount(dto);
        if (diff == 0) {
            diff = this.compareWithValue(dto);
        }
        return diff;
    }

    /**
     * 按count降序
     *
     * @param dto
     * @return
     */
    public int compareWithCount(LHCStatDTO dto) {
        if (this.getCount() > dto.getCount()) {
            return -1;
        } else if (this.getCount() == dto.getCount()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 按值升序
     *
     * @param dto
     * @return
     */
    public int compareWithValue(LHCStatDTO dto) {
        if (!CommonUtils.isNumeric(this.getValue())) {
            return 0;
        }
        int value1 = Integer.valueOf(this.getValue());
        int value2 = Integer.valueOf(dto.getValue());
        if (value1 > value2) {
            return 1;
        } else if (value1 == value2) {
            return 0;
        } else {
            return -1;
        }
    }

    public static LHCStatDTO newInstance(String value, int index) {
        return newInstance(value, index, null);
    }

    public static LHCStatDTO newInstance(String value, int index, Integer count) {
        return new LHCStatDTO(value, index, count);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void setIndex(List<Integer> index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
