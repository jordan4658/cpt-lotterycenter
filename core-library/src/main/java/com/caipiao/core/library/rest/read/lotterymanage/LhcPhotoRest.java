package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.lotterymanage.LhcPhotoCategoryVO;
import com.caipiao.core.library.vo.lotterymanage.LhcPhotoVO;
import com.mapper.domain.LhcPhotoCategory;
import com.mapper.domain.LhcPhotoComment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface LhcPhotoRest {

    /**
     * 根据父级id获取六合彩图库的分类
     * @param parentId
     * @return
     */
    @PostMapping("/lhcPhoto/findLhcPhotoCategoryByParentId.json")
    List<LhcPhotoCategoryVO> findLhcPhotoCategoryByParentId(@RequestParam("parentId") Integer parentId);

    /**
     * 六合图库列表
     * @param pageNum
     * @param pageSize
     * @param issue
     * @param oneId
     * @param twoId
     * @return
     */
    @PostMapping("/lhcPhoto/pageLhcPhoto.json")
    PageResult<List<LhcPhotoVO>> pageLhcPhoto(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("issue") String issue, @RequestParam("oneId") Integer oneId, @RequestParam("twoId") Integer twoId);

    @PostMapping("/lhcPhoto/findLhcPhotoById.json")
    LhcPhotoVO findLhcPhotoById(@RequestParam("photoId") Integer photoId);

    /**
     * 六合图库分类列表
     * @param pageNum
     * @param pageSize
     * @param parentId
     * @param name
     * @return
     */
    @PostMapping("/lhcPhoto/pageLhcPhotoCategory.json")
    PageResult<List<LhcPhotoCategoryVO>> pageLhcPhotoCategory(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("parentId") Integer parentId, @RequestParam("name") String name);

    @PostMapping("/lhcPhoto/findLhcPhotoCategoryById.json")
    LhcPhotoCategory findLhcPhotoCategoryById(@RequestParam("categoryId") Integer categoryId);
    @PostMapping("/lhcPhoto/pageLhcPhotoComment.json")
	PageResult<List<LhcPhotoComment>> pageLhcPhotoComment(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name,
                                                          @RequestParam("content") String content, @RequestParam("start") String start, @RequestParam("end") String end);
}
