package com.caipiao.core.library.vo.money;

import com.mapper.domain.MemberBalanceChange;

public class BalanceChangeVO extends MemberBalanceChange {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
