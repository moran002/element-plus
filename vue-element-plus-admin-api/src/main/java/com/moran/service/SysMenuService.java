package com.moran.service;

import com.moran.conf.constant.CommonConstant;
import com.moran.conf.constant.LogRecordConstant;
import com.moran.conf.exception.ServiceException;
import com.moran.controller.system.menu.MenuReqVO;
import com.moran.controller.system.menu.MenuTypeEnum;
import com.moran.convert.MenuConvert;
import com.moran.mapper.SysMenuMapper;
import com.moran.model.SysMenu;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
@Service
public class SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    public List<SysMenu> listByIds(List<Long> menuIds) {
        return sysMenuMapper.listByIds(menuIds);
    }

    public List<SysMenu> selectAll() {
        return sysMenuMapper.selectAll();
    }
    @LogRecord(type = LogRecordConstant.SYSTEM_MENU, subType = LogRecordConstant.CREATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#menu.id}}",
            extra = "{{#menu.toString}}")
    public void createMenu(MenuReqVO vo) {
        verifyParentMenu(vo.getParentId());
        SysMenu menu = MenuConvert.INSTANCE.convertMenu(vo);
        sysMenuMapper.insert(menu);
        LogRecordContext.putVariable("menu", menu);
    }

    private void verifyParentMenu(Long parentId) {
        if (parentId == null) {
            return;
        }
        SysMenu menu = sysMenuMapper.selectById(parentId);
        if (menu == null) {
            throw new ServiceException("上级菜单不存在");
        }
        if (MenuTypeEnum.BUTTON.getType() == menu.getType()) {
            throw new ServiceException("当前菜单为按钮,不能作为上级");
        }
    }

    private void verifyMenu(Long menuId) {
        if (menuId == null) {
            throw new ServiceException("请选择菜单");
        }
        SysMenu menu = sysMenuMapper.selectById(menuId);
        if (menu == null) {
            throw new ServiceException("菜单不存在");
        }
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_MENU, subType = LogRecordConstant.UPDATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#vo.id}}",
            extra = "{{#menu.toString}}")
    public void updateMenu(MenuReqVO vo) {
        verifyMenu(vo.getId());
        verifyParentMenu(vo.getParentId());
        SysMenu menu = MenuConvert.INSTANCE.convertMenu(vo);
        sysMenuMapper.updateById(menu);
        LogRecordContext.putVariable("menu", menu);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_MENU, subType = LogRecordConstant.DELETE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#id}}")
    public void deleteMenu(Long id) {
        verifyMenu(id);
        sysMenuMapper.deleteById(id);
    }

    /**
     * 查询所有存在下级的上级ID
     */
    public List<Long> getParentIsExistChildren() {
        List<SysMenu> menus = selectAll();
        List<Long> parentIds = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menus.stream().anyMatch(m -> menu.getId().equals(m.getParentId()))) {
                parentIds.add(menu.getId());
            }
        }
        return parentIds;
    }
}
