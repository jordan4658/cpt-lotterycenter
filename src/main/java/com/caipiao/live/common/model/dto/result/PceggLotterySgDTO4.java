package com.caipiao.live.common.model.dto.result;


import com.caipiao.live.common.mybatis.entity.PceggLotterySg;

/**
 * @Author xiaomi
 * @Date 2018/10/30 17:46
 */
public class PceggLotterySgDTO4 extends PceggLotterySg {
    private static final long serialVersionUID = 1L;

    private Integer single; //单
    private Integer two; //双
    private Integer big; //大
    private Integer small; //小
    private Integer bigSingle; //大单
    private Integer smallOne;//小单
    private Integer bigTwo; //大双
    private Integer smallTwo;//小双
    private Integer maximum;//极大
    private Integer minimum;//极小
    private Integer redWave;//红波
    private Integer greenWave;//绿波
    private Integer Lambo;//蓝波
    private Integer leopard;//豹子

    private Integer countIssue;//总期数

    public PceggLotterySgDTO4() {}

    public PceggLotterySgDTO4(boolean isInit) {
        if (isInit) {
            this.single = 0;
            this.two = 0;
            this.big = 0;
            this.small = 0;
            this.bigSingle = 0;
            this.smallOne = 0;
            this.bigTwo = 0;
            this.smallTwo = 0;
            this.maximum = 0;
            this.minimum = 0;
            this.redWave = 0;
            this.greenWave = 0;
            this.Lambo = 0;
            this.leopard = 0;
            this.countIssue = 0;
        }
    }

    public PceggLotterySgDTO4(Integer single, Integer two, Integer big, Integer small, Integer bigSingle, Integer smallOne, Integer bigTwo, Integer smallTwo, Integer maximum, Integer minimum, Integer redWave, Integer greenWave, Integer lambo, Integer leopard, Integer countIssue) {
        this.single = single;
        this.two = two;
        this.big = big;
        this.small = small;
        this.bigSingle = bigSingle;
        this.smallOne = smallOne;
        this.bigTwo = bigTwo;
        this.smallTwo = smallTwo;
        this.maximum = maximum;
        this.minimum = minimum;
        this.redWave = redWave;
        this.greenWave = greenWave;
        Lambo = lambo;
        this.leopard = leopard;
        this.countIssue = countIssue;
    }

    public Integer getSingle() {
        return single;
    }

    public void setSingle(Integer single) {
        this.single = single;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public Integer getBig() {
        return big;
    }

    public void setBig(Integer big) {
        this.big = big;
    }

    public Integer getSmall() {
        return small;
    }

    public void setSmall(Integer small) {
        this.small = small;
    }

    public Integer getBigSingle() {
        return bigSingle;
    }

    public void setBigSingle(Integer bigSingle) {
        this.bigSingle = bigSingle;
    }

    public Integer getSmallOne() {
        return smallOne;
    }

    public void setSmallOne(Integer smallOne) {
        this.smallOne = smallOne;
    }

    public Integer getBigTwo() {
        return bigTwo;
    }

    public void setBigTwo(Integer bigTwo) {
        this.bigTwo = bigTwo;
    }

    public Integer getSmallTwo() {
        return smallTwo;
    }

    public void setSmallTwo(Integer smallTwo) {
        this.smallTwo = smallTwo;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getRedWave() {
        return redWave;
    }

    public void setRedWave(Integer redWave) {
        this.redWave = redWave;
    }

    public Integer getGreenWave() {
        return greenWave;
    }

    public void setGreenWave(Integer greenWave) {
        this.greenWave = greenWave;
    }

    public Integer getLambo() {
        return Lambo;
    }

    public void setLambo(Integer lambo) {
        Lambo = lambo;
    }

    public Integer getLeopard() {
        return leopard;
    }

    public void setLeopard(Integer leopard) {
        this.leopard = leopard;
    }

    public Integer getCountIssue() {
        return countIssue;
    }

    public void setCountIssue(Integer countIssue) {
        this.countIssue = countIssue;
    }
}
