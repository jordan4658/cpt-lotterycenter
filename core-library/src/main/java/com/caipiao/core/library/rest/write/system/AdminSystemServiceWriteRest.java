package com.caipiao.core.library.rest.write.system;

import com.caipiao.core.library.dto.system.AdminOperaterDTO;
import com.caipiao.core.library.dto.system.RoleResourcesDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.Operater;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AdminSystemServiceWriteRest extends BaseRest {

    /**
     * 修改用户信息
     * @param operater
     */
    @PostMapping("adminWrite/updateOperaterById.json")
    void updateOperaterById(@RequestBody Operater operater);

    /**
     * 修改菜单图标
     * @param id 菜单id
     * @param icon 菜单图标
     * @return
     */
    @PostMapping("adminWrite/uploadPictureFile.json")
    ResultInfo<Void> uploadPictureFile(@RequestParam("id") int id, @RequestParam("icon") String icon);

    /**
     * 改变菜单排序
     * @param id
     * @param moveId
     * @return
     */
    @PostMapping("adminWrite/moveMenuInfo.json")
    ResultInfo<Void> moveMenuInfo(@RequestParam("id") int id, @RequestParam("moveId") String moveId);

    /**
     * 切换菜单状态
     * @param menuId 菜单id
     * @return
     */
    @PostMapping("adminWrite/cutMenuStatus.json")
    ResultInfo<Void> cutMenuStatus(@RequestParam("menuId") int menuId);

    /**
     * 新增菜单
     * @return
     */
    @PostMapping("adminWrite/insertMenuInfo.json")
    ResultInfo<Void> insertMenuInfo(@RequestParam("name") String name,
                                    @RequestParam("parentId") int parentId, @RequestParam("permission") String permission,
                                    @RequestParam("type") int type, @RequestParam("url") String url);

    /**
     * 修改菜单
     * @return
     */
    @PostMapping("adminWrite/updateMenuInfo.json")
    ResultInfo<Void> updateMenuInfo(@RequestParam("id") int id, @RequestParam("name") String name,
                                    @RequestParam("parentId") int parentId, @RequestParam("permission") String permission,
                                    @RequestParam("type") int type, @RequestParam("url") String url);

    /**
     * 删除菜单及其绑定的资源
     * @param menuId
     * @return
     */
    @PostMapping("adminWrite/delMenu.json")
    ResultInfo<Void> delMenu(@RequestParam("menuId") int menuId);

    /**
     * 切换角色状态
     * @param roleId 角色id
     * @return
     */
    @PostMapping("adminWrite/cutRoleStatus.json")
    ResultInfo<Void> cutRoleStatus(@RequestParam("roleId") Integer roleId);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @PostMapping("adminWrite/deleteRole.json")
    ResultInfo<Void> deleteRole(@RequestParam("roleId") Integer roleId);

    /**
     * 保存角色资源
     * @param roleResourcesDTO
     * @return
     */
    @PostMapping("adminWrite/saveOrUpdateResource.json")
    int saveOrUpdateResource(@RequestBody RoleResourcesDTO roleResourcesDTO);

    /**
     * 新增角色
     * @param name 角色名
     * @param remark 角色备注
     * @return
     */
    @PostMapping("adminWrite/insertRoleInfo.json")
    ResultInfo<Void> insertRoleInfo(@RequestParam("name") String name, @RequestParam("remark") String remark);

    /**
     * 修改角色
     * @param id 角色id
     * @param name 角色名
     * @param remark 角色备注
     * @return
     */
    @PostMapping("adminWrite/updateRoleInfo.json")
    ResultInfo<Void> updateRoleInfo(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("remark") String remark);

    /**
     * 切换账号的状态
     * @param memberId
     * @return
     */
    @PostMapping("adminWrite/updateMemberType.json")
    ResultInfo<Void> updateMemberType(@RequestParam("memberId") int memberId);

    /**
     * 切换账号的状态
     * @param memberId
     * @return
     */
    @PostMapping("adminWrite/deleteAdmin.json")
    ResultInfo<Void> deleteAdmin(@RequestParam("memberId") int memberId);

    /**
     * 新增或修改用户
     * @param id 用户id
     * @param account 账号
     * @param password 密码
     * @param salt 加密盐
     * @param roleId 角色id
     * @return
     */
    @PostMapping("adminWrite/insertOrUpdateMember.json")
    ResultInfo<Void> insertOrUpdateMember(@RequestParam("id") int id, @RequestParam("account") String account,
                                          @RequestParam("password") String password, @RequestParam("salt") String salt,
                                          @RequestParam("roleId") int roleId);
}
