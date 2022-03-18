package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.lotterymanage.LhcPlayInfoDTO;
import com.caipiao.core.library.vo.lotterymanage.LotteryPlayDTO;
import com.caipiao.core.library.vo.lotterymanage.LotteryPlayInfoDTO;
import com.caipiao.core.library.vo.lotterymanage.PcddPlayInfoDTO;
import com.mapper.domain.LhcGodType;
import com.mapper.domain.LotteryPlay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface LotteryPlayReadRest {


    /**
     * 根据分类Id查询玩法列表
     * @param cateId 分类id
     * @return
     */
    @PostMapping("/play/playList")
    List<LotteryPlayDTO> playList(@RequestParam(name = "cateId") Integer cateId);

    /**
     * 获取玩法列表（除本身外）
     * @param id 本身id
     * @return
     */
    @PostMapping("/play/getPlays")
    List<LotteryPlay> getPlays(@RequestParam(name = "id") Integer id);

    /**
     * 根据分类id获取玩法列表
     * @param cateId 分类id
     * @return
     */
    @PostMapping("/play/getPlaysByCateId")
    List<LotteryPlay> getPlaysByCateId(@RequestParam(name = "cateId") Integer cateId);

    /**
     * 根据分类id获取一级玩法列表
     * @param cateId 分类id
     * @return
     */
    @PostMapping("/play/getPlaysFirstLevelByCateId")
    List<LotteryPlay> getPlaysFirstLevelByCateId(@RequestParam(name = "cateId") Integer cateId);

    /**
     * 根据id查询玩法信息
     * @param id 玩法id
     * @return
     */
    @PostMapping("/play/queryPlayById")
    LotteryPlay queryPlayById(@RequestParam(name = "id") Integer id);

    /**
     * 根据父级id查找子集信息
     * @param parentId 父级id
     * @return
     */
    @PostMapping("/play/queryPlayByParentId")
    List<LotteryPlay> queryPlayByParentId(@RequestParam(name = "parentId") Integer parentId);

    /**
     * 查询玩法配置信息
     * @param playId 玩法id
     * @return
     */
    @PostMapping("/play/queryPlayInfoById")
    ResultInfo<Map<String, LotteryPlayInfoDTO>> queryPlayInfoById(@RequestParam(name = "lotteryId") Integer lotteryId, @RequestParam(name = "playId") Integer playId);

    /**
     * 获取PC蛋蛋具体玩法赔率信息
     * @param playId 玩法id
     * @param lotteryId 彩种id
     * @return
     */
    @PostMapping("/play/queryPcddPlayOdds")
    ResultInfo<PcddPlayInfoDTO> queryPcddPlayOdds(@RequestParam(name = "playId") Integer playId, @RequestParam(name = "lotteryId") Integer lotteryId);

    /**
     * 获取六合彩具体玩法赔率信息
     * @param playId 玩法id
     * @param lotteryId 彩种id
     * @return
     */
    @PostMapping("/play/queryLhcBuy")
    ResultInfo<List<LhcPlayInfoDTO>> queryLhcBuy(@RequestParam(name = "playId") Integer playId, @RequestParam(name = "lotteryId") Integer lotteryId);

    /**
     * 根据分类id和玩法等级获取玩法列表
     * @param cateId 分类id
     * @param level 玩法等级
     * @return
     */
    @PostMapping("/play/getPlaysByCateIdAndLevel.json")
    List<LotteryPlay> getPlaysByCateIdAndLevel(@RequestParam(name = "cateId") Integer cateId, @RequestParam(name = "level") Integer level);
    
    /**
     * 根据分类id获取一级玩法列表
     * @param cateId 分类id
     * @return
     */
    @PostMapping("/play/getPlaysFirstLevelByLotteryId.json")
	List<LotteryPlay> getPlaysFirstLevelByLotteryId(@RequestParam(name = "lotteryId") Integer lotteryId);
    
    /**
     * 根据分类id和玩法等级获取玩法列表
     * @param cateId 分类id
     * @param level 玩法等级
     * @return
     */
    @PostMapping("/play/getPlaysByLotteryIdAndLevel.json")
    List<LotteryPlay> getPlaysByLotteryIdAndLevel(@RequestParam(name = "lotteryId") Integer lotteryId, @RequestParam(name = "level") Integer level);
    
    @RequestMapping("/lhcGod/getWebGodType.json")
	PageResult<List<LhcGodType>> getWebGodType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

}
