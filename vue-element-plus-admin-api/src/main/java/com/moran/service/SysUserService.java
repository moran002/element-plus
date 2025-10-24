package com.moran.service;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.crypto.asymmetric.KeyType;
import cn.hutool.v7.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moran.conf.constant.CommonConstant;
import com.moran.conf.constant.LogRecordConstant;
import com.moran.conf.exception.ServiceException;
import com.moran.controller.system.user.UserPasswordReqVO;
import com.moran.controller.system.user.UserReqVO;
import com.moran.mapper.SysUserMapper;
import com.moran.model.SysUser;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

/**
 * 用户表 Service接口
 *
 * @author MyBatis-Plus Generator
 * @since 2025-09-04
 */
@Service
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser getUserByAccount(String username) {
        return sysUserMapper.getUserByAccount(username);
    }

    public Page<SysUser> pageList(String account, String nickName, Long roleId, String mobile) {
        return sysUserMapper.pageList(account, nickName, roleId, mobile);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_USER, subType = LogRecordConstant.CREATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#user.id}}",
            extra = "{{#user.toString}}")
    public void save(UserReqVO vo) {
        SysUser user = BeanUtil.toBean(vo, SysUser.class);
        user.setPassword(CommonConstant.RSA.encryptBase64("123456", KeyType.PublicKey));
        sysUserMapper.insert(user);
        LogRecordContext.putVariable("user",user);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_USER, subType = LogRecordConstant.UPDATE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#vo.id}}",
            extra = "{{#user.toString}}")
    public void update(UserReqVO vo) {
        verifyUserExist(vo.getId());
        SysUser user = BeanUtil.toBean(vo, SysUser.class);
        sysUserMapper.updateById(user);
        LogRecordContext.putVariable("user",user);
    }

    private void verifyUserExist(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
    }
    @LogRecord(type = LogRecordConstant.SYSTEM_USER, subType = LogRecordConstant.DELETE,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#id}}",
    extra = "{{#id}}")
    public void delete(Long id) {
        verifyUserExist(id);
        sysUserMapper.deleteById(id);
    }

    @LogRecord(type = LogRecordConstant.SYSTEM_USER, subType = LogRecordConstant.PASSWORD,
            success = CommonConstant.SUCCESS, fail = "{{#_errorMsg}}", bizNo = "{{#vo.id}}",
            extra = "{{#user.toString}}")
    public void updatePassword(@Valid UserPasswordReqVO vo) {
        verifyUserExist(vo.getId());
        SysUser user = BeanUtil.toBean(vo, SysUser.class);
        user.setPassword(CommonConstant.RSA.encryptBase64(vo.getPassword(), KeyType.PublicKey));
        sysUserMapper.updateById(user);
        LogRecordContext.putVariable("user",user);
    }

    /**
     * @param password      未加密的密码
     * @param tablePassword 加密后的密码
     */
    public boolean checkPassword(String password, String tablePassword) {
        RSA rsa = CommonConstant.RSA;
        String dePwd = rsa.decryptStr(tablePassword, KeyType.PrivateKey);
        return password.equals(dePwd);
    }
}
