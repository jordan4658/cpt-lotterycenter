package com.caipiao.core.library.rest.read.system;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.operater.OperaterVO;
import com.caipiao.core.library.vo.system.*;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.OperateSensitiveLog;
import com.mapper.domain.Operater;
import com.mapper.domain.OperaterRole;
import com.mapper.domain.RoleResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AdminSystemServiceRest extends BaseRest {

    /**
     * 根据账号获取管理员对象
     * @param account
     * @return
     */
    @GetMapping("/shiroRealm/operater.json")
    Operater queryOperaterByAccount(@RequestParam("account") String account);

    /**
     * 根据角色id获取对应的角色名字
     * @param roleId
     * @return
     */
    @GetMapping("/shiroRealm/getRoleName.json")
    String getRoleName(@RequestParam("roleId") int roleId);

    /**
     * 根据角色id查找对应的资源
     * @param roleId
     * @return
     */
    @GetMapping("/shiroRealm/ResourceList.json")
    Set<String> getResourceByRoleId(@RequestParam("roleId") int roleId);

    /**
     * 查询全部权限规则
     * @return
     */
    @GetMapping("/shiroService/getPermission.json")
    List<RoleResource> getPermission();

    /**
     * 根据id查找角色对象
     * @param roleId
     * @return
     */
    @GetMapping("/admin/getOperaterRoleById.json")
    OperaterRole getOperaterRoleById(@RequestParam("roleId") int roleId);

    /**
     * 获取顶部菜单
     * @param roleId
     * @return
     */
    @GetMapping("/admin/listTopMenu.json")
    List<MenuDataVO> listTopMenu(@RequestParam("roleId") int roleId);

    /**
     * 获取左边菜单
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/admin/listLeftMenu.json")
    List<MenuDataVO> listLeftMenu(@RequestParam("roleId") int roleId);

    /**
     * 获取树菜单
     * @return
     */
    @GetMapping("/admin/queryMenusTree.json")
    List<ResourcesZtreeVO> queryMenusTree();

    /**
     * 根据菜单的父级id查询同一父级id下的菜单
     * @param parentId 菜单的父级id
     * @return
     */
    @GetMapping("/admin/queryMenuList.json")
    PageResult<List<MenuPermissionVO>> queryMenuList(@RequestParam("parentId") int parentId, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);

    /**
     * 根据菜单的父级id查询同一父级id下的菜单
     * @param menuId 菜单id
     * @return
     */
    @GetMapping("/admin/queryMenuParentIdById.json")
    int queryMenuParentIdById(@RequestParam("menuId") int menuId);


    /**
     * 根据id获取菜单信息
     * @param id 菜单id
     * @return
     */
    @GetMapping("/admin/findMenuInfo.json")
    ResultInfo<MenuVO> findMenuInfo(@RequestParam("id") int id);

    /**
     * 角色资源管理页面展示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/admin/queryRolesList.json")
    PageResult<List<RolesVO>> queryRolesList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);

    /**
     * 获取角色信息
     * @param id 角色id
     * @return
     */
    @GetMapping("/admin/findRoleInfo.json")
    ResultInfo<RolesVO> findRoleInfo(@RequestParam("id") int id);

    /**
     * 获取树状结构资源数据
     * @param roleId
     * @return
     */
    @GetMapping("/admin/queryResourcesZtree.json")
    List<ResourcesZtreeVO> queryResourcesZtree(@RequestParam("roleId") int roleId);


    /**
     * 获取系统管理员
     * @param memberId
     * @return
     */
    @GetMapping("/admin/queryMemberById.json")
    ResultInfo<AdminOperaterVO> queryMemberById(@RequestParam("memberId") int memberId);

    /**
     * 查找是否存在该账号
     * @param account 账号
     * @param memberId 当前用户的id
     * @return
     */
    @GetMapping("/admin/queryMemberByAccount.json")
    ResultInfo<Void> queryMemberByAccount(@RequestParam("account") String account, @RequestParam("memberId") int memberId);

    /**
     * 获取系统管理员分页信息
     * @param pageNum 页数
     * @param pageSize 页大小
     * @param account 账号
     * @param status 状态
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @GetMapping("/admin/getOperaterPageInfo.json")
    PageResult<List<AdminOperaterVO>> getOperaterPageInfo(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                                                          @RequestParam("account") String account, @RequestParam("status") int status,
                                                          @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/admin/getRoleList.json")
    ResultInfo<List<RolesVO>> getRoleList();

    /**
     * 条件查询敏感日志
     * @param pageNum 页数
     * @param pageSize 页大小
     * @param account 账号
     * @param module 模块
     * @param methods 方法
     * @param responseResult 响应结果，0执行成功，1执行失败
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @GetMapping("/admin/queryOperateSensitiveLogs.json")
    PageResult<List<OperateSensitiveLog>> queryOperateSensitiveLogs(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                                                                    @RequestParam("account") String account, @RequestParam("module") String module,
                                                                    @RequestParam("methods") String methods, @RequestParam("responseResult") int responseResult,
                                                                    @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 条件查询需导出的敏感日志对象
     * @param account 账号
     * @param module 模块
     * @param methods 方法
     * @param responseResult 响应结果，0执行成功，1执行失败
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @GetMapping("/admin/querySensitiveLogExportVOs.json")
    List<SensitiveLogExportVO> querySensitiveLogExportVOs(@RequestParam("account") String account, @RequestParam("module") String module,
                                                          @RequestParam("methods") String methods, @RequestParam("responseResult") int responseResult,
                                                          @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);
}
