package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.system.MenuPermissionVO;
import com.caipiao.core.library.vo.system.MenuVO;
import com.caipiao.core.library.vo.system.ResourcesZtreeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface ProblemComplaintServiceRest extends BaseRest {

    /**
     * 获取树菜单
     * @return
     */
    @GetMapping("/pcr/queryMenusTree.json")
    List<ResourcesZtreeVO> queryMenusTree();

    /**
     * 获取树菜单--给接口使用
     * @return
     */
    @GetMapping("/pcr/queryMenusTreeForInterface.json")
    List<ResourcesZtreeVO> queryMenusTreeForInterface();

    /**
     * 根据菜单的父级id查询同一父级id下的菜单
     * @param parentId 菜单的父级id
     * @return
     */
    @GetMapping("/pcr/queryMenuList.json")
    PageResult<List<MenuPermissionVO>> queryMenuList(@RequestParam("parentId") int parentId, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);

    /**
     * 根据菜单的父级id查询同一父级id下的菜单
     * @param menuId 菜单id
     * @return
     */
    @GetMapping("/pcr/queryMenuParentIdById.json")
    int queryMenuParentIdById(@RequestParam("menuId") int menuId);


    /**
     * 根据id获取菜单信息
     * @param id 菜单id
     * @return
     */
    @GetMapping("/pcr/findMenuInfo.json")
    ResultInfo<MenuVO> findMenuInfo(@RequestParam("id") int id);

}
