package com.moran.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.constant.CommonConstant;
import com.moran.conf.constant.LogRecordConstant;
import com.moran.conf.exception.ServiceException;
import com.moran.controller.system.role.RoleReqVO;
import com.moran.mapper.SysRoleMapper;
import com.moran.model.SysRole;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    public List<SysRole> listByIds(List<Long> roleIds) {
        return sysRoleMapper.listByIds(roleIds);
    }

    public List<SysRole> selectAll() {
        return sysRoleMapper.selectList(new LambdaQueryWrapper<>(SysRole.class));
    }

    public Page<SysRole> pageList(String name) {
        return sysRoleMapper.pageList(name);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_ROLE, subType = LogRecordConstant.CREATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#role.id}}",
            extra = "{{#role.toString}}")
    public void createRole(RoleReqVO vo) {
        SysRole role = BeanUtil.toBean(vo, SysRole.class);
        sysRoleMapper.insert(role);
        LogRecordContext.putVariable("role", role);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_ROLE, subType = LogRecordConstant.UPDATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#vo.id}}",
            extra = "{{#role.toString}}")
    public void updateRole(@Valid RoleReqVO vo) {
        verifyRoleName(vo.getName(), vo.getId());
        SysRole role = BeanUtil.toBean(vo, SysRole.class);
        sysRoleMapper.updateById(role);
        LogRecordContext.putVariable("role", role);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_ROLE, subType = LogRecordConstant.DELETE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#id}}")
    public void deleteRole(Long id) {
        verifyRole(id);
        sysRoleMapper.deleteById(id);
    }

    private void verifyRole(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            throw new ServiceException("请选择角色");
        }
    }

    private void verifyRoleName(String name, Long id) {
        SysRole role = sysRoleMapper.selectByRoleName(name);
        if (role == null) {
            return;
        }
        if (id == null || !id.equals(role.getId())) {
            throw new ServiceException("当前角色名称已存在");
        }
    }
}
