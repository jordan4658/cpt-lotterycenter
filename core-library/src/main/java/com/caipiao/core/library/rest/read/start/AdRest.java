package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.AdPhotoVO;
import com.caipiao.core.library.vo.start.AdVO;
import com.mapper.domain.AdBasic;
import com.mapper.domain.AdSite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AdRest {

    /**
     * 后台广告管理列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    @GetMapping("/start/pageAdBasic.json")
    PageResult<List<AdBasic>> pageAdBasic(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title);

    /**
     * 获取所有的广告位置
     * @return
     */
    @GetMapping("/start/getAllAdSite.json")
    Map<String,List<AdSite>> getAllAdSite();

    /**
     * 根据id获取广告信息
     * @param adId
     * @return
     */
    @GetMapping("/start/getAdById.json")
    AdVO getAdById(@RequestParam("adId") Integer adId);

    /**
     * 根据发布系统类型和位置来获取图片
     * @param type
     * @param siteId
     * @return
     */
    @PostMapping("/start/findByTypeAndSite.json")
    List<AdPhotoVO> findByTypeAndSite(@RequestParam("type") Integer type, @RequestParam("siteId") Integer siteId);
}
