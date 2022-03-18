package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.enums.lottery.LotteryOpenStatusEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.AmlhcLotterySg;
import com.caipiao.live.common.mybatis.entity.AmlhcLotterySgExample;
import com.caipiao.live.common.mybatis.entity.FivebjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.FivebjpksLotterySgExample;
import com.caipiao.live.common.mybatis.entity.FivelhcLotterySg;
import com.caipiao.live.common.mybatis.entity.FivelhcLotterySgExample;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySg;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySgExample;
import com.caipiao.live.common.mybatis.entity.JsbjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.JsbjpksLotterySgExample;
import com.caipiao.live.common.mybatis.entity.JssscLotterySg;
import com.caipiao.live.common.mybatis.entity.JssscLotterySgExample;
import com.caipiao.live.common.mybatis.entity.OnelhcLotterySg;
import com.caipiao.live.common.mybatis.entity.OnelhcLotterySgExample;
import com.caipiao.live.common.mybatis.entity.TenbjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.TenbjpksLotterySgExample;
import com.caipiao.live.common.mybatis.entity.TensscLotterySg;
import com.caipiao.live.common.mybatis.entity.TensscLotterySgExample;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySg;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AmlhcLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.FivebjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.FivelhcLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.FivesscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.JsbjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.JssscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.OnelhcLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.TenbjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.TensscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.TjsscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AmlhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.AuspksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.AussscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.AzksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.BjpksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.CqsscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.DzksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.DzpceggLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.DzxyftLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FivelhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FivesscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FvpksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.LhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.LotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.OnelhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.PceggLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.SslhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TenpksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TensscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TjsscDragonServiceReadSg;
import com.caipiao.live.common.service.lottery.TjsscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TxffcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.XjplhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.XjsscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.XyftLotterySgServiceReadSg;
import com.caipiao.live.common.util.lottery.CaipiaoNumberFormatUtils;
import com.caipiao.live.common.util.lottery.LhcUtils;
import com.caipiao.live.common.util.lottery.ListSortUtils;
import com.caipiao.live.common.util.lottery.NnJsOperationUtils;
import com.caipiao.live.common.util.lottery.NnKlOperationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * @create 2018-08-27 14:18
 **/
@Service
public class LotteryReadSgServiceImpl implements LotterySgServiceReadSg {

    private final static Logger logger = LoggerFactory.getLogger(LotteryReadSgServiceImpl.class);
    @Autowired
    private CqsscLotterySgServiceReadSg cqsscLotterySgService;
    @Autowired
    private JssscLotterySgServiceReadSg jssscLotterySgService;
    @Autowired
    private LhcLotterySgServiceReadSg lhcLotterySgService;
    @Autowired
    private BjpksLotterySgServiceReadSg bjpksLotterySgService;
    @Autowired
    private PceggLotterySgServiceReadSg pceggLotterySgService;
    @Autowired
    private XjsscLotterySgServiceReadSg xjsscLotterySgService;
    @Autowired
    private TxffcLotterySgServiceReadSg txffcLotterySgService;
    @Autowired
    private XyftLotterySgServiceReadSg xyftLotterySgService;
    //    @Autowired
//    private TcplsLotterySgService tcplsLotterySgService;
//    @Autowired
//    private TcplwLotterySgService tcplwLotterySgService;
//    @Autowired
//    private TcdltLotterySgService tcdltLotterySgService;
//    @Autowired
//    private Tc7xcLotterySgService tc7xcLotterySgService;
//    @Autowired
//    private Fc3dLotterySgService fc3dLotterySgService;
//    @Autowired
//    private FcssqLotterySgService fcssqLotterySgService;
//    @Autowired
//    private Fc7lcLotterySgService fc7lcLotterySgService;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private FivesscLotterySgMapper fivesscLotterySgMapper;
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;
    @Autowired
    private FivelhcLotterySgMapper fivelhcLotterySgMapper;
    @Autowired
    private OnelhcLotterySgMapper onelhcLotterySgMapper;
    @Autowired
    private AmlhcLotterySgServiceReadSg amlhcLotterySgService;
    //    @Autowired
//    private FtjspksLotterySgService ftjspksLotterySgService;
//    @Autowired
//    private FtjssscLotterySgService ftjssscLotterySgService;
//    @Autowired
//    private FtxyftLotterySgService ftxyftLotterySgService;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private AussscLotterySgServiceReadSg aussscLotterySgService;
    @Autowired
    private AuspksLotterySgServiceReadSg auspksLotterySgService;
    @Autowired
    private JspksLotterySgServiceReadSg jspksLotterySgService;
    @Autowired
    private OnelhcLotterySgServiceReadSg onelhcLotterySgService;
    @Autowired
    private FivesscLotterySgServiceReadSg fivesscLotterySgService;
    @Autowired
    private TensscLotterySgServiceReadSg tensscLotterySgService;
    @Autowired
    private TjsscLotterySgServiceReadSg tjsscLotterySgService;
    @Autowired
    private TjsscDragonServiceReadSg tjsscDragonService;
    @Autowired
    private FvpksLotterySgServiceReadSg fvpksLotterySgService;
    @Autowired
    private TenpksLotterySgServiceReadSg tenpksLotterySgService;
    @Autowired
    private FivelhcLotterySgServiceReadSg fivelhcLotterySgService;
    @Autowired
    private SslhcLotterySgServiceReadSg sslhcLotterySgService;
    @Autowired
    private DzpceggLotterySgServiceReadSg dzpceggLotterySgService;
    @Autowired
    private DzxyftLotterySgServiceReadSg dzxyftLotterySgService;
    @Autowired
    private DzksLotterySgServiceReadSg dzksLotterySgService;
    @Autowired
    private AzksLotterySgServiceReadSg azksLotterySgService;
    @Autowired
    private XjplhcLotterySgServiceReadSg xjplhcLotterySgService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> cqssc = this.cqsscLotterySgService.getNewestSgInfo();
        Map<String, Object> tjssc = tjsscLotterySgService.getNewestSgInfo();
        Map<String, Object> tenssc = tensscLotterySgService.getTensscNewestSgInfo();
        Map<String, Object> fivessc = fivesscLotterySgService.getFivesscNewestSgInfo();
        Map<String, Object> jsssc = jssscLotterySgService.getJssscNewestSgInfo();
        Map<String, Object> sslhc = sslhcLotterySgService.getSslhcNewestSgInfo().getData();
        Map<String, Object> fivelhc = fivelhcLotterySgService.getFivelhcNewestSgInfo().getData();
        Map<String, Object> onelhc = onelhcLotterySgService.getOnelhcNewestSgInfo().getData();
        ResultInfo<Map<String, Object>> tenpks = tenpksLotterySgService.getNewestSgInfo();
        ResultInfo<Map<String, Object>> fivepks = fvpksLotterySgService.getNewestSgInfo();
        ResultInfo<Map<String, Object>> jspks = jspksLotterySgService.getJspksNewestSgInfo();
        ResultInfo<Map<String, Object>> lhc = this.lhcLotterySgService.getNewestSgInfo();
        ResultInfo<Map<String, Object>> bjpks = this.bjpksLotterySgService.getNewestSgInfo();
        ResultInfo<Map<String, Object>> pcegg = this.pceggLotterySgService.getSgInfo();
        ResultInfo<Map<String, Object>> xjssc = this.xjsscLotterySgService.getNewestSgInfo();
        Map<String, Object> txffc = this.txffcLotterySgService.getNewestSgInfo();
        ResultInfo<Map<String, Object>> xyft = this.xyftLotterySgService.getNewestSgInfo();

//        Map<String, Object> tcpls = this.tcplsLotterySgService.getNewestSgInfo();
//        Map<String, Object> tcplw = this.tcplwLotterySgService.getNewestSgInfo();
//        Map<String, Object> tc7xc = this.tc7xcLotterySgService.getNewestSgInfo();
//        Map<String, Object> tcdlt = this.tcdltLotterySgService.getNewestSgInfo();
//        Map<String, Object> fc3d = this.fc3dLotterySgService.getNewestSgInfo();
//        Map<String, Object> fc7lc = this.fc7lcLotterySgService.getNewestSgInfo();
//        Map<String, Object> fcssq = this.fcssqLotterySgService.getNewestSgInfo();

        Map<String, Object> auspks = aussscLotterySgService.getNewestSgInfo();
        Map<String, Object> ausssc = auspksLotterySgService.getNewestSgInfo();
        Map<String, Object> ausact = ausactLotterySgService.getNewestSgInfo();
        result.put("auspks", auspks);
        result.put("ausssc", ausssc);
        result.put("ausact", ausact);
        result.put("cqssc", cqssc);
        result.put("tjssc", tjssc);
        result.put("tenssc", tenssc);
        result.put("fivessc", fivessc);
        result.put("jsssc", jsssc);
        result.put("sslhc", sslhc);
        result.put("fivelhc", fivelhc);
        result.put("onelhc", onelhc);
        result.put("tenpks", tenpks);
        result.put("fivepks", fivepks);
        result.put("jspks", jspks);
        result.put("lhc", lhc.getData());
        result.put("bjpks", bjpks.getData());
        result.put("pcegg", pcegg.getData());
        result.put("xjssc", xjssc.getData());
        result.put("txffc", txffc);
        result.put("xyft", xyft.getData());
//        result.put("tcpls", tcpls);
//        result.put("tcplw", tcplw);
//        result.put("tc7xc", tc7xc);
//        result.put("tcdlt", tcdlt);
//        result.put("fc3d", fc3d);
//        result.put("fc7lc", fc7lc);
//        result.put("fcssq", fcssq);
        return ResultInfo.ok(result);
    }

    /*
     * @Title: getNewestSgInfobyids
     * @Description: 通过ID获取多个彩种的赛果
     * @see com.caipiao.live.read.service.result.LotterySgService#
     * getNewestSgInfobyids(java.lang.String)
     */
    @Override
    public ResultInfo<Map<String, Object>> getNewestSgScInfobyids(String ids) {
        String[] idarr = ids.split(",");
        Map<String, Object> result = new HashMap<>();
        for (String id : idarr) {
            if (null == id || "".equals(id.trim())) {
                return getNewestSgInfo();
            }
            id = id.trim();
            CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
            if (null == caipiaoTypeEnum) {
                return getNewestSgInfo();
            }
//            int idint = Integer.parseInt(id);
            switch (caipiaoTypeEnum) {
                case TENSSC:
                    Map<String, Object> tenssc = tensscLotterySgService.getTensscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TENSSC.getTagType(), tenssc);
                    break;
                case FIVESSC:
                    Map<String, Object> fivessc = fivesscLotterySgService.getFivesscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.FIVESSC.getTagType(), fivessc);
                    break;
                case JSSSC:
                    Map<String, Object> jsssc = jssscLotterySgService.getJssscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.JSSSC.getTagType(), jsssc);
                    break;
                case ONELHC:
                    Map<String, Object> onelhc = onelhcLotterySgService.getOnelhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.ONELHC.getTagType(), onelhc);
                    break;
                case FIVELHC:
                    Map<String, Object> fivelhc = fivelhcLotterySgService.getFivelhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.FIVELHC.getTagType(), fivelhc);
                    break;
                case AMLHC:
                    Map<String, Object> sslhc = sslhcLotterySgService.getSslhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.AMLHC.getTagType(), sslhc);
                    break;
                case TENPKS:
                    ResultInfo<Map<String, Object>> tenpks = tenpksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TENPKS.getTagType(), tenpks.getData());
                    break;
                case FIVEPKS:
                    ResultInfo<Map<String, Object>> fivepks = fvpksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.FIVEPKS.getTagType(), fivepks.getData());
                    break;
                case JSPKS:
                    ResultInfo<Map<String, Object>> jspks = jspksLotterySgService.getJspksNewestSgInfo();
                    result.put(CaipiaoTypeEnum.JSPKS.getTagType(), jspks.getData());
                    break;
                case TXFFC:
                    Map<String, Object> txffc = this.txffcLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TXFFC.getTagType(), txffc);
                    break;
                case DZPCDAND:
                    Map<String, Object> dzpcegg = dzpceggLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZPCDAND.getTagType(), dzpcegg);
                    break;
                case DZXYFEIT:
                    Map<String, Object> dzxyft = dzxyftLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZXYFEIT.getTagType(), dzxyft);
                    break;
                case DZKS:
                    Map<String, Object> dzks = dzksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZKS.getTagType(), dzks);
                    break;
                case AZKS:
                    Map<String, Object> azks = azksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.AZKS.getTagType(), azks);
                    break;
                case XJPLHC:
                    Map<String, Object> xjplhc = xjplhcLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.XJPLHC.getTagType(), xjplhc);
                    break;
                default:
                    break;
            }
        }
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfobyids(String ids) {
        String[] idarr = ids.split(",");
        Map<String, Object> result = new HashMap<>();
        for (String id : idarr) {
            if (null == id || "".equals(id.trim())) {
                return getNewestSgInfo();
            }
            id = id.trim();
            CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
            if (null == caipiaoTypeEnum) {
                return getNewestSgInfo();
            }
//            int idint = Integer.parseInt(id);
            switch (caipiaoTypeEnum) {
//                case 0:
//                    return getNewestSgInfo();
                case CQSSC:
                    Map<String, Object> cqssc = this.cqsscLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.CQSSC.getTagType(), cqssc);
                    break;
                case XJSSC:
                    ResultInfo<Map<String, Object>> xjssc = this.xjsscLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.XJSSC.getTagType(), xjssc.getData());
                    break;
                case TJSSC:
                    Map<String, Object> tjssc = tjsscLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TJSSC.getTagType(), tjssc);
                    break;
                case TENSSC:
                    Map<String, Object> tenssc = tensscLotterySgService.getTensscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TENSSC.getTagType(), tenssc);
                    break;
                case FIVESSC:
                    Map<String, Object> fivessc = fivesscLotterySgService.getFivesscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.FIVESSC.getTagType(), fivessc);
                    break;
                case JSSSC:
                    Map<String, Object> jsssc = jssscLotterySgService.getJssscNewestSgInfo();
                    result.put(CaipiaoTypeEnum.JSSSC.getTagType(), jsssc);
                    break;
                case LHC:
                    ResultInfo<Map<String, Object>> lhc = this.lhcLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.LHC.getTagType(), lhc.getData());
                    break;
                case ONELHC:
                    Map<String, Object> onelhc = onelhcLotterySgService.getOnelhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.ONELHC.getTagType(), onelhc);
                    break;
                case FIVELHC:
                    Map<String, Object> fivelhc = fivelhcLotterySgService.getFivelhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.FIVELHC.getTagType(), fivelhc);
                    break;
                case AMLHC:
                    Map<String, Object> sslhc = sslhcLotterySgService.getSslhcNewestSgInfo().getData();
                    result.put(CaipiaoTypeEnum.AMLHC.getTagType(), sslhc);
                    break;
                case BJPKS:
                    ResultInfo<Map<String, Object>> bjpks = this.bjpksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.BJPKS.getTagType(), bjpks.getData());
                    break;
                case TENPKS:
                    ResultInfo<Map<String, Object>> tenpks = tenpksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TENPKS.getTagType(), tenpks.getData());
                    break;
                case FIVEPKS:
                    ResultInfo<Map<String, Object>> fivepks = fvpksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.FIVEPKS.getTagType(), fivepks.getData());
                    break;
                case JSPKS:
                    ResultInfo<Map<String, Object>> jspks = jspksLotterySgService.getJspksNewestSgInfo();
                    result.put(CaipiaoTypeEnum.JSPKS.getTagType(), jspks.getData());
                    break;
                case XYFEIT:
                    ResultInfo<Map<String, Object>> xyft = this.xyftLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.XYFEIT.getTagType(), xyft.getData());
                    break;
                case PCDAND:
                    ResultInfo<Map<String, Object>> pcegg = this.pceggLotterySgService.getSgInfo();
                    result.put(CaipiaoTypeEnum.PCDAND.getTagType(), pcegg.getData());
                    break;
                case TXFFC:
                    Map<String, Object> txffc = this.txffcLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.TXFFC.getTagType(), txffc);
                    break;
//                case DLT:
//                    Map<String, Object> dlt = tcdltLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.DLT.getTagType(), dlt);
//                    break;
//                case TCPLW:
//                    Map<String, Object> tcplw = tcplwLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.TCPLW.getTagType(), tcplw);
//                    break;
//                case TC7XC:
//                    Map<String, Object> tc7xc = tc7xcLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.TC7XC.getTagType(), tc7xc);
//                    break;
//                case FCSSQ:
//                    Map<String, Object> fcssq = fcssqLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.FCSSQ.getTagType(), fcssq);
//                    break;
//                case FC3D:
//                    Map<String, Object> fc3d = fc3dLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.FC3D.getTagType(), fc3d);
//                    break;
//                case FC7LC:
//                    Map<String, Object> fc7lcc = fc7lcLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.FC7LC.getTagType(), fc7lcc);
//                    break;
//                case KLNIU:
//                    Map<String, Object> hpNiuMap = fivesscLotterySgService.getFivesscNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.KLNIU.getTagType(), hpNiuMap);
//                    break;
//                case AZNIU:
//                    Map<String, Object> auspksMap = auspksLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.AZNIU.getTagType(), auspksMap);
//                    break;
//                case JSNIU:
//                    ResultInfo<Map<String, Object>> jsnnsInfo = jspksLotterySgService.getJspksNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.JSNIU.getTagType(), jsnnsInfo.getData());
//                    break;
//                case JSPKFT:
//                    Map<String, Object> ftjspks = ftjspksLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.JSPKFT.getTagType(), ftjspks);
//                    break;
//                case XYFTFT:
//                    Map<String, Object> ftxyft = ftxyftLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.XYFTFT.getTagType(), ftxyft);
//                    break;
//                case JSSSCFT:
//                    Map<String, Object> ftjsssc = ftjssscLotterySgService.getNewestSgInfo();
//                    result.put(CaipiaoTypeEnum.JSSSCFT.getTagType(), ftjsssc);
//                    break;
                case AUSACT:
                    Map<String, Object> ausact = ausactLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.AUSACT.getTagType(), ausact);
                    break;
                case AUSSSC:
                    Map<String, Object> ausssc = aussscLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.AUSSSC.getTagType(), ausssc);
                    break;
                case AUSPKS:
                    Map<String, Object> auspks = auspksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.AUSPKS.getTagType(), auspks);
                    break;
                case DZPCDAND:
                    Map<String, Object> dzpcegg = dzpceggLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZPCDAND.getTagType(), dzpcegg);
                    break;
                case DZXYFEIT:
                    Map<String, Object> dzxyft = dzxyftLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZXYFEIT.getTagType(), dzxyft);
                    break;
                case DZKS:
                    Map<String, Object> dzks = dzksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.DZKS.getTagType(), dzks);
                    break;
                case AZKS:
                    Map<String, Object> azks = azksLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.AZKS.getTagType(), azks);
                    break;
                case XJPLHC:
                    Map<String, Object> xjplhc = xjplhcLotterySgService.getNewestSgInfo();
                    result.put(CaipiaoTypeEnum.XJPLHC.getTagType(), xjplhc);
                    break;
                default:
                    break;
            }
        }
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize, Integer id) {
        ResultInfo<Map<String, Object>> ret = new ResultInfo<>();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(String.valueOf(id));
        if (null == caipiaoTypeEnum) {
            return ret;
        }
        switch (caipiaoTypeEnum) {
            case CQSSC:
                ret = ResultInfo.ok(cqsscLotterySgService.lishiSg(pageNo, pageSize));
                break;
            case XJSSC:
                ret = xjsscLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case TJSSC:
                ret = this.lishitjsscSg(pageNo, pageSize);
                break;
            case TENSSC:
                ret = this.lishitensscSg(pageNo, pageSize);
                break;
            case FIVESSC:
                ret = this.lishifivesscSg(pageNo, pageSize);
                break;
            case JSSSC:
                ret = this.lishijssscSg(pageNo, pageSize);
                break;
            case LHC:
                ret = lhcLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case ONELHC:
                ret = this.lishionelhcSg(pageNo, pageSize);
                break;
            case FIVELHC:
                ret = this.lishifivelhcSg(pageNo, pageSize);
                break;
            case AMLHC:
                ret = this.lishisslhcSg(pageNo, pageSize);
                break;
            case BJPKS:
                ret = bjpksLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case TENPKS:
                ret = this.lishitenpksSg(pageNo, pageSize);
                break;
            case FIVEPKS:
                ret = this.lishifivepksSg(pageNo, pageSize);
                break;
            case JSPKS:
                ret = this.lishijspksSg(pageNo, pageSize);
                break;
            case XYFEIT:
                ret = xyftLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case PCDAND:
                ret = pceggLotterySgService.lishitenpksSg(pageNo, pageSize);
                break;
            case TXFFC:
                ret = ResultInfo.ok(txffcLotterySgService.lishiSg(pageNo, pageSize));
                break;
//            case DLT:
//                ret = tcdltLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case TCPLW:
//                ret = tcplwLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case TC7XC:
//                ret = tc7xcLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case FCSSQ:
//                ret = fcssqLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case FC3D:
//                ret = fc3dLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case FC7LC:
//                ret = fc7lcLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case KLNIU:
//                ret = this.lishiHappyNiuNiuSg(pageNo, pageSize);
//                break;
//            case AZNIU:
//                ret = auspksLotterySgService.azNNlishiSg(pageNo, pageSize);
//                break;
//            case JSNIU:
//                ret = this.jsNiuNiupksSg(pageNo, pageSize);
//                break;
//            case JSPKFT:
//                ret = ftjspksLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case XYFTFT:
//                ret = ftxyftLotterySgService.lishiSg(pageNo, pageSize);
//                break;
//            case JSSSCFT:
//                ret = ftjssscLotterySgService.lishiSg(pageNo, pageSize);
//                break;
            case AUSACT:
                ret = ausactLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case AUSSSC:
                ret = aussscLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case AUSPKS:
                ret = auspksLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case DZPCDAND:
                ret = dzpceggLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case DZXYFEIT:
                ret = dzxyftLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case DZKS:
                ret = dzksLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case AZKS:
                ret = azksLotterySgService.lishiSg(pageNo, pageSize);
                break;
            case XJPLHC:
                ret = xjplhcLotterySgService.lishiSg(pageNo, pageSize);
                break;
            default:
                break;
        }

        return ret;
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitjsscSg(Integer pageNo, Integer pageSize) {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        criteria.andWanIsNotNull();
        criteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<TjsscLotterySg> tjsscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.TJSSC_SG_HS_LIST)) {
            TjsscLotterySgExample exampleOne = new TjsscLotterySgExample();
            TjsscLotterySgExample.Criteria tjsscCriteriaOne = exampleOne.createCriteria();
            tjsscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<TjsscLotterySg> cqsscLotterySgsOne = tjsscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.TJSSC_SG_HS_LIST, cqsscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            tjsscLotterySgs = redisTemplate.opsForList().range(RedisKeys.TJSSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);
        }

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TjsscLotterySg sg : tjsscLotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }
//            if(sg.getWan() == null){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
//                // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
//                map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
//            }

            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitensscSg(Integer pageNo, Integer pageSize) {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        criteria.andWanIsNotNull();
        criteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<TensscLotterySg> tensscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.TENSSC_SG_HS_LIST)) {
            TensscLotterySgExample exampleOne = new TensscLotterySgExample();
            TensscLotterySgExample.Criteria tensscCriteriaOne = exampleOne.createCriteria();
            tensscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<TensscLotterySg> tensscLotterySgsOne = tensscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.TENSSC_SG_HS_LIST, tensscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            tensscLotterySgs = redisTemplate.opsForList().range(RedisKeys.TENSSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            tensscLotterySgs = tensscLotterySgMapper.selectByExample(example);
        }

//        List<TensscLotterySg> lotterySgs = tensscLotterySgMapper.selectByExample(example);
        //logger.info("lishitensscSg lotterySgs:{}", JSONObject.toJSONString(lotterySgs));

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TensscLotterySg sg : tensscLotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivesscSg(Integer pageNo, Integer pageSize) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        criteria.andWanIsNotNull();
        criteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<FivesscLotterySg> fivesscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.FIVESSC_SG_HS_LIST)) {
            FivesscLotterySgExample exampleOne = new FivesscLotterySgExample();
            FivesscLotterySgExample.Criteria fivesscCriteriaOne = exampleOne.createCriteria();
            fivesscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<FivesscLotterySg> fivesscLotterySgsOne = fivesscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.FIVESSC_SG_HS_LIST, fivesscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            fivesscLotterySgs = redisTemplate.opsForList().range(RedisKeys.FIVESSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            fivesscLotterySgs = fivesscLotterySgMapper.selectByExample(example);
        }

//        List<FivesscLotterySg> lotterySgs = fivesscLotterySgMapper.selectByExample(example);
        //logger.info("lishifivesscSg lotterySgs:{}.", JSONObject.toJSONString(lotterySgs));

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivesscLotterySg sg : fivesscLotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }
//            if(sg.getWan() == null){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
//                // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
//                map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
//            }

            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return
     * @Title: lishifivesscSg
     * @Description: 快乐牛牛历史
     * @see .lang.Integer, java.lang.Integer)
     */
    public ResultInfo<Map<String, Object>> lishiHappyNiuNiuSg(Integer pageNo, Integer pageSize) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        criteria.andWanIsNotNull();
        criteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<FivesscLotterySg> fivesscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.FIVESSC_SG_HS_LIST)) {
            FivesscLotterySgExample exampleOne = new FivesscLotterySgExample();
            FivesscLotterySgExample.Criteria fivesscCriteriaOne = exampleOne.createCriteria();
            fivesscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<FivesscLotterySg> fivesscLotterySgsOne = fivesscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.FIVESSC_SG_HS_LIST, fivesscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            fivesscLotterySgs = redisTemplate.opsForList().range(RedisKeys.FIVESSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            fivesscLotterySgs = fivesscLotterySgMapper.selectByExample(example);
        }

//        List<FivesscLotterySg> lotterySgs = fivesscLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivesscLotterySg sg : fivesscLotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(AppMianParamEnum.ISSUE.getParamEnName(), sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getIdealTime());
//            }else{
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put(AppMianParamEnum.NUMBER.getParamEnName(), new StringBuffer().append(sg.getWan())
//                        .append(sg.getQian()).append(sg.getBai()).append(sg.getShi()).append(sg.getGe()).toString());
//                map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(), sg.getQian(), sg.getBai(),
//                        sg.getShi(), sg.getGe()));
//                map.put(AppMianParamEnum.HE.getParamEnName(),
//                        sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
//                // 获取牛牛结果
//                String niuWinner = NnKlOperationUtils.getNiuWinner(sg.getNumber());
//                map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
//            }

            map.put(AppMianParamEnum.NUMBER.getParamEnName(), new StringBuffer().append(sg.getWan())
                    .append(sg.getQian()).append(sg.getBai()).append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(), sg.getQian(), sg.getBai(),
                    sg.getShi(), sg.getGe()));
            map.put(AppMianParamEnum.HE.getParamEnName(),
                    sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
            // 获取牛牛结果
            String niuWinner = NnKlOperationUtils.getNiuWinner(sg.getNumber());
            map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijssscSg(Integer pageNo, Integer pageSize) {
        JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
        //        criteria.andWanIsNotNull();
        criteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<JssscLotterySg> jssscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.JSSSC_SG_HS_LIST)) {
            JssscLotterySgExample exampleOne = new JssscLotterySgExample();
            JssscLotterySgExample.Criteria jssscCriteriaOne = exampleOne.createCriteria();
            jssscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<JssscLotterySg> jssscLotterySgsOne = jssscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.JSSSC_SG_HS_LIST, jssscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            jssscLotterySgs = redisTemplate.opsForList().range(RedisKeys.JSSSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            jssscLotterySgs = jssscLotterySgMapper.selectByExample(example);
        }

//        List<JssscLotterySg> lotterySgs = jssscLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JssscLotterySg sg : jssscLotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }

//            if(sg.getWan() == null){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                        .append(sg.getShi()).append(sg.getGe()).toString());
//                // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
//                // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
//                map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
//            }

            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
                    .append(sg.getShi()).append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishisslhcSg(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        AmlhcLotterySgExample example = new AmlhcLotterySgExample();
//        example.createCriteria()
//                .andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name())
//                .andNumberIsNotNull();
        example.createCriteria().andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        example.createCriteria().andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<AmlhcLotterySg> amlhcLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AMLHC_SG_HS_LIST)) {
            AmlhcLotterySgExample exampleOne = new AmlhcLotterySgExample();
            AmlhcLotterySgExample.Criteria sslhcCriteriaOne = exampleOne.createCriteria();
            sslhcCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AmlhcLotterySg> sslhcLotterySgsOne = amlhcLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AMLHC_SG_HS_LIST, sslhcLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            amlhcLotterySgs = redisTemplate.opsForList().range(RedisKeys.AMLHC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            amlhcLotterySgs = amlhcLotterySgMapper.selectByExample(example);
        }

//        List<SslhcLotterySg> lhcLotterySgs = this.sslhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishiamlhcSg(amlhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishionelhcSg(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        OnelhcLotterySgExample example = new OnelhcLotterySgExample();
//        example.createCriteria().andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
        //        criteria.andWanIsNotNull();
        example.createCriteria().andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        example.createCriteria().andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<OnelhcLotterySg> onelhcLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.ONELHC_SG_HS_LIST)) {
            OnelhcLotterySgExample exampleOne = new OnelhcLotterySgExample();
            OnelhcLotterySgExample.Criteria onelhcCriteriaOne = exampleOne.createCriteria();
            onelhcCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<OnelhcLotterySg> onelhcLotterySgsOne = onelhcLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.ONELHC_SG_HS_LIST, onelhcLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            onelhcLotterySgs = redisTemplate.opsForList().range(RedisKeys.ONELHC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            onelhcLotterySgs = onelhcLotterySgMapper.selectByExample(example);
        }

//        List<OnelhcLotterySg> lhcLotterySgs = this.onelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishionelhcSg(onelhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivelhcSg(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        FivelhcLotterySgExample example = new FivelhcLotterySgExample();
//        example.createCriteria()
//                .andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name())
//                .andNumberIsNotNull();
        //        criteria.andWanIsNotNull();
        example.createCriteria().andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        example.createCriteria().andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<FivelhcLotterySg> fivelhcLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.FIVELHC_SG_HS_LIST)) {
            FivelhcLotterySgExample exampleOne = new FivelhcLotterySgExample();
            FivelhcLotterySgExample.Criteria fivelhcCriteriaOne = exampleOne.createCriteria();
            fivelhcCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<FivelhcLotterySg> fivelhcLotterySgsOne = fivelhcLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.FIVELHC_SG_HS_LIST, fivelhcLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            fivelhcLotterySgs = redisTemplate.opsForList().range(RedisKeys.FIVELHC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            fivelhcLotterySgs = fivelhcLotterySgMapper.selectByExample(example);
        }

//        List<FivelhcLotterySg> lhcLotterySgs = this.fivelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishifivelhcSg(fivelhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitenpksSg(Integer pageNo, Integer pageSize) {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
//        example.createCriteria()
//                .andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name())
//                .andNumberIsNotNull();
        example.createCriteria().andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        example.createCriteria().andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<TenbjpksLotterySg> tenbjpksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.TENPKS_SG_HS_LIST)) {
            TenbjpksLotterySgExample exampleOne = new TenbjpksLotterySgExample();
            TenbjpksLotterySgExample.Criteria tenpksCriteriaOne = exampleOne.createCriteria();
            tenpksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<TenbjpksLotterySg> tenbjpksLotterySgsOne = tenbjpksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.TENPKS_SG_HS_LIST, tenbjpksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            tenbjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.TENPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            tenbjpksLotterySgs = tenbjpksLotterySgMapper.selectByExample(example);
        }

//        List<TenbjpksLotterySg> bjpksLotterySgs = tenbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TenbjpksLotterySg sg : tenbjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", sg.getNumber());
//                map.put("numberstr", sg.getNumber());
//            }

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivepksSg(Integer pageNo, Integer pageSize) {
        FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
//        bjpksCriteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        bjpksCriteria.andNumberIsNotNull();
        bjpksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<FivebjpksLotterySg> fivebjpksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.FIVEPKS_SG_HS_LIST)) {
            FivebjpksLotterySgExample exampleOne = new FivebjpksLotterySgExample();
            FivebjpksLotterySgExample.Criteria fivebjpksCriteriaOne = exampleOne.createCriteria();
            fivebjpksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<FivebjpksLotterySg> fivebjpksLotterySgsOne = fivebjpksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.FIVEPKS_SG_HS_LIST, fivebjpksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            fivebjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.FIVEPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            fivebjpksLotterySgs = fivebjpksLotterySgMapper.selectByExample(example);
        }

//        List<FivebjpksLotterySg> bjpksLotterySgs = fivebjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivebjpksLotterySg sg : fivebjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", sg.getNumber());
//                map.put("numberstr", sg.getNumber());
//            }

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijspksSg(Integer pageNo, Integer pageSize) {
        JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
//        bjpksCriteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        bjpksCriteria.andNumberIsNotNull();
        bjpksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<JsbjpksLotterySg> jsbjpksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.JSPKS_SG_HS_LIST)) {
            JsbjpksLotterySgExample exampleOne = new JsbjpksLotterySgExample();
            JsbjpksLotterySgExample.Criteria jsbjpksCriteriaOne = exampleOne.createCriteria();
            jsbjpksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<JsbjpksLotterySg> jsbjpksLotterySgsOne = jsbjpksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.JSPKS_SG_HS_LIST, jsbjpksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            jsbjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.JSPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            jsbjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        }

//        List<JsbjpksLotterySg> bjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JsbjpksLotterySg sg : jsbjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put("time", sg.getIdealTime());
//            }else{
//                map.put("time", sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", sg.getNumber());
//                map.put("numberstr", sg.getNumber());
//            }

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        return ResultInfo.ok(result);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return ResultInfo<Map < String, Object>>
     * @Title: jsNiuNiupksSg
     * @Description: 德州牛牛历史信息
     * @author admin
     * @date 2019年4月11日下午2:08:36
     */
    public ResultInfo<Map<String, Object>> jsNiuNiupksSg(Integer pageNo, Integer pageSize) {
        JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria jsbjpksCriteria = example.createCriteria();
//        bjpksCriteria.andOpenStatusNotEqualTo(LotteryOpenStatusEnum.WAIT.name());
//        bjpksCriteria.andNumberIsNotNull();
        jsbjpksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<JsbjpksLotterySg> jsbjpksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.JSPKS_SG_HS_LIST)) {
            JsbjpksLotterySgExample exampleOne = new JsbjpksLotterySgExample();
            JsbjpksLotterySgExample.Criteria jsbjpksCriteriaOne = exampleOne.createCriteria();
            jsbjpksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<JsbjpksLotterySg> jsbjpksLotterySgsOne = jsbjpksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.JSPKS_SG_HS_LIST, jsbjpksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            jsbjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.JSPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            jsbjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        }

//        List<JsbjpksLotterySg> bjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JsbjpksLotterySg sg : jsbjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getIdealTime());
//            }else{
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", sg.getNumber());
//                map.put("numberstr", sg.getNumber());
//
//                String jsNiuWinner = NnJsOperationUtils.getNiuWinner(sg.getNumber());
//                map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);
//            }

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            String jsNiuWinner = NnJsOperationUtils.getNiuWinner(sg.getNumber());
            map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        return ResultInfo.ok(result);
    }

    /**
     * @return
     * @Title: getSgLongDragons
     * @Description: 获取开奖长龙数据
     */
    @Override
    public ResultInfo<List<Map<String, Object>>> getSgLongDragons() {
        // 定义长龙接口返回类型
        List<Map<String, Object>> dragonList = new ArrayList<Map<String, Object>>();
        try {
            // 澳洲ACT 大小/单双
            List<Map<String, Object>> ausactLongMapList = ausactLotterySgService.getActSgLong();

            if (!CollectionUtils.isEmpty(ausactLongMapList)) {
                dragonList.addAll(ausactLongMapList);
            }
            // PC蛋蛋  混合大小/混合单双
            List<Map<String, Object>> pceggLongMapList = pceggLotterySgService.getPceggSgLong();

            if (!CollectionUtils.isEmpty(pceggLongMapList)) {
                dragonList.addAll(pceggLongMapList);
            }
            // 时时彩系列
            List<Map<String, Object>> sscDragonList = this.getSscDragonLongResult();

            if (!CollectionUtils.isEmpty(sscDragonList)) {
                dragonList.addAll(sscDragonList);
            }
            // 六合彩系列
            List<Map<String, Object>> lhcDragonList = this.getLhcDragonLongResult();

            if (!CollectionUtils.isEmpty(lhcDragonList)) {
                dragonList.addAll(lhcDragonList);
            }
            // PK10系列
            List<Map<String, Object>> pksDragonList = this.getPksDragonLongResult();

            if (!CollectionUtils.isEmpty(pksDragonList)) {
                dragonList.addAll(pksDragonList);
            }
            // 排序
            ListSortUtils.sort(dragonList);
        } catch (Exception e) {
            logger.error("app getSgLongDragons.json error:", e);
        }
        return ResultInfo.ok(dragonList);
    }

    /**
     * @Title: getSscDragonLongResult
     * @Description: 获取时时彩系列长龙数据
     * @author HANS
     * @date 2019年5月28日下午7:47:25
     */
    private List<Map<String, Object>> getSscDragonLongResult() {
        // 定义长龙接口返回类型
        List<Map<String, Object>> sscDragonList = new ArrayList<Map<String, Object>>();
        try {
            /**
             * jsssc德州时时彩
             *  1第一球大小、第二球大小、第三球大小、第四球大小、第五球大小、两面总和大小
             *  2第一球单双、第二球单双、第三球单双、第四球单双、第五球单双、两面总和单双
             * */
            List<Map<String, Object>> jssscLongMapList = jssscLotterySgService.getJssscSgLong();
            sscDragonList.addAll(jssscLongMapList);
            /**
             * 5分时时彩
             * */
            List<Map<String, Object>> fivesscLongMapList = fivesscLotterySgService.getFivesscSgLong();
            sscDragonList.addAll(fivesscLongMapList);
            /**
             * 10分时时彩
             * */
            List<Map<String, Object>> tensscLongMapList = tensscLotterySgService.getTensscSgLong();
            sscDragonList.addAll(tensscLongMapList);
            /**
             * tjssc天津时时彩
             * */
            List<Map<String, Object>> tjsscLongMapList = tjsscDragonService.getTjsscSgDragonLong();
            sscDragonList.addAll(tjsscLongMapList);
            /**
             * xjssc新疆时时彩
             * */
            List<Map<String, Object>> xjsscLongMapList = xjsscLotterySgService.getxjsscSgLong();
            sscDragonList.addAll(xjsscLongMapList);
            /**
             * cqssc重庆时时彩
             * */
            List<Map<String, Object>> cqsscLongMapList = cqsscLotterySgService.getCqsscSgLong();
            sscDragonList.addAll(cqsscLongMapList);
            /**
             * txffc比特币分分彩
             * */
            List<Map<String, Object>> txffcLongMapList = txffcLotterySgService.getTxffcSgLong();
            sscDragonList.addAll(txffcLongMapList);
        } catch (Exception e) {
            logger.error("app Method:getSscDragonLongResult error:", e);
        }
        return sscDragonList;
    }

    /**
     * @Title: getLhcDragonLongResult
     * @Description: 获取六合彩系列长龙数据
     * @author HANS
     * @date 2019年5月28日下午7:49:41
     */
    private List<Map<String, Object>> getLhcDragonLongResult() {
        // 定义长龙接口返回类型
        List<Map<String, Object>> lhcDragonList = new ArrayList<Map<String, Object>>();
        try {
            /**
             * jslhc德州六合彩
             *  1特码两面单双、正码总单总双、正1特单双、正2特单双、正3特单双、正4特单双、正5特单双、正6特单双
             *  2特码两面大小、正码总大总小、正1特大小、正2特大小、正3特大小、正4特大小、正5特大小、正6特大小
             * */
            List<Map<String, Object>> onelhcLongMapList = onelhcLotterySgService.getOnelhcSgLong();
            lhcDragonList.addAll(onelhcLongMapList);
            /**
             * 5分六合彩
             * */
            List<Map<String, Object>> fivelhcLongMapList = fivelhcLotterySgService.getFivelhcSgLong();
            lhcDragonList.addAll(fivelhcLongMapList);
            /**
             * sslhc时时六合彩
             * */
            List<Map<String, Object>> sslhcLongMapList = amlhcLotterySgService.getSslhcSgLong();
            lhcDragonList.addAll(sslhcLongMapList);
        } catch (Exception e) {
            logger.error("app Method:getLhcDragonLongResult error:", e);
        }
        return lhcDragonList;
    }

    /**
     * @Title: getPksDragonLongResult
     * @Description: 获取PK10系列长龙数据
     * @author HANS
     * @date 2019年5月28日下午7:51:34
     */
    private List<Map<String, Object>> getPksDragonLongResult() {
        // 定义长龙接口返回类型
        List<Map<String, Object>> pksDragonList = new ArrayList<Map<String, Object>>();
        try {
            /**
             * jspks德州PK10
             *  W:冠亚和大小、冠军大小、亚军大小、第三名大小、第四名大小、第五名大小、第六名大小、第七名大小、第八名大小、第九名大小、第十名大小
             *  D:冠亚和单双、冠军单双、亚军单双、第三名单双、第四名单双、第五名单双、第六名单双、第七名单双、第八名单双、第九名单双、第十名单双
             *  L:冠军龙虎、亚军龙虎、第三名龙虎、第四名龙虎、第五名龙虎、第六名龙虎、第七名龙虎、第八名龙虎、第九名龙虎、第十名龙虎
             */
            List<Map<String, Object>> jspksLongMapList = jspksLotterySgService.getJspksSgLong();
            pksDragonList.addAll(jspksLongMapList);
            /**
             * 5分PK10
             * */
            List<Map<String, Object>> fivePksLongMapList = fvpksLotterySgService.getFivePksSgLong();
            pksDragonList.addAll(fivePksLongMapList);
            /**
             * 10分PK10
             * */
            List<Map<String, Object>> tenpksLongMapList = tenpksLotterySgService.getTenPksSgLong();
            pksDragonList.addAll(tenpksLongMapList);
            /**
             * bjpk10北京PK10
             * */
            List<Map<String, Object>> bjpksLongMapList = bjpksLotterySgService.getBjPksSgLong();
            pksDragonList.addAll(bjpksLongMapList);
            /**
             * xyft幸运飞艇
             * */
            List<Map<String, Object>> xyftLongMapList = xyftLotterySgService.getXyftPksSgLong();
            pksDragonList.addAll(xyftLongMapList);
        } catch (Exception e) {
            logger.error("app Method:getPksDragonLongResult error:", e);
        }
        return pksDragonList;
    }

}
