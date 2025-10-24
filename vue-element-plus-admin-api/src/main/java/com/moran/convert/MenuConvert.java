package com.moran.convert;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.moran.controller.auth.vo.RouterRespVO;
import com.moran.controller.system.menu.MenuReqVO;
import com.moran.controller.system.menu.MenuRespVO;
import com.moran.model.SysMenu;
import com.moran.model.vo.TreeRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    default List<TreeRespVO> convertTree(List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getType() == 0 && m.getParentId() == null)
                .map(m -> {
                    TreeRespVO treeRespVO = new TreeRespVO();
                    treeRespVO.setValue(m.getId());
                    treeRespVO.setLabel(m.getTitle());
                    treeRespVO.setChildren(findTreeChildren(m.getId(), menus));
                    return treeRespVO;
                }).toList();
    }

    private List<TreeRespVO> findTreeChildren(Long parentId, List<SysMenu> menus) {
        return menus.stream().filter(m -> parentId.equals(m.getParentId()))
                .map(m -> {
                    TreeRespVO treeRespVO = new TreeRespVO();
                    treeRespVO.setValue(m.getId());
                    treeRespVO.setLabel(m.getTitle());
                    treeRespVO.setChildren(findTreeChildren(m.getId(), menus));
                    return treeRespVO;
                }).toList();
    }

    default List<MenuRespVO> convertList(List<SysMenu> list) {
        return list.stream().filter(m -> m.getType() == 0 && m.getParentId() == null)
                .map(m -> {
                    MenuRespVO vo = BeanUtil.toBean(m, MenuRespVO.class);
                    MenuRespVO.Meta meta = BeanUtil.toBean(m, MenuRespVO.Meta.class);
                    vo.setMeta(meta);
                    vo.setChildren(findChildren(m.getId(), list, m.getTitle()));
                    return vo;
                }).toList();
    }

    private List<MenuRespVO> findChildren(Long parentId, List<SysMenu> list, String parentName) {
        return list.stream().filter(m -> parentId.equals(m.getParentId()))
                .map(m -> {
                    MenuRespVO vo = BeanUtil.toBean(m, MenuRespVO.class);
                    vo.setParentName(parentName);
                    MenuRespVO.Meta meta = BeanUtil.toBean(m, MenuRespVO.Meta.class);
                    vo.setMeta(meta);
                    vo.setChildren(findChildren(m.getId(), list, m.getTitle()));
                    return vo;
                }).toList();
    }

    default SysMenu convertMenu(MenuReqVO vo) {
        SysMenu menu = BeanUtil.toBean(vo, SysMenu.class);
        if (vo.getMeta() != null) {
            menu.setTitle(vo.getMeta().getTitle());
            menu.setHidden(vo.getMeta().getHidden());
            menu.setAlwaysShow(vo.getMeta().getAlwaysShow());
            menu.setIcon(vo.getMeta().getIcon());
            menu.setNoCache(vo.getMeta().getNoCache());
            menu.setBreadcrumb(vo.getMeta().getBreadcrumb());
            menu.setAffix(vo.getMeta().getAffix());
            menu.setNoTagsView(vo.getMeta().getNoTagsView());
            menu.setActiveMenu(vo.getMeta().getActiveMenu());
        }
        return menu;
    }

    default List<RouterRespVO> convertRouters(List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getParentId() == null && m.getType() == 0)
                .map(m -> {
                    RouterRespVO vo = BeanUtil.toBean(m, RouterRespVO.class);
                    vo.setMeta(BeanUtil.toBean(m, RouterRespVO.Meta.class));
                    vo.setChildren(findRoutersChildren(m.getId(), menus));
                    return vo;
                }).toList();
    }

    private static List<RouterRespVO> findRoutersChildren(Long parentId, List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getParentId() != null
                        && parentId.equals(m.getParentId())
                        && m.getType() == 1)
                .map(m -> {
                    RouterRespVO vo = BeanUtil.toBean(m, RouterRespVO.class);
                    vo.setMeta(BeanUtil.toBean(m, RouterRespVO.Meta.class));
                    vo.setChildren(findRoutersChildren(m.getId(), menus));
                    if (CollUtil.isEmpty(vo.getChildren())) {
                        vo.getMeta().setPermission(menus.stream().filter(c -> c.getType() == 2
                                        && m.getId().equals(c.getParentId())
                                        && StrUtil.isNotBlank(m.getPermission()))
                                .map(SysMenu::getPermission).toList());
                    }
                    return vo;
                }).toList();
    }
}
