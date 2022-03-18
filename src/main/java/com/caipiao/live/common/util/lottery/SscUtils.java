package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.model.vo.SscTodayCountVO;
import com.caipiao.live.common.model.dto.lottery.SscCountModel;
import com.caipiao.live.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < 10; i++) {
            SscTodayCountVO sscTodayCountVO = new SscTodayCountVO();
            sscTodayCountVO.setNum(i);
            sscTodayCountVOS.add(sscTodayCountVO);
        }
        return sscTodayCountVOS;
    }

    public static void setSscCountVoMember(ArrayList<SscTodayCountVO> sscTodayCountVOList, List<SscCountModel> sscCountModels, String member) {
        if (sscCountModels != null && sscTodayCountVOList != null && StringUtils.isNotBlank(member)) {
            switch (member) {
                case WAN:
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum().equals(sscCountModel.getNumber())) {
                                sscTodayCountVO.setWan(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case QIAN:
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum().equals(sscCountModel.getNumber())) {
                                sscTodayCountVO.setQian(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case BAI:
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum().equals(sscCountModel.getNumber())) {
                                sscTodayCountVO.setBai(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case SHI:
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum().equals(sscCountModel.getNumber())) {
                                sscTodayCountVO.setShi(sscCountModel.getCount());
                                break;
                            }
                        }
                    }
                    break;
                case GE:
                    for (SscTodayCountVO sscTodayCountVO : sscTodayCountVOList) {
                        for (SscCountModel sscCountModel : sscCountModels) {
                            if (sscTodayCountVO.getNum().equals(sscCountModel.getNumber())) {
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
}
