package com.caipiao.live.common.model.dto;

import java.util.List;

public class XiaZhuDO {

    private List<String> manyorderno;
    String period;
    //private LotSschistory lotSschistory;

    public List<String> getManyorderno() {
        return manyorderno;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setManyorderno(List<String> manyorderno) {
        this.manyorderno = manyorderno;
    }

	/*public LotSschistory getLotSschistory() {
		return lotSschistory;
	}

	public void setLotSschistory(LotSschistory lotSschistory) {
		this.lotSschistory = lotSschistory;
	}*/

}
