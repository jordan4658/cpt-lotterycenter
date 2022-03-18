package com.caipiao.live.common.model.dto.result;


import com.caipiao.live.common.mybatis.entity.PceggLotterySg;

/**
 * PC蛋蛋免费推荐+赛果
 */
public class PceggRecommendDTO {

    private PceggLotterySg pceggLotterySg;

    //private PceggRecommend pceggRecommend;

    public PceggLotterySg getPceggLotterySg() {
        return pceggLotterySg;
    }

    public void setPceggLotterySg(PceggLotterySg pceggLotterySg) {
        this.pceggLotterySg = pceggLotterySg;
    }

//    public PceggRecommend getPceggRecommend() {
//        return pceggRecommend;
//    }
//
//    public void setPceggRecommend(PceggRecommend pceggRecommend) {
//        this.pceggRecommend = pceggRecommend;
//    }
}
