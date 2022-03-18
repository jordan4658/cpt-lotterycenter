package com.caipiao.core.library.dto.result;

import com.mapper.domain.PceggLotterySg;
import com.mapper.domain.PceggRecommend;

/**
 * PC蛋蛋免费推荐+赛果
 */
public class PceggRecommendDTO {

    private PceggLotterySg pceggLotterySg;

    private PceggRecommend pceggRecommend;

    public PceggLotterySg getPceggLotterySg() {
        return pceggLotterySg;
    }

    public void setPceggLotterySg(PceggLotterySg pceggLotterySg) {
        this.pceggLotterySg = pceggLotterySg;
    }

    public PceggRecommend getPceggRecommend() {
        return pceggRecommend;
    }

    public void setPceggRecommend(PceggRecommend pceggRecommend) {
        this.pceggRecommend = pceggRecommend;
    }
}
